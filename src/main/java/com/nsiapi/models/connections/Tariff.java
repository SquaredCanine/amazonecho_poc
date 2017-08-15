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
    "returnType",
    "premierClass"
})
public class Tariff {

  @JsonProperty("returnType")
  private Object returnType;
  @JsonProperty("premierClass")
  private Boolean premierClass;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("returnType")
  public Object getReturnType() {
    return returnType;
  }

  @JsonProperty("returnType")
  public void setReturnType(Object returnType) {
    this.returnType = returnType;
  }

  @JsonProperty("premierClass")
  public Boolean getPremierClass() {
    return premierClass;
  }

  @JsonProperty("premierClass")
  public void setPremierClass(Boolean premierClass) {
    this.premierClass = premierClass;
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
