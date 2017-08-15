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
    "id",
    "comfortClass",
    "availability",
    "salesPrice",
    "tariff"
})
public class Offer {

  @JsonProperty("id")
  private String id;
  @JsonProperty("comfortClass")
  private String comfortClass;
  @JsonProperty("availability")
  private Integer availability;
  @JsonProperty("salesPrice")
  private SalesPrice salesPrice;
  @JsonProperty("tariff")
  private Tariff tariff;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("comfortClass")
  public String getComfortClass() {
    return comfortClass;
  }

  @JsonProperty("comfortClass")
  public void setComfortClass(String comfortClass) {
    this.comfortClass = comfortClass;
  }

  @JsonProperty("availability")
  public Integer getAvailability() {
    return availability;
  }

  @JsonProperty("availability")
  public void setAvailability(Integer availability) {
    this.availability = availability;
  }

  @JsonProperty("salesPrice")
  public SalesPrice getSalesPrice() {
    return salesPrice;
  }

  @JsonProperty("salesPrice")
  public void setSalesPrice(SalesPrice salesPrice) {
    this.salesPrice = salesPrice;
  }

  @JsonProperty("tariff")
  public Tariff getTariff() {
    return tariff;
  }

  @JsonProperty("tariff")
  public void setTariff(Tariff tariff) {
    this.tariff = tariff;
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
