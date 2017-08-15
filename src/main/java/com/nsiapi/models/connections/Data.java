package com.nsiapi.models.connections;

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
    "connections",
    "messages",
    "outboundOfferId",
    "outboundConnectionId",
    "prevAvailable",
    "nextAvailable",
    "travelId",
    "uid"
})
public class Data {

  @JsonProperty("connections")
  private List<Connection> connections = null;
  @JsonProperty("messages")
  private List<Object> messages = null;
  @JsonProperty("outboundOfferId")
  private String outboundOfferId;
  @JsonProperty("outboundConnectionId")
  private String outboundConnectionId;
  @JsonProperty("prevAvailable")
  private String prevAvailable;
  @JsonProperty("nextAvailable")
  private String nextAvailable;
  @JsonProperty("travelId")
  private String travelId;
  @JsonProperty("uid")
  private String uid;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

  @JsonProperty("messages")
  public List<Object> getMessages() {
    return messages;
  }

  @JsonProperty("messages")
  public void setMessages(List<Object> messages) {
    this.messages = messages;
  }

  @JsonProperty("outboundOfferId")
  public String getOutboundOfferId() {
    return outboundOfferId;
  }

  @JsonProperty("outboundOfferId")
  public void setOutboundOfferId(String outboundOfferId) {
    this.outboundOfferId = outboundOfferId;
  }

  @JsonProperty("outboundConnectionId")
  public String getOutboundConnectionId() {
    return outboundConnectionId;
  }

  @JsonProperty("outboundConnectionId")
  public void setOutboundConnectionId(String outboundConnectionId) {
    this.outboundConnectionId = outboundConnectionId;
  }

  @JsonProperty("prevAvailable")
  public String getPrevAvailable() {
    return prevAvailable;
  }

  @JsonProperty("prevAvailable")
  public void setPrevAvailable(String prevAvailable) {
    this.prevAvailable = prevAvailable;
  }

  @JsonProperty("nextAvailable")
  public String getNextAvailable() {
    return nextAvailable;
  }

  @JsonProperty("nextAvailable")
  public void setNextAvailable(String nextAvailable) {
    this.nextAvailable = nextAvailable;
  }

  @JsonProperty("travelId")
  public String getTravelId() {
    return travelId;
  }

  @JsonProperty("travelId")
  public void setTravelId(String travelId) {
    this.travelId = travelId;
  }

  @JsonProperty("uid")
  public String getUid() {
    return uid;
  }

  @JsonProperty("uid")
  public void setUid(String uid) {
    this.uid = uid;
  }

  @JsonProperty("connections")
  public List<Connection> getConnections() {
    return connections;
  }

  @JsonProperty("connections")
  public void setConnections(List<Connection> connections) {
    this.connections = connections;
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
