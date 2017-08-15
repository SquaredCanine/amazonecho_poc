/**
 * Created by doombringer on 7/27/2017.
 */
package com.database.models;

public class UsersModel {

  private String UID;
  private String name;
  private String email;

  public UsersModel(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
