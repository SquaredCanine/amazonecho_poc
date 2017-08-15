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
    "amount",
    "count",
    "passenger"
})
public class Ticket {

  @JsonProperty("amount")
  private String amount;
  @JsonProperty("count")
  private Object count;
  @JsonProperty("passenger")
  private Passenger_ passenger;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(String amount) {
    this.amount = amount;
  }

  @JsonProperty("count")
  public Object getCount() {
    return count;
  }

  @JsonProperty("count")
  public void setCount(Object count) {
    this.count = count;
  }

  @JsonProperty("passenger")
  public Passenger_ getPassenger() {
    return passenger;
  }

  @JsonProperty("passenger")
  public void setPassenger(Passenger_ passenger) {
    this.passenger = passenger;
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
