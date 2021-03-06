package com.nsiapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.nsiapi.models.booking.Dnr;
import com.nsiapi.models.calendardates.Dates;
import com.nsiapi.models.calendarprices.Prices;
import com.nsiapi.models.connections.Connections;
import com.nsiapi.models.stations.Stations;
import com.nsiapi.requests.CalendarDateRequest;
import com.nsiapi.requests.CalendarPriceRequest;
import com.nsiapi.requests.PriceAndTimeRequest;
import com.nsiapi.requests.ProvisionalBookingRequest;
import com.nsiapi.requests.StationNameRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * This class is used to get response from the ns international api
 */
@SuppressWarnings("ConstantConditions")
public class NSIApi {

  private final String BASE_URL = "https://www.nsinternational.nl/api/v1.1/";
  private CloseableHttpClient client;

  /**
   * Returns a model containing connections corresponding to the request
   * @param PATRequest PriceAndTimeRequest
   * @return an object filled  with JSON data
   * @throws ParseException Throws this exception when JSON parsing fails or when the IO fails.
   */
  public Connections getResponse(PriceAndTimeRequest PATRequest) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(BASE_URL + PATRequest.getRequestUrl());
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
      Connections model = gson.fromJson(reader, Connections.class);
      client.close();
      return model;
    }catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    throw new ParseException("Nothing could be returned");
  }

  /**
   * Returns a model containing station names corresponding to the request
   * @param SNRequest StationNameRequest
   * @return an object filled  with JSON data
   * @throws ParseException Throws this exception when JSON parsing fails or when the IO fails.
   */
  public Stations getResponse(StationNameRequest SNRequest) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(BASE_URL + SNRequest.getRequestUrl());
    System.out.println(SNRequest.getRequestUrl());
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
      Stations model = gson.fromJson(reader, Stations.class);
      client.close();
      return model;
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    throw new ParseException("Nothing could be returned");
  }

  /**
   * Returns a model containing a signature and dnr ID for a provisional booking corresponding to the request
   * @param PBRequest ProvisionalBookingRequest
   * @return an object filled  with JSON data
   * @throws ParseException Throws this exception when JSON parsing fails or when the IO fails.
   */
  public Dnr getResponse(ProvisionalBookingRequest PBRequest) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpPost request = new HttpPost(BASE_URL + PBRequest.getRequestUrl());
    System.out.println(PBRequest.getRequestUrl());
    HttpResponse response = null;
    try {
      request.setEntity(new StringEntity(PBRequest.getRequestBody()));
      response = client.execute(request);
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    try {
      JsonReader reader = new JsonReader(
          new BufferedReader(new InputStreamReader(response.getEntity().getContent())));
      Dnr model = gson.fromJson(reader, Dnr.class);
      client.close();
      return model;
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    throw new ParseException("Nothing could be returned");
  }

  /**
   * Returns a model containing calendar dates corresponding to the request
   * @param CDRequest CalendarDateRequest
   * @return an object filled  with JSON data
   * @throws ParseException Throws this exception when JSON parsing fails or when the IO fails.
   */
  public Dates getResponse(CalendarDateRequest CDRequest) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(BASE_URL + CDRequest.getRequestUrl());
    System.out.println(CDRequest.getRequestUrl());
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
      Dates model = gson.fromJson(reader, Dates.class);
      client.close();
      return model;
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    throw new ParseException("Nothing could be returned");
  }

  /**
   * Returns a model containing calendar prices corresponding to the request
   * @param CPRequest CalendarPriceRequest
   * @return an object filled  with JSON data
   * @throws ParseException Throws this exception when JSON parsing fails or when the IO fails.
   */
  public Prices getResponse(CalendarPriceRequest CPRequest) throws ParseException {
    client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(BASE_URL + CPRequest.getRequestUrl());
    System.out.println(CPRequest.getRequestUrl());
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
      Prices model = gson.fromJson(reader, Prices.class);
      client.close();
      return model;
    } catch (IOException IoE) {
      System.out.println("IOException " + IoE.getMessage());
    }
    throw new ParseException("Nothing could be returned");
  }
}
