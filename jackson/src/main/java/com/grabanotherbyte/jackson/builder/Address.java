package com.grabanotherbyte.jackson.builder;

import java.util.Objects;
import java.util.StringJoiner;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = Address.Builder.class)
public class Address {

  private final String street;
  private final String zipCode;
  private final String city;
  private final String province;
  private final String country;

  private Address(String street, String zipCode, String city, String province, String country) {
    this.street = street;
    this.zipCode = zipCode;
    this.city = city;
    this.province = province;
    this.country = country;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getStreet() {
    return street;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getCity() {
    return city;
  }

  public String getProvince() {
    return province;
  }

  public String getCountry() {
    return country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(street, address.street)
        && Objects.equals(zipCode, address.zipCode)
        && Objects.equals(city, address.city)
        && Objects.equals(province, address.province)
        && Objects.equals(country, address.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, zipCode, city, province, country);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Address.class.getSimpleName() + "[", "]")
        .add("street='" + street + "'")
        .add("zipCode='" + zipCode + "'")
        .add("city='" + city + "'")
        .add("province='" + province + "'")
        .add("country='" + country + "'")
        .toString();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {

    private String street;
    private String zipCode;
    private String city;
    private String province;
    private String country;

    public Builder street(String street) {
      this.street = street;
      return this;
    }

    public Builder zipCode(String zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Builder province(String province) {
      this.province = province;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public Address build() {
      return new Address(street, zipCode, city, province, country);
    }
  }
}
