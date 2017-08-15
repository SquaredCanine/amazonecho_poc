package com.nsiapi.requests;

import com.RequestInterface;

@SuppressWarnings("FieldCanBeLocal")
public class CalendarDateRequest implements RequestInterface {

  private final String destinationCode;
  private final String originCode;
  private final String goToUrl = "calendardates/";

  public CalendarDateRequest(String destinationCode, String originCode) {
    this.destinationCode = destinationCode;
    this.originCode = originCode;
  }

  public String getRequestUrl() {
    return (goToUrl + originCode + "/" + destinationCode + "/outbound?lang=nl");
  }
}
