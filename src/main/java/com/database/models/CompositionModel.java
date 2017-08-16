package com.database.models;

/**
 * Database table 'Composition' data is put in this object.
 */
public class CompositionModel {

  private String UID;
  private int numberOfPassengers;

  /**
   * Constructor
   * @param numberOfPassengers number of passengers
   */
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
