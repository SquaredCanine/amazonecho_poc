package com.nsiapi.requests;

import com.RequestInterface;

/**
 * This request is used to find available dates for booking a journey
 */
@SuppressWarnings("FieldCanBeLocal")
public class CalendarDateRequest implements RequestInterface {

  private final String destinationCode;
  private final String originCode;
  private final String goToUrl = "calendardates/";

  /**
   * Constructs the request
   * @param destinationCode String containing the station code of the destination
   * @param originCode String containing the station code of the origin
   */
  public CalendarDateRequest(String destinationCode, String originCode) {
    this.destinationCode = destinationCode;
    this.originCode = originCode;
  }

  /**
   * Returns a URL to get the Calendar dates from the API
   * @return A String containg the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getRequestUrl() {
    return (goToUrl + originCode + "/" + destinationCode + "/outbound?lang=nl");
  }
}
