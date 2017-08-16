package com.nsiapi.requests;

import com.RequestInterface;

/**
 * This request is used to find all available prices for a journey
 */
@SuppressWarnings("FieldCanBeLocal")
public class CalendarPriceRequest implements RequestInterface {

  private final String destinationCode;
  private final String originCode;
  private final String goToUrl = "calendarprices/";

  /**
   * Constructs the request
   * @param destinationCode String containing the station code of the destination
   * @param originCode String containing the station code of the origin
   */
  public CalendarPriceRequest(String destinationCode, String originCode) {
    this.destinationCode = destinationCode;
    this.originCode = originCode;
  }

  /**
   * Returns a URL to get the Calendar prices from the API
   * @return A String containing the correct URL for the API
   * {@link com.RequestInterface#getRequestUrl()}
   */
  public String getRequestUrl() {
    return (goToUrl + originCode + "/" + destinationCode + "/outbound?lang=nl");
  }
}
