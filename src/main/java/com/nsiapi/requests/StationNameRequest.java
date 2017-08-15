package com.nsiapi.requests;

import com.RequestInterface;

/**
 * Created by doombringer on 7/31/2017.
 */
@SuppressWarnings("FieldCanBeLocal")
public class StationNameRequest implements RequestInterface {

  private final String city;
  private final String gotoUrl = "stations/";

  public StationNameRequest(String city) {
    this.city = city;
  }

  public String getRequestUrl() {
    return (gotoUrl + "?name=" + city);
  }
}
