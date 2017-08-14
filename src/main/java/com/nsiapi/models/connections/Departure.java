
package com.nsiapi.models.connections;

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
    "planned",
    "delay",
    "platform",
    "updatedPlatform"
})
public class Departure {

    @JsonProperty("planned")
    private String planned;
    @JsonProperty("delay")
    private Integer delay;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("updatedPlatform")
    private Object updatedPlatform;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("planned")
    public String getPlanned() {
        return planned;
    }

    @JsonProperty("planned")
    public void setPlanned(String planned) {
        this.planned = planned;
    }

    @JsonProperty("delay")
    public Integer getDelay() {
        return delay;
    }

    @JsonProperty("delay")
    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    @JsonProperty("platform")
    public String getPlatform() {
        return platform;
    }

    @JsonProperty("platform")
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @JsonProperty("updatedPlatform")
    public Object getUpdatedPlatform() {
        return updatedPlatform;
    }

    @JsonProperty("updatedPlatform")
    public void setUpdatedPlatform(Object updatedPlatform) {
        this.updatedPlatform = updatedPlatform;
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
