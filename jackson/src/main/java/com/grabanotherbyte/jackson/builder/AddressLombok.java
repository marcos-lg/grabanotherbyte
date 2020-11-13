package com.grabanotherbyte.jackson.builder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AddressLombok.AddressLombokBuilder.class)
public class AddressLombok {

  String street;
  String zipCode;
  String city;
  String province;
  String country;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AddressLombokBuilder {
    // Lombok will add the rest
  }
}
