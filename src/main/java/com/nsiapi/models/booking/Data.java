
package com.nsiapi.models.booking;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dnrId",
    "signature",
    "created",
    "messages"
})
public class Data {

    @JsonProperty("dnrId")
    private String dnrId;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("created")
    private String created;
    @JsonProperty("messages")
    private Object messages;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("dnrId")
    public String getDnrId() {
        return dnrId;
    }

    @JsonProperty("dnrId")
    public void setDnrId(String dnrId) {
        this.dnrId = dnrId;
    }

    @JsonProperty("signature")
    public String getSignature() {
        return signature;
    }

    @JsonProperty("signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @JsonProperty("created")
    public String getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(String created) {
        this.created = created;
    }

    @JsonProperty("messages")
    public Object getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(Object messages) {
        this.messages = messages;
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
