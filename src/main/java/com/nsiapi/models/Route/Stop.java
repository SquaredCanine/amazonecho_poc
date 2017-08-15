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
    "departure",
    "type"
})
public class Stop {

  @JsonProperty("name")
  private String name;
  @JsonProperty("alias")
  private Object alias;
  @JsonProperty("country")
  private String country;
  @JsonProperty("code")
  private String code;
  @JsonProperty("arrival")
  private Arrival_ arrival;
  @JsonProperty("departure")
  private Departure_ departure;
  @JsonProperty("type")
  private String type;
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
  public Object getAlias() {
    return alias;
  }

  @JsonProperty("alias")
  public void setAlias(Object alias) {
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
  public Arrival_ getArrival() {
    return arrival;
  }

  @JsonProperty("arrival")
  public void setArrival(Arrival_ arrival) {
    this.arrival = arrival;
  }

  @JsonProperty("departure")
  public Departure_ getDeparture() {
    return departure;
  }

  @JsonProperty("departure")
  public void setDeparture(Departure_ departure) {
    this.departure = departure;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
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
