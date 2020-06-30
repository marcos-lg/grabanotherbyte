package com.onemorebyte.collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.teeing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectorsAfterJava8Test {

  private static final List<Email> EMAILS = new ArrayList<>();

  static {
    EMAILS.add(
        new Email(
            "John",
            Arrays.asList("Paula", "Tim"),
            Collections.singletonList("David"),
            "Greetings",
            "Greetings from John"));
    EMAILS.add(
        new Email(
            "Tim",
            Arrays.asList("Robert", "Joe"),
            Collections.emptyList(),
            "How are you?",
            "Hello, how are you?"));
    EMAILS.add(
        new Email(
            "Maria",
            Collections.singletonList("Peter"),
            Collections.emptyList(),
            "Hi",
            "Hi Peter!"));
    EMAILS.add(
        new Email(
            "Maria",
            Collections.singletonList("Joe"),
            Collections.singletonList("Anna"),
            "Hello",
            "Hello Joe!"));
  }

  @Test
  public void flatMappingTest() {
    // JAVA 9
    Set<String> recipients = EMAILS.stream().collect(flatMapping(e -> e.getTo().stream(), toSet()));
    assertEquals(5, recipients.size());

    recipients = EMAILS.stream().flatMap(e -> e.getTo().stream()).collect(toSet());
    assertEquals(5, recipients.size());

    Map<String, Set<String>> recipientsBySender =
        EMAILS.stream()
            .collect(groupingBy(Email::getFrom, flatMapping(e -> e.getTo().stream(), toSet())));
    assertEquals(3, recipientsBySender.keySet().size());
    assertEquals(2, recipientsBySender.get("Maria").size());
  }

  @Test
  public void filteringTest() {
    // JAVA 9
    List<Integer> list = Stream.of(1, 2, 3, 3, 4, 5).collect(filtering(v -> v > 2, toList()));
    assertEquals(4, list.size());

    Map<String, List<Email>> ccEmailsBySender =
        EMAILS.stream()
            .collect(
                groupingBy(
                    Email::getFrom,
                    filtering(e -> e.getCc() != null && !e.getCc().isEmpty(), toList())));
    assertEquals(3, ccEmailsBySender.keySet().size());
    assertEquals(1, ccEmailsBySender.get("Maria").size());
    assertEquals(0, ccEmailsBySender.get("Tim").size());

    Map<String, List<Email>> ccEmailsBySenderWithoutFiltering =
        EMAILS.stream()
            .filter(e -> e.getCc() != null && !e.getCc().isEmpty())
            .collect(groupingBy(Email::getFrom));
    assertEquals(2, ccEmailsBySenderWithoutFiltering.keySet().size());
    assertEquals(1, ccEmailsBySenderWithoutFiltering.get("Maria").size());
    assertNull(ccEmailsBySenderWithoutFiltering.get("Tim"));
  }

  @Test
  public void unmodifiableCollectionUnsupportedTest() {
    assertThrows(
        UnsupportedOperationException.class,
        () -> Stream.of(1, 2, 3, 3, 4, 5).filter(v -> v > 2).collect(toCollection(List::of)));
  }

  @Test
  public void unmodifiableCollectionUsingCollectingAndThenTest() {
    List<Integer> unmodifiableList =
        Stream.of(1, 2, 3, 3, 4, 5)
            .filter(v -> v > 2)
            .collect(collectingAndThen(toList(), List::copyOf));
    assertEquals(4, unmodifiableList.size());
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(6));
  }

  @Test
  public void toUnmodifiableListTest() {
    // JAVA 10
    List<Integer> unmodifiableList =
        Stream.of(1, 2, 3, 3, 4, 5).filter(v -> v > 2).collect(toUnmodifiableList());
    assertEquals(4, unmodifiableList.size());
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(6));
  }

  @Test
  public void toUnmodifiableSetTest() {
    // JAVA 10
    Set<Integer> unmodifiableSet =
        Stream.of(1, 2, 3, 3, 4, 5).filter(v -> v > 2).collect(toUnmodifiableSet());
    assertEquals(3, unmodifiableSet.size());
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableSet.add(6));
  }

  @Test
  public void toUnmodifiableMapTest() {
    // JAVA 10
    Map<String, Email> unmodifiableMap =
        EMAILS.stream().collect(toUnmodifiableMap(Email::getSubject, v -> v));
    assertEquals(4, unmodifiableMap.keySet().size());
    assertThrows(UnsupportedOperationException.class, () -> unmodifiableMap.remove("yellow"));
  }

  @Test
  public void teeingTest() {
    // JAVA 12
    String person = "Joe";
    boolean moreSentEmailsThanReceived =
        EMAILS.stream()
            .collect(
                teeing(
                    filtering(e -> e.getFrom().equalsIgnoreCase(person), counting()),
                    filtering(e -> e.getTo().contains(person), counting()),
                    (from, to) -> from - to > 0));
    assertFalse(moreSentEmailsThanReceived);

    String person2 = "Maria";
    moreSentEmailsThanReceived =
        EMAILS.stream()
            .collect(
                teeing(
                    filtering(e -> e.getFrom().equalsIgnoreCase(person2), counting()),
                    filtering(e -> e.getTo().contains(person2), counting()),
                    (from, to) -> from - to > 0));
    assertTrue(moreSentEmailsThanReceived);

    // diff between the longest and shortest email body
    int diff =
        EMAILS.stream()
            .map(e -> e.getBody().length())
            .collect(
                teeing(
                    collectingAndThen(
                        maxBy(Comparator.naturalOrder()), (Optional<Integer> v) -> v.orElse(0)),
                    collectingAndThen(
                        minBy(Comparator.naturalOrder()), (Optional<Integer> v) -> v.orElse(0)),
                    (max, min) -> max - min));
    assertEquals(10, diff);

  }

  private static class Email {
    private String from;
    private List<String> to;
    private List<String> cc;
    private String subject;
    private String body;

    Email(String from, List<String> to, List<String> cc, String subject, String body) {
      this.from = from;
      this.to = to;
      this.cc = cc;
      this.subject = subject;
      this.body = body;
    }

    public String getFrom() {
      return from;
    }

    public void setFrom(String from) {
      this.from = from;
    }

    public List<String> getTo() {
      return to;
    }

    public void setTo(List<String> to) {
      this.to = to;
    }

    public List<String> getCc() {
      return cc;
    }

    public void setCc(List<String> cc) {
      this.cc = cc;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }

    public String getBody() {
      return body;
    }

    public void setBody(String body) {
      this.body = body;
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", Email.class.getSimpleName() + "[", "]")
          .add("from='" + from + "'")
          .add("to=" + to)
          .add("cc=" + cc)
          .add("subject='" + subject + "'")
          .add("body='" + body + "'")
          .toString();
    }
  }
}
