package com.nsiapi.requests;

import chooseCity.chooseCitySpeechlet;
import com.RequestInterface;
import com.database.models.CompositionModel;

/**
 * This request is used to create a provisional booking for the selected offer and connection.
 */
@SuppressWarnings("StringConcatenationInLoop")
public class ProvisionalBookingRequest implements RequestInterface {

  private String userID;
  private String connectionID;
  private String offerID;
  private String passengers;
  private String seatreservation = "true";
  private String originCode;
  private String destinationCode;
  private String gotoUrl = "bookings/provision/";
  private String alternateGotoUrl = "bookings/alternative/";

  /**
   * Sets the requests and body
   * @param userID String containing the user ID of NS
   * @param connectionID String containing the selected Connection
   * @param offerID String containing the selected Offer
   * @param seatreservation String containing true or false, default is true.
   * @param originCode String containing the station code of the origin
   * @param destinationCode String containing the station code of the destination
   */
  public ProvisionalBookingRequest(String userID, String connectionID, String offerID,
      String seatreservation, String originCode, String destinationCode) {
    this.userID = userID;
    this.connectionID = connectionID;
    this.offerID = offerID;
    this.seatreservation = seatreservation;
    this.originCode = originCode;
    this.destinationCode = destinationCode;
    setPassengers();
  }

  /**
   * Sets the amount of passengers according to the database, if the database does not contain an amount of passengers.
   * Then 1 passenger is selected.
   */
  private void setPassengers() {
    CompositionModel model = chooseCitySpeechlet.DB
        .getComposition(chooseCitySpeechlet.UNIQUE_USER_ID);
    passengers = "";
    if (model == null || model.getUID() == null || model.getUID().equals("")
        || model.getNumberOfPassengers() == 0) {
      passengers += "A,";
    } else {
      for (int i = 0; i < model.getNumberOfPassengers(); i++) {
        passengers += "A,";
      }
    }
  }

  /**
   * Returns a URL to create a provisionalBooking using the API
   * @return A String containg the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getRequestUrl() {
    return (gotoUrl + userID + "?origin="
        + originCode + "&destination="
        + destinationCode + "&lang=nl");
  }

  /**
   * Returns an Alternate URL to create a provisionalBooking using the API
   * @return A String containg the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getAlternateRequestUrl() {
    return (alternateGotoUrl + userID + "?origin="
        + originCode + "&destination="
        + destinationCode + "&lang=nl");
  }

  /**
   * Returns the body for the request for creating a provisional booking.
   * @return String containing the body for the request.
   */
  public String getRequestBody() {
    return ("{\"outbound\":" +
        "{\"connectionId\":\"" + connectionID + "\",\"offerId\":\"" + offerID
        + "\",\"seatReservation\":" + seatreservation + "},\"passengers\":\"" + passengers + "\"}");
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
