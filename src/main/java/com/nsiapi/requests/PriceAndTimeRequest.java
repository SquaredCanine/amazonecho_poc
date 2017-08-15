package com.nsiapi.requests;

import chooseCity.chooseCitySpeechlet;
import com.RequestInterface;
import com.database.models.CompositionModel;
import com.database.models.LocationsModel;
import java.util.ArrayList;

/**
 * Created by doombringer on 7/24/2017.
 */
public class PriceAndTimeRequest implements RequestInterface {

  private String originCode;
  private String destinationCode;
  private String date = "20170724";
  private String time = "1600";
  private String passengers;
  private String timetype;
  private String gotoUrl = "connections/";
  private boolean arrival = false;


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

  public PriceAndTimeRequest(String originCode, String destinationCode, String date, String time) {
    this.originCode = originCode;
    this.destinationCode = destinationCode;
    this.date = date;
    this.time = time;
    setPassengers();
    setTimetype();
  }

  private void setPassengers() {
    CompositionModel model = chooseCitySpeechlet.DB
        .getComposition(chooseCitySpeechlet.UNIQUE_USER_ID);
    passengers = "passengers=";
    if (model == null || model.getUID() == null || model.getUID().equals("")
        || model.getNumberOfPassengers() == 0) {
      passengers += "A,";
    } else {
      for (int i = 0; i < model.getNumberOfPassengers(); i++) {
        passengers += "A,";
      }
    }
  }

  private void setTimetype() {
    if (arrival) {
      timetype = "&timetype=arrival";
    } else {
      timetype = "&timetype=departure";
    }
  }

  private String getCode(String original) {
    ArrayList<LocationsModel> locations = chooseCitySpeechlet.DB
        .getLocations(chooseCitySpeechlet.UNIQUE_USER_ID);
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

  public String getRequestUrl() {
    System.out.println(
        gotoUrl + originCode + "/" + destinationCode + "/" + date + "/" + time + "/outbound?"
            + passengers + timetype);
    return (gotoUrl + originCode + "/" + destinationCode + "/" + date + "/" + time + "/outbound?"
        + passengers + timetype);
  }

  public String getOriginCode() {
    return originCode;
  }

  public String getDestinationCode() {
    return destinationCode;
  }
}
