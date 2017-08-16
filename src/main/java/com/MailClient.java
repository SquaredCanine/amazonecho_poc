package com;

import com.nsiapi.models.connections.Connection;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * This class is used to send an email to the users
 */
public class MailClient {

  private final String gotoUrl;
  private final String sender = System.getenv("username_email");
  private final String password = System.getenv("password_email");
  private final Connection journey;
  private final String userEmail;

  /**
   * Constructor
   * @param gotoUrl String containing the provisional booking url
   * @param journey Connection containing the journey for which the provisional booking was made
   * @param userEmail String containing the email of the user
   */
  public MailClient(String gotoUrl, Connection journey, String userEmail) {
    this.gotoUrl = gotoUrl;
    this.journey = journey;
    this.userEmail = userEmail;
  }

  /**
   * Sends an email with a fixed body containing provisional booking url, small recap of the journey.
   * The email is send to the email of the user.
   */
  public void sendMail() {
    Email email = new SimpleEmail();
    email.setHostName("smtp.googlemail.com");
    email.setSmtpPort(465);
    email.setAuthenticator(new DefaultAuthenticator(sender, password));
    email.setSSLOnConnect(true);
    try {
      email.setFrom("internationalereizen@gmail.com");
      email.setSubject("Voltooi jouw boeking!");
      email.setContent("<a href =\"https://www.nsinternational.nl/\">" +
          "<img src=\"http://www.natm.nl/web/wp-content/uploads/2015/04/logo_nsinternational.jpg\" alt=\"NsInternational\" title=\"NsInternational\" style=\"display:block\" height=\"100px\" width=\"272px\">"
          +
          "</a><br>" +
          "Beste Reiziger,<br>" +
          "<br>" +
          "Voltooi jouw boeking door op <a href =\"" + gotoUrl
          + "\">deze</a> link te klikken. Fijne reis!<br>" +
          "<br>" +
          "<h3>Samenvatting van de reis</h3>" +
          "<div style=\"border-left:6px solid;border-color:#2196F3;color:#000;background-color:#ddffff;padding-left:16px\">Vetrek om: "
          + journey.getDepartureTime() + "  <br>" +
          "Vertrek van: " + journey.getOrigin().getName() + "</div>" +
          "<br>" +
          "<div style=\"border-left:6px solid;border-color:#0000FF;color:#000;background-color:#eedddd;padding-left:16px\">Aankomst om: "
          + journey.getArrivalTime() + "<br>" +
          "Aankomst op: " + journey.getDestination().getName() + "</div>" +
          "<br>" +
          "<b>Totale prijs: " + journey.getOffers().get(0).getSalesPrice().getAmount() + " </b>" +
          "<br><br>" +
          "Met vriendelijke groet,<br><br> Quinten \"Eindbaas\" Stekelenburg<br>", "text/html");
      email.addTo(userEmail);
      email.send();

    } catch (EmailException EE) {
      System.out.println("Problem with emailclient: " + EE.getMessage());
    }
  }
}
