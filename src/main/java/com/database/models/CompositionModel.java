package com.database.models;

public class CompositionModel {

  private String UID;
  private int numberOfPassengers;

  public CompositionModel(int numberOfPassengers) {
    this.numberOfPassengers = numberOfPassengers;
  }

  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
  }

  public int getNumberOfPassengers() {
    return numberOfPassengers;
  }

  public void setNumberOfPassengers(int numberOfPassengers) {
    this.numberOfPassengers = numberOfPassengers;
  }
}
