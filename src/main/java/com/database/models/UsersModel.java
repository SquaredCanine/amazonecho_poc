package com.database.models;

/**
 * Database table 'Users' data is put in this object.
 */
public class UsersModel {

  private String UID;
  private String name;
  private String email;

  /**
   * Constructor
   * @param name String containing the first and last name of the user
   * @param email String containing the email of the user
   */
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
