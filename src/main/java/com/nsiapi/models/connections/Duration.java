package com.nsiapi.models.connections;

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
    "days",
    "hours",
    "minutes",
    "delay"
})
public class Duration {

  @JsonProperty("days")
  private Integer days;
  @JsonProperty("hours")
  private Integer hours;
  @JsonProperty("minutes")
  private Integer minutes;
  @JsonProperty("delay")
  private Boolean delay;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("days")
  public Integer getDays() {
    return days;
  }

  @JsonProperty("days")
  public void setDays(Integer days) {
    this.days = days;
  }

  @JsonProperty("hours")
  public Integer getHours() {
    return hours;
  }

  @JsonProperty("hours")
  public void setHours(Integer hours) {
    this.hours = hours;
  }

  @JsonProperty("minutes")
  public Integer getMinutes() {
    return minutes;
  }

  @JsonProperty("minutes")
  public void setMinutes(Integer minutes) {
    this.minutes = minutes;
  }

  @JsonProperty("delay")
  public Boolean getDelay() {
    return delay;
  }

  @JsonProperty("delay")
  public void setDelay(Boolean delay) {
    this.delay = delay;
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
