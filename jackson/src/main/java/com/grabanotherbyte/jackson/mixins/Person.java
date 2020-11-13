package com.grabanotherbyte.jackson.mixins;

import java.io.IOException;
import java.util.StringJoiner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.grabanotherbyte.jackson.mixins.Person.PersonDeserializer;
import com.grabanotherbyte.jackson.mixins.Person.PersonSerializer;

@JsonSerialize(using = PersonSerializer.class)
@JsonDeserialize(using = PersonDeserializer.class)
public class Person {

  private final String firstName;
  private final String lastName;

  @JsonCreator
  public Person(
      @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
        .add("firstName='" + firstName + "'")
        .add("lastName='" + lastName + "'")
        .toString();
  }

  public static class PersonSerializer extends JsonSerializer<Person> {

    static final String SEPARATOR = " ";

    @Override
    public void serialize(
        Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      jsonGenerator.writeString(person.getFirstName() + SEPARATOR + person.getLastName());
    }
  }

  public static class PersonDeserializer extends JsonDeserializer<Person> {

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      String[] fields = jsonParser.getValueAsString().split(PersonSerializer.SEPARATOR);
      return new Person(fields[0], fields[1]);
    }
  }

  public static class PersonReversedSerializer extends JsonSerializer<Person> {

    static final String SEPARATOR = ", ";

    @Override
    public void serialize(
        Person person, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      jsonGenerator.writeString(person.getLastName() + SEPARATOR + person.getFirstName());
    }
  }

  public static class PersonReversedDeserializer extends JsonDeserializer<Person> {

    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {
      String[] fields = jsonParser.getValueAsString().split(PersonReversedSerializer.SEPARATOR);
      return new Person(fields[1], fields[0]);
    }
  }
}
