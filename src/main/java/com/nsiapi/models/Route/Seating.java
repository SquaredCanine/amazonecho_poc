package com.nsiapi.models.Route;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "description",
    "reservation",
    "preferences"
})
public class Seating {

  @JsonProperty("description")
  private Object description;
  @JsonProperty("reservation")
  private String reservation;
  @JsonProperty("preferences")
  private List<Object> preferences = null;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("description")
  public Object getDescription() {
    return description;
  }

  @JsonProperty("description")
  public void setDescription(Object description) {
    this.description = description;
  }

  @JsonProperty("reservation")
  public String getReservation() {
    return reservation;
  }

  @JsonProperty("reservation")
  public void setReservation(String reservation) {
    this.reservation = reservation;
  }

  @JsonProperty("preferences")
  public List<Object> getPreferences() {
    return preferences;
  }

  @JsonProperty("preferences")
  public void setPreferences(List<Object> preferences) {
    this.preferences = preferences;
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
