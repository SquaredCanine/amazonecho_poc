package com.database.models;

/**
 * Database table 'Journey' data is put in this object.
 */
public class JourneyModel {

  private String UID;
  private String identifier;
  private String originCode;
  private String destinationCode;
  private String departuretime;
  private String departuredate;
  private String orderprice;

  /**
   * Constructor
   * @param identifier String containing a number
   * @param originCode String containing the station code origin
   * @param destinationCode String containing the station code destination
   * @param departuretime String containing the departure time from the origin
   * @param departuredate String containing the departure date from the origin
   * @param orderprice String containing the orderPrice
   */
  public JourneyModel(String identifier, String originCode, String destinationCode,
      String departuretime, String departuredate, String orderprice) {
    this.identifier = identifier;
    this.originCode = originCode;
    this.destinationCode = destinationCode;
    this.departuretime = departuretime;
    this.departuredate = departuredate;
    this.orderprice = orderprice;
  }

  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
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

  public String getDeparturetime() {
    return departuretime;
  }

  public void setDeparturetime(String departuretime) {
    this.departuretime = departuretime;
  }

  public String getDeparturedate() {
    return departuredate;
  }

  public void setDeparturedate(String departuredate) {
    this.departuredate = departuredate;
  }

  public String getOrderprice() {
    return orderprice;
  }

  public void setOrderprice(String orderprice) {
    this.orderprice = orderprice;
  }
}
