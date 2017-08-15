package com.nsiapi.models.calendardates;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "startDate",
    "endDate",
    "title",
    "content"
})
public class End {

  @JsonProperty("type")
  private String type;
  @JsonProperty("startDate")
  private String startDate;
  @JsonProperty("endDate")
  private String endDate;
  @JsonProperty("title")
  private String title;
  @JsonProperty("content")
  private String content;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("startDate")
  public String getStartDate() {
    return startDate;
  }

  @JsonProperty("startDate")
  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @JsonProperty("endDate")
  public String getEndDate() {
    return endDate;
  }

  @JsonProperty("endDate")
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  @JsonProperty("title")
  public void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty("content")
  public String getContent() {
    return content;
  }

  @JsonProperty("content")
  public void setContent(String content) {
    this.content = content;
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
