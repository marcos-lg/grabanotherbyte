package com.grabanotherbyte.jackson.mixins;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonTest {

  @Test
  public void personSerializerTest() throws JsonProcessingException {
    Person person = new Person("Oliver", "Atom");

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(person);

    Person read = objectMapper.readValue(json, Person.class);
    assertEquals("Oliver", read.getFirstName());
    assertEquals("Atom", read.getLastName());

    objectMapper = new ObjectMapper();
    objectMapper.addMixIn(Person.class, PersonMixin.class);
    json = objectMapper.writeValueAsString(person);

    read = objectMapper.readValue(json, Person.class);
    assertEquals("Oliver", read.getFirstName());
    assertEquals("Atom", read.getLastName());
  }
}
