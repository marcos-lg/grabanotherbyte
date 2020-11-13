package com.grabanotherbyte.jackson.builder;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressLombokJacksonizedTest {

  @Test
  public void addressTest() throws JsonProcessingException {
    AddressLombokJacksonized address =
        AddressLombokJacksonized.builder()
            .street("street")
            .zipCode("1234")
            .city("my city")
            .province("province")
            .country("country")
            .build();

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(address);

    AddressLombokJacksonized read = objectMapper.readValue(json, AddressLombokJacksonized.class);
    assertEquals(address, read);
  }
}
