
package com.nsiapi.models.connections;

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
    "id",
    "status",
    "origin",
    "destination",
    "duration",
    "transfers",
    "modalities",
    "messages",
    "bndsCode",
    "offers"
})
public class Connection {

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private String status;
    @JsonProperty("origin")
    private Origin origin;
    @JsonProperty("destination")
    private Destination destination;
    @JsonProperty("duration")
    private Duration duration;
    @JsonProperty("transfers")
    private Integer transfers;
    @JsonProperty("modalities")
    private List<Modality> modalities = null;
    @JsonProperty("messages")
    private List<Object> messages = null;
    @JsonProperty("bndsCode")
    private String bndsCode;
    @JsonProperty("offers")
    private List<Offer> offers = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getArrivalTime(){
        return getDestination().getArrival().getPlanned().split(" ")[1];
    }
    public String getDepartureTime(){
        return getOrigin().getDeparture().getPlanned().split(" ")[1];
    }
    public String getArrivalDate(){
        return getDestination().getArrival().getPlanned().split(" ")[0];
    }
    public String getDepartureDate(){
        return getOrigin().getDeparture().getPlanned().split(" ")[0];
    }

    public String getDBArrivalTime(){
        return getDestination().getArrival().getPlanned().split(" ")[1].replace(":","");
    }
    public String getDBDepartureTime(){
        return getOrigin().getDeparture().getPlanned().split(" ")[1].replace(":","");
    }
    public String getDBArrivalDate(){
        return getDestination().getArrival().getPlanned().split(" ")[0].replace("-","");
    }
    public String getDBDepartureDate(){
        return getOrigin().getDeparture().getPlanned().split(" ")[0].replace("-","");
    }

    public String getJourneySummary(){
        return "You will depart at " + getOrigin().getDeparture().getPlanned().split(" ")[1] +
                " , from " + getOrigin().getName() +
                ". Your journey will be " + getDuration().getHours() + " hours and " + getDuration().getMinutes() + " minutes long. " +
                " And you will arrive on " + getDestination().getArrival().getPlanned().split(" ")[1] +
                " at " + getDestination().getName() + ". ";
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
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

    @JsonProperty("duration")
    public Duration getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @JsonProperty("transfers")
    public Integer getTransfers() {
        return transfers;
    }

    @JsonProperty("transfers")
    public void setTransfers(Integer transfers) {
        this.transfers = transfers;
    }

    @JsonProperty("modalities")
    public List<Modality> getModalities() {
        return modalities;
    }

    @JsonProperty("modalities")
    public void setModalities(List<Modality> modalities) {
        this.modalities = modalities;
    }

    @JsonProperty("messages")
    public List<Object> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<Object> messages) {
        this.messages = messages;
    }

    @JsonProperty("bndsCode")
    public String getBndsCode() {
        return bndsCode;
    }

    @JsonProperty("bndsCode")
    public void setBndsCode(String bndsCode) {
        this.bndsCode = bndsCode;
    }

    @JsonProperty("offers")
    public List<Offer> getOffers() {
        return offers;
    }

    @JsonProperty("offers")
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
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
