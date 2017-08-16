package com.amazonapi.requests;

import com.RequestInterface;

public class GetUserRequest implements RequestInterface {

  private String accessToken;

  public GetUserRequest(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getRequestUrl() {
    return accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
