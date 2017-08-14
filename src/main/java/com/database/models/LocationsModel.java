/**
 * Created by doombringer on 7/27/2017.
 */
package com.database.models;
public class LocationsModel {

    private String UID;
    private String identifier;
    private String city;
    private String stationCode;

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
