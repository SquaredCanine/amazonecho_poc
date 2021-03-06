package com.database;

import com.database.models.CompositionModel;
import com.database.models.JourneyModel;
import com.database.models.LocationsModel;
import com.database.models.UsersModel;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.nsi.demo.echo.NSInternationalSpeechlet;

/**
 * This class is used for all the database modifications like adding,updating and removing
 * aUID stands for amazon USER ID or UNIQUE_USER_ID
 */
public class Database {

  private java.sql.Connection con = null;
  private final String host = System.getenv("location_DB");
  private final String uName = System.getenv("username_DB");
  private final String uPass = System.getenv("password_DB");
  private ResultSet rs = null;
  private int ri = 0;

  public Database() {
    this.connect();
  }

  private void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      con = DriverManager.getConnection(host, uName, uPass);
      System.out.println("succeeded");

    } catch (ClassNotFoundException CNFE) {
      System.out.println("Could not find the class " + CNFE.getMessage());
    } catch (SQLException SQLE){
      System.out.println("Something went wrong with sql " + SQLE.getMessage());
    }
  }


  public void addLocation(String aUID, String identifier, String city, String stationCode) {
    try {
      removeLocation(aUID, identifier);
      PreparedStatement ps = con.prepareStatement(
          "INSERT INTO `mydb`.`Location` (`UID`, `identifier`, `city`, `stationCode`) VALUES ( ? , ? , ? , ?);");
      ps.setString(1, aUID);
      ps.setString(2, identifier);
      ps.setString(3, city);
      ps.setString(4, stationCode);

      ri = ps.executeUpdate();
      ri = 0;

    } catch (SQLException SQLE) {
      System.out.println("There is a problem with SQL: " + SQLE.getMessage());
    }
  }

  public ArrayList<LocationsModel> getLocations(String aUID) {
    try {
      PreparedStatement ps = con
          .prepareStatement("SELECT identifier,city,stationCode FROM Location WHERE UID = ? ");
      ps.setString(1, aUID);

      rs = ps.executeQuery();
      ArrayList<LocationsModel> resultList = new ArrayList<>();
      while (rs.next()) {
        resultList.add(new LocationsModel(rs.getString(1), rs.getString(2), rs.getString(3)));
      }
      return resultList;
    } catch (SQLException SQLE) {
      System.out.println("There is a problem with SQL: " + SQLE.getMessage());
    }
    return new ArrayList<>();
  }

  public void updateLocation(String aUID, String identifier, String city, String stationCode) {
    try {
      PreparedStatement ps = con.prepareStatement(
          "UPDATE `mydb`.`Location` SET `city`=? , `stationCode`=? WHERE `UID`=? and`identifier`=? ");
      ps.setString(1, city);
      ps.setString(2, stationCode);
      ps.setString(3, aUID);
      ps.setString(4, identifier);

      ri = ps.executeUpdate();
      ri = 0;

    } catch (SQLException SQLE) {
      System.out.println("Something went wrong with SQL " + SQLE.getMessage());
    }
  }

  @SuppressWarnings("WeakerAccess")
  public void removeLocation(String aUID, String identifier) {
    try {
      PreparedStatement ps = con
          .prepareStatement("DELETE FROM `mydb`.`Location` WHERE `UID`=? and`identifier`=?");
      ps.setString(1, aUID);
      ps.setString(2, identifier);

      ri = ps.executeUpdate();
      ri = 0;

    } catch (SQLException SQLE) {
      System.out.println("Problem with sql " + SQLE.getMessage());
    }
  }

  public void addUser(String aUID, String name, String email) {
    try {
      PreparedStatement ps = con
          .prepareStatement("INSERT INTO `mydb`.`Users` (`UID`, `name`, `email`) VALUES (?, ?, ?)");
      ps.setString(1, aUID);
      ps.setString(2, name);
      ps.setString(3, email);

      ri = ps.executeUpdate();
      ri = 0;
    } catch (SQLException SQLE) {
      System.out.println("Something went wrong with SQL " + SQLE.getMessage());
    }
  }

  public UsersModel getUser(String aUID) {
    try {
      PreparedStatement ps = con.prepareStatement("SELECT name,email FROM Users WHERE UID = ? ");
      ps.setString(1, aUID);

      rs = ps.executeQuery();
      if (rs.next()) {
        return new UsersModel((rs.getString(1)), (rs.getString(2)));
      }
    } catch (SQLException SQLE) {
      System.out.println("Problem with SQL: " + SQLE.getMessage());
    }
    return null;
  }

  public void updateUser(String aUID, String name, String email) {
    try {
      PreparedStatement ps = con
          .prepareStatement("UPDATE `mydb`.`Users` SET `name`=?,`email`=? WHERE `UID`=?");
      ps.setString(1, name);
      ps.setString(2, email);
      ps.setString(3, aUID);

      ri = ps.executeUpdate();
      ri = 0;
    } catch (SQLException SQLE) {
      System.out.println("Problem with SQL: " + SQLE.getMessage());
    }
  }

  public void addComposition(String aUID, int passengers) {
    try {
      removeComposition(aUID);
      PreparedStatement ps = con.prepareStatement(
          "INSERT INTO `mydb`.`Composition` (`UID`, `numberOfPassengers`) VALUES (? , ?)");
      ps.setString(1, aUID);
      ps.setInt(2, passengers);

      ri = ps.executeUpdate();
      ri = 0;
    } catch (SQLException SQLE) {
      System.out.println("Problem with SQL: " + SQLE.getMessage());
    }
  }

  public CompositionModel getComposition(String aUID) {
    try {
      PreparedStatement ps = con
          .prepareStatement("SELECT numberOfPassengers FROM Composition WHERE UID = ? ");
      ps.setString(1, aUID);

      rs = ps.executeQuery();
      if (rs.next()) {
        return new CompositionModel(rs.getInt(1));
      }
    } catch (SQLException SQLE) {
      System.out.println("Problem with SQL" + SQLE.getMessage());
    }
    return null;
  }

  public void updateComposition(String aUID, int passengers) {
    try {
      PreparedStatement ps = con.prepareStatement(
          "UPDATE `mydb`.`Composition` SET `numberOfPassengers`=? WHERE `UID`=? ");
      ps.setInt(1, passengers);
      ps.setString(2, aUID);

      ri = ps.executeUpdate();
      ri = 0;

    } catch (SQLException SQLE) {
      System.out.println("Problems with SQL: " + SQLE.getMessage());
    }
  }

  @SuppressWarnings("WeakerAccess")
  public void removeComposition(String aUID) {
    try {
      PreparedStatement ps = con.prepareStatement("DELETE FROM `mydb`.`Composition` WHERE `UID`=?");
      ps.setString(1, aUID);

      ri = ps.executeUpdate();
      ri = 0;

    } catch (SQLException SQLE) {
      System.out.println("Problems with SQL: " + SQLE.getMessage());
    }
  }

  public void addJourney(int numericalidentifier, com.nsiapi.models.connections.Connection selectedJourney, int selectedClass) {
        String aUID = NSInternationalSpeechlet.UNIQUE_USER_ID;
        String identifier = Integer.toString(numericalidentifier);
        String originCode = selectedJourney.getOrigin().getCode();
        String destinationCode = selectedJourney.getDestination().getCode();
        String departuretime = selectedJourney.getDBDepartureTime();
        String departuredate = selectedJourney.getDBDepartureDate();
        String orderprice = selectedJourney.getOffers().get(selectedClass).getSalesPrice().getAmount();
    try {
      PreparedStatement ps = con.prepareStatement(
          "INSERT INTO `mydb`.`Journey` (`UID`, `identifier`, `originCode`, `destinationCode`, `departuretime`, `departuredate`, `orderprice`) VALUES (?, ?, ?, ?, ?, ?, ?)");
      ps.setString(1, aUID);
      ps.setString(2, identifier);
      ps.setString(3, originCode);
      ps.setString(4, destinationCode);
      ps.setString(5, departuretime);
      ps.setString(6, departuredate);
      ps.setString(7, orderprice);

      ri = ps.executeUpdate();
      ri = 0;
    } catch (SQLException SQLE) {
      System.out.println("Problems with sql: " + SQLE.getMessage());
    }
  }

  public ArrayList<JourneyModel> getJourneys(String aUID) {
    try {
      PreparedStatement ps = con.prepareStatement("SELECT * FROM mydb.Journey WHERE UID = ? ");
      ps.setString(1, aUID);

      rs = ps.executeQuery();
      ArrayList<JourneyModel> resultList = new ArrayList<>();
      while (rs.next()) {
        resultList.add(
            new JourneyModel(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                rs.getString(6), rs.getString(7)));
      }
      return resultList;
    } catch (SQLException SQLE) {
      System.out.println("There is a problem with SQL: " + SQLE.getMessage());
    }
    return null;
  }

  public int getMaxJourneyIdentifier(String aUID) {
    try {
      PreparedStatement ps = con
          .prepareStatement("SELECT max(identifier) FROM mydb.Journey WHERE UID = ? ");
      ps.setString(1, aUID);

      rs = ps.executeQuery();
      rs.next();
      return rs.getInt(1) + 1;
    } catch (SQLException SQLE) {
      System.out.println("There is a problem with SQL: " + SQLE.getMessage());
    } catch (NumberFormatException NFE) {
      System.out.println("There is a problem with Parsing: " + NFE.getMessage());
    }
    return 1;
  }

  public void removeJourney(String aUID, String identifier) {
    try {
      PreparedStatement ps = con
          .prepareStatement("DELETE FROM `mydb`.`Journey` WHERE `UID`=? and`identifier`=? ");
      ps.setString(1, aUID);
      ps.setString(2, identifier);

      ri = ps.executeUpdate();
      ri = 0;
    } catch (SQLException SQLE) {
      System.out.println("Problems with SQL: " + SQLE.getMessage());
    }
  }


}