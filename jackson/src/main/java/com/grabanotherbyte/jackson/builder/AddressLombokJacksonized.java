package com.grabanotherbyte.jackson.builder;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class AddressLombokJacksonized {

  private final String street;
  private final String zipCode;
  private final String city;
  private final String province;
  private final String country;
}
