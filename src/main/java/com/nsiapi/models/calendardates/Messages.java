package com.nsiapi.models.calendardates;

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
    "days",
    "periods",
    "end",
    "blockades"
})
public class Messages {

  @JsonProperty("days")
  private List<Object> days = null;
  @JsonProperty("periods")
  private List<Object> periods = null;
  @JsonProperty("end")
  private List<End> end = null;
  @JsonProperty("blockades")
  private Object blockades;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("days")
  public List<Object> getDays() {
    return days;
  }

  @JsonProperty("days")
  public void setDays(List<Object> days) {
    this.days = days;
  }

  @JsonProperty("periods")
  public List<Object> getPeriods() {
    return periods;
  }

  @JsonProperty("periods")
  public void setPeriods(List<Object> periods) {
    this.periods = periods;
  }

  @JsonProperty("end")
  public List<End> getEnd() {
    return end;
  }

  @JsonProperty("end")
  public void setEnd(List<End> end) {
    this.end = end;
  }

  @JsonProperty("blockades")
  public Object getBlockades() {
    return blockades;
  }

  @JsonProperty("blockades")
  public void setBlockades(Object blockades) {
    this.blockades = blockades;
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
