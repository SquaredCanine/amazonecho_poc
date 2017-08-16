package com.nsiapi.requests;

import nl.nsi.demo.echo.NSInternationalSpeechlet;
import com.RequestInterface;
import com.database.models.CompositionModel;
import com.database.models.LocationsModel;
import java.util.ArrayList;

/**
 * This request is used to find connections corresponding to the origin, destination, date and time.
 */
@SuppressWarnings({"FieldCanBeLocal", "StringConcatenationInLoop"})
public class PriceAndTimeRequest implements RequestInterface {

  private final String originCode;
  private final String destinationCode;
  private String date = "20170724";
  private String time = "1600";
  private String passengers;
  private String timetype;
  private final String gotoUrl = "connections/";
  private boolean arrival = false;

  /**
   * Sets the request
   * @param origin name of the origin
   * @param destination name of the destination
   * @param date String in the format YYYYMMDD
   * @param time String in the format HHmm
   * @param arrival set the boolean to true if the date and time are based upon arrival
   */
  public PriceAndTimeRequest(String origin, String destination, String date, String time,
      boolean arrival) {
    this.date = date;
    this.time = time;
    this.arrival = arrival;
    originCode = getCode(origin);
    destinationCode = getCode(destination);
    setPassengers();
    setTimetype();
  }

  /**
   * Sets the request
   * @param originCode String containing the station code of the origin
   * @param destinationCode String containing the station code of the destination
   * @param date String in the format YYYYMMDD
   * @param time String in the format HHmm
   */
  public PriceAndTimeRequest(String originCode, String destinationCode, String date, String time) {
    this.originCode = originCode;
    this.destinationCode = destinationCode;
    this.date = date;
    this.time = time;
    setPassengers();
    setTimetype();
  }

  /**
   * Sets the amount of passengers according to the database, if the database does not contain an amount of passengers.
   * Then 1 passenger is selected.
   */
  private void setPassengers() {
    CompositionModel model = NSInternationalSpeechlet.DB
        .getComposition(NSInternationalSpeechlet.UNIQUE_USER_ID);
    passengers = "passengers=";
    if (model == null || model.getNumberOfPassengers() == 0) {
      passengers += "A,";
    } else {
      for (int i = 0; i < model.getNumberOfPassengers(); i++) {
        passengers += "A,";
      }
    }
  }

  /**
   * Sets the timetype according to the boolean.
   */
  private void setTimetype() {
    if (arrival) {
      timetype = "&timetype=arrival";
    } else {
      timetype = "&timetype=departure";
    }
  }

  /**
   * Converts a city name(or location like home or work) to a stationcode.
   * @param original String containing the city name
   * @return String containing the station code, default is "NLASC" (Amsterdam Centraal/Amsterdam)
   */
  private String getCode(String original) {
    ArrayList<LocationsModel> locations = NSInternationalSpeechlet.DB
        .getLocations(NSInternationalSpeechlet.UNIQUE_USER_ID);
    switch (original) {
      case "Berlin":
        System.out.println("DEBHF");
        return "DEBHF";
      case "Amsterdam":
        System.out.println("NLASC");
        return "NLASC";
      case "Paris":
        System.out.println("FRPAR");
        return "FRPAR";
      case "London":
        System.out.println("GBSPX");
        return "GBSPX";
      case "home":
        for (LocationsModel element : locations) {
          if (element.getIdentifier().equals("home")) {
            System.out.println("home found");
            return element.getStationCode();
          }
        }
      case "work":
        for (LocationsModel element : locations) {
          if (element.getIdentifier().equals("work")) {
            System.out.println("work found");
            return element.getStationCode();
          }
        }
      default:
        return "NLASC";
    }
  }

  /**
   * Returns a URL to get the Connections from the API
   * @return A String containing the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getRequestUrl() {
    System.out.println(
        gotoUrl + originCode + "/" + destinationCode + "/" + date + "/" + time + "/outbound?"
            + passengers + timetype);
    return (gotoUrl + originCode + "/" + destinationCode + "/" + date + "/" + time + "/outbound?"
        + passengers + timetype);
  }

  /**
   * Getter for the originCode
   * @return String originCode
   */
  public String getOriginCode() {
    return originCode;
  }

  /**
   * Getter for the destinationCode
   * @return String destinationCode
   */
  public String getDestinationCode() {
    return destinationCode;
  }
}
