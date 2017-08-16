package com.nsiapi.requests;

import com.RequestInterface;

/**
 * This request is used to find a station Code and name corresponding to the entered city name.
 */
@SuppressWarnings("FieldCanBeLocal")
public class StationNameRequest implements RequestInterface {

  private final String city;
  private final String gotoUrl = "stations/";

  /**
   * Sets the Request
   * @param city String containing the city name
   */
  public StationNameRequest(String city) {
    this.city = city;
  }

  /**
   * Returns a URL to find a station name and code from the API
   * @return A String containing the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getRequestUrl() {
    return (gotoUrl + "?name=" + city);
  }
}
