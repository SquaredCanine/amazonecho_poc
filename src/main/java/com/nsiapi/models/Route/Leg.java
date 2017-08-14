
package com.nsiapi.models.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "modality",
    "duration",
    "origin",
    "destination",
    "stops",
    "messages",
    "seating",
    "overBooking",
    "tacos"
})
public class Leg {

    @JsonProperty("modality")
    private Modality modality;
    @JsonProperty("duration")
    private Duration duration;
    @JsonProperty("origin")
    private Origin origin;
    @JsonProperty("destination")
    private Destination destination;
    @JsonProperty("stops")
    private List<Stop> stops = null;
    @JsonProperty("messages")
    private List<Object> messages = null;
    @JsonProperty("seating")
    private Seating seating;
    @JsonProperty("overBooking")
    private Boolean overBooking;
    @JsonProperty("tacos")
    private List<Object> tacos = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("modality")
    public Modality getModality() {
        return modality;
    }

    @JsonProperty("modality")
    public void setModality(Modality modality) {
        this.modality = modality;
    }

    @JsonProperty("duration")
    public Duration getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @JsonProperty("origin")
    public Origin getOrigin() {
        return origin;
    }

    @JsonProperty("origin")
    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    @JsonProperty("destination")
    public Destination getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @JsonProperty("stops")
    public List<Stop> getStops() {
        return stops;
    }

    @JsonProperty("stops")
    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    @JsonProperty("messages")
    public List<Object> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<Object> messages) {
        this.messages = messages;
    }

    @JsonProperty("seating")
    public Seating getSeating() {
        return seating;
    }

    @JsonProperty("seating")
    public void setSeating(Seating seating) {
        this.seating = seating;
    }

    @JsonProperty("overBooking")
    public Boolean getOverBooking() {
        return overBooking;
    }

    @JsonProperty("overBooking")
    public void setOverBooking(Boolean overBooking) {
        this.overBooking = overBooking;
    }

    @JsonProperty("tacos")
    public List<Object> getTacos() {
        return tacos;
    }

    @JsonProperty("tacos")
    public void setTacos(List<Object> tacos) {
        this.tacos = tacos;
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
