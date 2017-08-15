package com.nsiapi.models.calendarprices;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("CanBeFinal")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "periods"
})
public class Departure {

  @JsonProperty("periods")
  private List<Period_> periods = null;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

  @JsonProperty("periods")
  public List<Period_> getPeriods() {
    return periods;
  }

  @JsonProperty("periods")
  public void setPeriods(List<Period_> periods) {
    this.periods = periods;
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
