package com.nsiapi.requests;

import chooseCity.chooseCitySpeechlet;
import com.database.models.CompositionModel;
import com.requestInterface;

public class ProvisionalBookingRequest implements requestInterface {
    private String userID;
    private String connectionID;
    private String offerID;
    private String passengers;
    private String seatreservation = "true";
    private String originCode;
    private String destinationCode;
    private String gotoUrl = "bookings/provision/";
    private String alternateGotoUrl = "bookings/alternative/";

    public ProvisionalBookingRequest(String userID, String connectionID, String offerID, String seatreservation, String originCode, String destinationCode) {
        this.userID = userID;
        this.connectionID = connectionID;
        this.offerID = offerID;
        this.seatreservation = seatreservation;
        this.originCode = originCode;
        this.destinationCode = destinationCode;
        setPassengers();
    }

    private void setPassengers(){
        CompositionModel model = chooseCitySpeechlet.DB.getComposition(chooseCitySpeechlet.UNIQUE_USER_ID);
        passengers = "";
        if(model == null || model.getUID() == null || model.getUID().equals("") || model.getNumberOfPassengers() == 0){
            passengers += "A,";
        }else {
            for (int i = 0; i < model.getNumberOfPassengers(); i++) {
                passengers += "A,";
            }
        }
    }

    public String getRequestUrl(){
        return (gotoUrl + userID + "?origin="
                + originCode + "&destination="
                + destinationCode + "&lang=nl");
    }

    public String getAlternateRequestUrl(){
        return (alternateGotoUrl + userID + "?origin="
                + originCode + "&destination="
                + destinationCode + "&lang=nl");
    }

    public String getRequestBody(){
        return ("{\"outbound\":" +
                "{\"connectionId\":\"" + connectionID + "\",\"offerId\":\"" + offerID + "\",\"seatReservation\":" + seatreservation + "},\"passengers\":\"" + passengers + "\"}");
    }
    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public String getPassengers() {
        return passengers;
    }

    public void setPassengers(String passengers) {
        this.passengers = passengers;
    }

    public String getSeatreservation() {
        return seatreservation;
    }

    public void setSeatreservation(String seatreservation) {
        this.seatreservation = seatreservation;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOriginCode() {
        return originCode;
    }

    public void setOriginCode(String originCode) {
        this.originCode = originCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String gotoUrl) {
        this.gotoUrl = gotoUrl;
    }

    public String getAlternateGotoUrl() {
        return alternateGotoUrl;
    }

    public void setAlternateGotoUrl(String alternateGotoUrl) {
        this.alternateGotoUrl = alternateGotoUrl;
    }
}
