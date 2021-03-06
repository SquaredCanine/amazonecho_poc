package com.database.models;

/**
 * Database table 'Locations' data is put in this object.
 */
public class LocationsModel {

  private String UID;
  private String identifier;
  private String city;
  private String stationCode;

  /**
   * Constructor
   * @param identifier String containing the identifier of the location
   * @param city String containing the city name of the location
   * @param stationCode String containing the station code of the location
   */
  public LocationsModel(String identifier, String city, String stationCode) {
    this.identifier = identifier;
    this.city = city;
    this.stationCode = stationCode;
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

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStationCode() {
    return stationCode;
  }

  public void setStationCode(String stationCode) {
    this.stationCode = stationCode;
  }
}
