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
    "dates",
    "lastCalendarDate",
    "messages",
    "pricesAvailable",
    "showCalendarBlockadePhoneNumber"
})
public class Data {

  @JsonProperty("dates")
  private List<Date> dates = null;
  @JsonProperty("lastCalendarDate")
  private String lastCalendarDate;
  @JsonProperty("messages")
  private Messages messages;
  @JsonProperty("pricesAvailable")
  private Boolean pricesAvailable;
  @JsonProperty("showCalendarBlockadePhoneNumber")
  private Boolean showCalendarBlockadePhoneNumber;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("dates")
  public List<Date> getDates() {
    return dates;
  }

  @JsonProperty("dates")
  public void setDates(List<Date> dates) {
    this.dates = dates;
  }

  @JsonProperty("lastCalendarDate")
  public String getLastCalendarDate() {
    return lastCalendarDate;
  }

  @JsonProperty("lastCalendarDate")
  public void setLastCalendarDate(String lastCalendarDate) {
    this.lastCalendarDate = lastCalendarDate;
  }

  @JsonProperty("messages")
  public Messages getMessages() {
    return messages;
  }

  @JsonProperty("messages")
  public void setMessages(Messages messages) {
    this.messages = messages;
  }

  @JsonProperty("pricesAvailable")
  public Boolean getPricesAvailable() {
    return pricesAvailable;
  }

  @JsonProperty("pricesAvailable")
  public void setPricesAvailable(Boolean pricesAvailable) {
    this.pricesAvailable = pricesAvailable;
  }

  @JsonProperty("showCalendarBlockadePhoneNumber")
  public Boolean getShowCalendarBlockadePhoneNumber() {
    return showCalendarBlockadePhoneNumber;
  }

  @JsonProperty("showCalendarBlockadePhoneNumber")
  public void setShowCalendarBlockadePhoneNumber(Boolean showCalendarBlockadePhoneNumber) {
    this.showCalendarBlockadePhoneNumber = showCalendarBlockadePhoneNumber;
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
