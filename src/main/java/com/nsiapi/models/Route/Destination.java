package com.nsiapi.models.Route;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "alias",
    "country",
    "code",
    "arrival",
    "departure"
})
public class Destination {

  @JsonProperty("name")
  private String name;
  @JsonProperty("alias")
  private String alias;
  @JsonProperty("country")
  private String country;
  @JsonProperty("code")
  private String code;
  @JsonProperty("arrival")
  private Arrival arrival;
  @JsonProperty("departure")
  private Object departure;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("alias")
  public String getAlias() {
    return alias;
  }

  @JsonProperty("alias")
  public void setAlias(String alias) {
    this.alias = alias;
  }

  @JsonProperty("country")
  public String getCountry() {
    return country;
  }

  @JsonProperty("country")
  public void setCountry(String country) {
    this.country = country;
  }

  @JsonProperty("code")
  public String getCode() {
    return code;
  }

  @JsonProperty("code")
  public void setCode(String code) {
    this.code = code;
  }

  @JsonProperty("arrival")
  public Arrival getArrival() {
    return arrival;
  }

  @JsonProperty("arrival")
  public void setArrival(Arrival arrival) {
    this.arrival = arrival;
  }

  @JsonProperty("departure")
  public Object getDeparture() {
    return departure;
  }

  @JsonProperty("departure")
  public void setDeparture(Object departure) {
    this.departure = departure;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
