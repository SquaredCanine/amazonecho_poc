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
    "currency",
    "amount",
    "passenger",
    "ticket",
    "reservation"
})
public class SalesPrice {

  @JsonProperty("currency")
  private String currency;
  @JsonProperty("amount")
  private String amount;
  @JsonProperty("passenger")
  private Passenger passenger;
  @JsonProperty("ticket")
  private Ticket ticket;
  @JsonProperty("reservation")
  private Object reservation;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(String currency) {
    this.currency = currency;
  }

  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(String amount) {
    this.amount = amount;
  }

  @JsonProperty("passenger")
  public Passenger getPassenger() {
    return passenger;
  }

  @JsonProperty("passenger")
  public void setPassenger(Passenger passenger) {
    this.passenger = passenger;
  }

  @JsonProperty("ticket")
  public Ticket getTicket() {
    return ticket;
  }

  @JsonProperty("ticket")
  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
  }

  @JsonProperty("reservation")
  public Object getReservation() {
    return reservation;
  }

  @JsonProperty("reservation")
  public void setReservation(Object reservation) {
    this.reservation = reservation;
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
