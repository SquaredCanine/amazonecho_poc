package com;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.nsiapi.models.connections.Connection;
import jdk.nashorn.internal.ir.annotations.Ignore;

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
   Properties props = new Properties();
   props.put("mail.smtp.host", "smtp.gmail.com");
   props.put("mail.smtp.socketFactory.port", "465");
   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
   props.put("mail.smtp.auth","true");
   props.put("mail.smtp.port","465");
   Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
     @Override
     protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(sender,password);
     }
   });
    try {
      Message email = new MimeMessage(session);
      email.setFrom(new InternetAddress("internationalereizen@gmail.com"));
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
      email.setRecipient(Message.RecipientType.TO,new InternetAddress(userEmail));
      Transport.send(email);

    } catch (MessagingException e) {
      System.out.println("Problem with emailclient: " + e.getMessage());
    }
  }
}
