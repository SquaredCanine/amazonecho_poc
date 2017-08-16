package com.amazonapi;

import com.amazonapi.models.AmazonUser;
import com.amazonapi.requests.GetUserRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * This class is used for Amazon API calls
 */
@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class AmazonApi {

  private final String BASE_URL = "https://api.amazon.com/user/profile?access_token=";
  private CloseableHttpClient client;

  /**
   *
   * @param GUR GetUserRequest request
   * @return a model containing information about the amazon user
   * @throws ParseException Throws a ParsException when nothing can be returned.
   */
  public AmazonUser getResponse(GetUserRequest GUR) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpGet request = null;
    try {
      request = new HttpGet(BASE_URL + URLEncoder.encode(GUR.getRequestUrl(), "UTF-8"));
    } catch (UnsupportedEncodingException UEE) {
      System.out.println("Problems with URL Encoding: " + UEE.getMessage());
    }
    HttpResponse response = null;
    try {
      response = client.execute(request);
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    try {
      JsonReader reader = new JsonReader(
          new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
      AmazonUser model = gson.fromJson(reader, AmazonUser.class);
      client.close();
      return model;
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());

    }
    throw new ParseException("Nothing could be returned");
  }
}
