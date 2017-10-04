package nl.nsi.demo.echo;

import static com.amazon.speech.speechlet.IntentRequest.DialogState.IN_PROGRESS;
import static com.amazon.speech.speechlet.IntentRequest.DialogState.STARTED;

import com.MailClient;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.Directive;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.dialog.directives.DelegateDirective;
import com.amazon.speech.ui.Image;
import com.amazon.speech.ui.LinkAccountCard;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;
import com.amazonapi.AmazonApi;
import com.amazonapi.models.AmazonUser;
import com.amazonapi.requests.GetUserRequest;
import com.database.Database;
import com.database.models.JourneyModel;
import com.database.models.UsersModel;
import com.nsiapi.NSIApi;
import com.nsiapi.models.booking.Dnr;
import com.nsiapi.models.calendardates.Dates;
import com.nsiapi.models.calendarprices.Period_;
import com.nsiapi.models.calendarprices.Price;
import com.nsiapi.models.calendarprices.Prices;
import com.nsiapi.models.connections.Connection;
import com.nsiapi.models.connections.Connections;
import com.nsiapi.models.stations.Stations;
import com.nsiapi.requests.CalendarDateRequest;
import com.nsiapi.requests.CalendarPriceRequest;
import com.nsiapi.requests.PriceAndTimeRequest;
import com.nsiapi.requests.ProvisionalBookingRequest;
import com.nsiapi.requests.StationNameRequest;
import java.util.ArrayList;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains all the functionality of the skill and will handle all the requests and intents.
 */
@SuppressWarnings("SameParameterValue")
public class NSInternationalSpeechlet implements Speechlet {

  /**
   * Is used for every database modification.
   */
  public static final Database DB = new Database();
  /**
   * Is used for every NS international API request
   */
  private static final NSIApi API = new NSIApi();
  /**
   * Is the selected logger
   */
  private static final Logger log = LoggerFactory.getLogger(NSInternationalSpeechlet.class);
  /**
   * Contains the name of the date slot
   */
  private static final String DATE_SLOT = "date";
  /**
   * Contains the name of the fromCity slot
   */
  private static final String CITY_ORIGIN = "fromCity";
  /**
   * Contains the name of the toCity slot
   */
  private static final String CITY_DESTINATION = "toCity";
  /**
   * Contains the name of the option slot
   */
  private static final String OPTION = "option";
  /**
   * Contains the name of the locationidentifier slot
   */
  private static final String LOCATION = "locationidentifier";
  /**
   * Contains the name of the cityname slot
   */
  private static final String CITY = "cityname";
  /**
   * Contains the name of the numberOfPassengers slot
   */
  private static final String COMPOSITION = "numberOfPassengers";
  /**
   * Contains the name of the juncture slot
   */
  private static final String JUNCTURE = "juncture";
  /**
   * Contains the name of the time slot
   */
  private static final String TIME = "time";


  /**
   * Contains the user ID from the NS International API
   */
  @SuppressWarnings("WeakerAccess")
  public static String UNIQUE_NS_ID = "";
  /**
   * Contains the User ID from the echo
   */
  public static String UNIQUE_USER_ID = "";
  /**
   * Contains a list of ordinal numbers used for presenting the journey options to the user
   */
  private final String[] ORDINAL_NUMBER_LIST = new String[]{"first", "second", "third", "fourth",
      "fifth"};
  /**
   * Indicates if the user is new, default is false
   */
  private boolean isNewUser = false;
  /**
   * Request containing the cheapest journey
   */
  private PriceAndTimeRequest cheapestRequest;
  /**
   * After a traveler intent has been called, 3 journeys will be stored in this list (if there are 3
   * options available).
   */
  private ArrayList<Connection> connectionOptions;
  /**
   * After the traveler intent has been called and executed, this boolean will be set to true. This
   * means the choose intent can be handled.
   */
  private boolean journeyHasBeenSelected = false;

  /**
   * The date used for requests in the format: YYYYMMDD
   */
  private String date = "";
  /**
   * The place of departure (name) like: Amsterdam,Paris,Berlin or London.
   */
  private String origin = "";
  /**
   * The arrival location (name) like: Amsterdam,Paris,Berlin or London.
   */
  private String destination = "";
  /**
   * The time used for the requests in the format: HHmm
   */
  private String time = "";

  /**
   * This int is used to select a class
   * 0 = first class
   * 1 = second class
   */
  private int selectedClass = 0;

  /**
   * This function is the first to be called when the skill is activated, in here the date and time
   * are set for use in the requests. The Amazon user ID is collected and stored. And the database
   * is called to see if this is a new user, if the user is new isNewUser is set to true.
   *
   * @param request Amazon sends this to the lambda function. Contains request ID.
   * @param session Contains the user ID, session ID and authentication token.
   * @throws SpeechletException A SpeechletException
   */
  @Override
  public void onSessionStarted(final SessionStartedRequest request, final Session session)
      throws SpeechletException {
    log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
    // any initialization logic goes here
    initializeDateandTime();
    UNIQUE_USER_ID = session.getUser().getUserId();
    UsersModel user = DB.getUser(UNIQUE_USER_ID);
    if (user == null || user.getName() == null || user.getName().equals("")) {
      isNewUser = true;
    }
  }

  /**
   * This function is called when the skill is activated without an intent, "Alexa, Start NSI". It
   * checks if the user has an access token, if not it returns a linkaccountcard, if yes it adds the
   * user to the database. This function calls the getUpdatedResponse.
   *
   * @param request Amazon's LaunchRequest, contains requestID.
   * @param session Amazon's Session, contains Session ID, accesstoken and User ID.
   * @return A speechletResponse from getUpdatedResponse
   * @throws SpeechletException A SpeechletException.
   */
  @Override
  public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
      throws SpeechletException {
    log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
    if (session.getUser().getAccessToken() == null) {
      return linkaccountCard();
    } else {
      if (isNewUser) {
        addUser(session.getUser().getAccessToken());
      }
    }
    return getUpdatedResponse();
  }

  /**
   * This function is called when the skill is activated with an intent. A function corresponding to
   * the intent is called. It also checks if the user is new and if the user has an access token.
   *
   * @param request IntentRequest contains requestID and the Intent.
   * @param session Session contains session ID.
   * @return Returns a speechletResponse from the correct function.
   * @throws SpeechletException A SpeechletException.
   */
  @Override
  public SpeechletResponse onIntent(final IntentRequest request, final Session session)
      throws SpeechletException {
    log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
    Intent intent = request.getIntent();
    String intentName = (intent != null) ? intent.getName() : null;

    if (session.getUser().getAccessToken() == null) {
      return linkaccountCard();
    } else if (isNewUser) {
      addUser(session.getUser().getAccessToken());
      return newUser();
    }
    if (request.getDialogState() == STARTED || request.getDialogState() == IN_PROGRESS) {
      return delegateDirective();
    }
    switch(intentName){
      case "Traveler":
        return getTravelerResponse(intent);
      case "Cheapest":
        return getCheapestOption(intent);
      case "LocationIntent":
        return getLocationIntentResponse(intent);
      case "CompositionIntent":
        return getCompositionIntentResponse(intent);
      case "AMAZON.CancelIntent":
      case "AMAZON.StopIntent":
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Goodbye");
        return SpeechletResponse.newTellResponse(outputSpeech);
      case "ChooseIntent":
        if(journeyHasBeenSelected){
          return getChooseIntentResponse(intent);
        }
       default:
         throw new SpeechletException("Invalid Intent");
    }
  }

  /**
   * This function is called after the skill has done its thing. Created by Amazon. Cleanup logic
   * goes here.
   *
   * @param request SessionEndedRequest, amazon stuff.
   * @param session Session, amazon stuff.
   * @throws SpeechletException A SpeechletException.
   */
  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session)
      throws SpeechletException {
    log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
  }

  /**
   * This function is called when the intent equals "Traveler". It asks the NS International API for
   * all the connections corresponding to the date,time,origin,destination and arrival or departure.
   * And selects a maximum of 3 connections to read back to the user. If connections could be
   * retrieved the boolean journeyHasBeenSelected is set to true.
   *
   * @param intent a Traveler intent
   * @return Returns a speechletResponse
   */
  private SpeechletResponse getTravelerResponse(Intent intent) {

    //Create placeholder speech output
    StringBuilder speechText = new StringBuilder();
    Slot cityDestination = intent.getSlot(CITY_DESTINATION);
    Slot cityOrigin = intent.getSlot(CITY_ORIGIN);
    Slot slotDate = intent.getSlot(DATE_SLOT);
    Slot juncture = intent.getSlot(JUNCTURE);
    Slot timeSlot = intent.getSlot(TIME);

    boolean arrival = false;
    if (cityOrigin != null && cityOrigin.getValue() != null) {
      origin = cityOrigin.getValue();
      speechText = new StringBuilder("You have chosen " + origin + " as your place of departure. ");
    }
    if (cityDestination != null && cityDestination.getValue() != null) {
      destination = cityDestination.getValue();
      speechText.append("and you want to go to ").append(destination).append(". ");
    }
    if (slotDate != null && slotDate.getValue() != null) {
      date = slotDate.getValue();
      date = date.replace("-", "");
      speechText.append("On ").append(slotDate.getValue()).append(". ");
    }
    if (juncture != null && juncture.getValue() != null) {
      if (juncture.getValue().equals("arrive")) {
        arrival = true;
      }
    }
    if (timeSlot != null && timeSlot.getValue() != null) {
      time = parseTime(timeSlot.getValue());
    }

    PriceAndTimeRequest request = new PriceAndTimeRequest(origin, destination, date, time, arrival);
    if (request.getDestinationCode().equals(request.getOriginCode())) {
      PlainTextOutputSpeech altSpeech = new PlainTextOutputSpeech();
      altSpeech.setText("Your selected origin or destination is wrong, please try again.");
      return SpeechletResponse.newTellResponse(altSpeech);
    }
    Connections model = API.getResponse(request);

    if (model.getStatus().equals("success")) {
      UNIQUE_NS_ID = model.getData().getUid();
      journeyHasBeenSelected = true;
      connectionOptions = new ArrayList<>();
      int i = 0;
      while (i < model.getData().getConnections().size()) {
        if (model.getData().getConnections().get(i).getStatus().equals("bookable")) {
          connectionOptions.add(model.getData().getConnections().get(i));
          if (connectionOptions.size() == 3) {
            break;
          }
        }
        i++;
      }
      i = 0;
      if (connectionOptions.isEmpty()) {
        PlainTextOutputSpeech altSpeech = new PlainTextOutputSpeech();
        altSpeech.setText("There are no trains available, try a different date");
        return SpeechletResponse.newTellResponse(altSpeech);
      } else {
        speechText.append("There are ").append(connectionOptions.size())
            .append(" options available. \n ");
      }
      if (arrival) {
        for (Connection element : connectionOptions) {
          speechText.append("The ").append(ORDINAL_NUMBER_LIST[i]).append(" option is arrival at ")
              .append(element.getArrivalTime()).append(".\n ");
          i++;
        }
      } else {
        for (Connection element : connectionOptions) {
          speechText.append("The ").append(ORDINAL_NUMBER_LIST[i])
              .append(" option is departure at ").append(element
              .getDepartureTime()).append(".\n ");
          i++;
        }
      }
      speechText.append("Which option do you choose?");
    } else {
      speechText = new StringBuilder(
          "Something went wrong with collection data of your journey, please try again later");
    }
    StandardCard card = new StandardCard();
    card.setTitle("Travel options");
    card.setText(speechText.toString());

    String repromptText = "If you want to cancel the order, just say exit.";

    return newAskResponse(speechText.toString(), repromptText, card);
  }

  /**
   * This function is called when the intent equals "ChooseIntent" and the boolean
   * journeyHasBeenSelected equals true. It creates a provisional booking for the selected journey
   * and mails it to the user. It also gives a quick recap of the selected journey, and sends a card
   * to the interface with a picture.
   *
   * @param intent a ChooseIntent intent
   * @return a speechletResponse
   */
  private SpeechletResponse getChooseIntentResponse(Intent intent) {
    String speechText = "";
    Slot option = intent.getSlot(OPTION);
    int selectedJourneyInteger = 0;
    int journeyidentifier = DB.getMaxJourneyIdentifier(UNIQUE_USER_ID);
    Connection selectedJourney;

    if (option != null && option.getValue() != null) {
      try {
        selectedJourneyInteger = Integer.parseInt(option.getValue()) - 1;
      } catch (NumberFormatException NFE) {

        return newAskResponse("I didn't quite get that, which option did you choose?",
            "To cancel the order, just say exit");

      }
      speechText = "You have chosen option " + (selectedJourneyInteger + 1) + " . ";
    }
    if (selectedJourneyInteger >= connectionOptions.size() || selectedJourneyInteger < 0) {
      selectedJourney = connectionOptions.get(connectionOptions.size() - 1);
    } else {
      selectedJourney = connectionOptions.get(selectedJourneyInteger);
    }
    connectionOptions = null;
    speechText += selectedJourney.getJourneySummary();

    DB.addJourney(journeyidentifier,selectedJourney,selectedClass);

    ProvisionalBookingRequest request = new ProvisionalBookingRequest(selectedJourney,selectedClass);
    Dnr model = NSInternationalSpeechlet.API.getResponse(request);

    String gotoUrl = ("https://www.nsinternational.nl/en/traintickets#/passengers/" + model
        .getData().getDnrId() + "?signature=" + model.getData().getSignature());
    String email = DB.getUser(UNIQUE_USER_ID).getEmail();
    new MailClient(gotoUrl, selectedJourney, email).sendMail();

    speechText += "Go to your email to finish the booking. ";

    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText(speechText);

    StandardCard card = new StandardCard();
    card.setImage(createDestinationImage());
    card.setText("You are going to: " + selectedJourney.getDestination().getName());
    card.setTitle("NSI wishes you a pleasant journey");
    journeyHasBeenSelected = false;
    return SpeechletResponse.newTellResponse(speech, card);
  }

  /**
   * This function is called when the skill is activated without an intent. It gets the first
   * journey from the database, and checks if it is delayed.
   *
   * @return a speechletResponse
   */
  private SpeechletResponse getUpdatedResponse() {

    StandardCard card = new StandardCard();
    card.setTitle("Get your update while it's fresh");
    String name = DB.getUser(UNIQUE_USER_ID).getName();
    String altSpeech = "Hi, " + name + ". ";
    ArrayList<JourneyModel> journeys = DB.getJourneys(UNIQUE_USER_ID);
    if (journeys.size() != 0) {
      PriceAndTimeRequest request = new PriceAndTimeRequest(journeys.get(0).getOriginCode(),
          journeys.get(0).getDestinationCode(), journeys.get(0).getDeparturedate(),
          journeys.get(0).getDeparturetime());
      Connections trip = API.getResponse(request);
      if (trip.getData().getConnections().get(0).getDuration().getDelay()) {
        altSpeech +=
            "Your trip to " + trip.getData().getConnections().get(0).getDestination().getName()
                + " has been delayed";
      } else {
        altSpeech +=
            "Your trip to " + trip.getData().getConnections().get(0).getDestination().getName()
                + " is going according to plan";
      }
    } else {
      altSpeech += "There are no saved journeys, you can start by saying: buy a ticket";
    }
    String altReprompt = "You can exit traveler by saying, exit.";
    card.setText(altSpeech);
    return newAskResponse(altSpeech, altReprompt, card);
  }

  /**
   * This function is called when the intent equals "LocationIntent". It stores the station of the
   * selected city under the identifier of the location in the database. Identifier must be home or
   * work.
   *
   * @param intent a LocationIntent
   * @return Returns a speechletResponse
   */
  private SpeechletResponse getLocationIntentResponse(Intent intent) {
    String identifier = intent.getSlot(LOCATION).getValue();
    String city = intent.getSlot(CITY).getValue();
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    PlainTextOutputSpeech repromptText = new PlainTextOutputSpeech();
    Reprompt reprompt = new Reprompt();

    try {
      StationNameRequest request = new StationNameRequest(city);
      Stations stations = API.getResponse(request);
      DB.addLocation(UNIQUE_USER_ID, identifier, city, stations.getStation());
      speech.setText("Location added succesfully, where do you want to travel?");
    } catch (Exception E) {
      speech.setText(
          "Location adding failed, you can try again if you want, or tell me where you want to travel");
    }
    repromptText.setText(
        "If you want to quit you can say exit, or you can say I want to travel to Amsterdam");
    reprompt.setOutputSpeech(repromptText);
    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  /**
   * This function is called when the intent equals "Composition Intent". It stores the number of
   * people the user wants to book tickets for in the database.
   *
   * @param intent a CompositionIntent
   * @return Returns a speechletResponse
   */
  private SpeechletResponse getCompositionIntentResponse(Intent intent) {
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    PlainTextOutputSpeech repromptText = new PlainTextOutputSpeech();
    Reprompt reprompt = new Reprompt();
    int nOP = Integer.parseInt(intent.getSlot(COMPOSITION).getValue());

    try {
      DB.addComposition(UNIQUE_USER_ID, nOP);
      speech.setText("Composition added succesfully");
    } catch (Exception E) {
      speech.setText("Composition adding failed");
    }
    repromptText.setText("Where do you want to go?");
    reprompt.setOutputSpeech(repromptText);
    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  /**
   * This function is called when the intent equals "Cheapest". If a cheapest journey already has
   * been selected, it will execute bookCheapestOption. If no cheapest journey has been selected, it
   * will execute getCheapestOptionFromServer
   *
   * @param intent a Cheapest intent
   * @return Returns a speechletResponse
   */
  private SpeechletResponse getCheapestOption(Intent intent) {
    if (cheapestRequest == null) {
      return getCheapestOptionFromServer(intent);
    } else {
      return bookCheapestOption();
    }
  }

  /**
   * This function is called when no cheapest journey has been selected. It returns the cheapest
   * date and price for the journey from the origin to the destination. If the origin is empty
   * Amsterdam Centraal is selected. After the cheapest date and price is returned, the user can
   * book the journey.
   *
   * @param intent a Cheapest intent
   * @return Returns a speechletResponse
   */
  @SuppressWarnings("UnusedAssignment")
  private SpeechletResponse getCheapestOptionFromServer(Intent intent) {
    Slot cityDestination = intent.getSlot(CITY_DESTINATION);
    Slot cityOrigin = intent.getSlot(CITY_ORIGIN);
    String speechText = "";
    String cheapOriginCode = "";
    String cheapDestinationCode = "";
    String cheapestTime = "";

    if (cityOrigin != null && cityOrigin.getValue() != null) {
      cheapOriginCode = API.getResponse(new StationNameRequest(cityOrigin.getValue())).getStation();
    } else {
      cheapOriginCode = "NLASC";
    }
    if (cityDestination != null && cityDestination.getValue() != null) {
      cheapDestinationCode = API.getResponse(new StationNameRequest(cityDestination.getValue()))
          .getStation();
    }
    String cheapestDate = "";
    Double cheapestPrice = 2000.00;
    if (!cheapOriginCode.equals("") && !cheapDestinationCode.equals("")) {
      CalendarDateRequest request = new CalendarDateRequest(cheapDestinationCode, cheapOriginCode);
      Dates data = API.getResponse(request);
      if (data.getData().getPricesAvailable()) {
        CalendarPriceRequest request1 = new CalendarPriceRequest(cheapDestinationCode,
            cheapOriginCode);
        Prices prices = API.getResponse(request1);
        for (Price element : prices.getData().getPrices()) {
          if (cheapestPrice > Double.parseDouble(element.getAmount())) {
            cheapestPrice = Double.parseDouble(element.getAmount());
            cheapestDate = element.getDate();
            for (Period_ period : element.getDeparture().getPeriods()) {
              if (element.getAmount().equals(period.getAmount())) {
                cheapestTime = period.getTime();
              }
            }
          }

        }
        speechText = "The cheapest date for your journey is " + cheapestDate + ". For a price of "
            + cheapestPrice + ". ";
        cheapestRequest = new PriceAndTimeRequest(cheapOriginCode, cheapDestinationCode,
            parseDate(cheapestDate), parseTime(cheapestTime));
      } else {
        speechText = "There are no prices available for this journey, i am sorry";
      }
    }
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(speechText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(outputSpeech);
    return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
  }

  /**
   * This function is called when the cheapest journey has been selected. It creates a provisional
   * booking and sends an email to the user.
   *
   * @return a speechletResponse
   */
  private SpeechletResponse bookCheapestOption() {

    Connections data = API.getResponse(cheapestRequest);
    UNIQUE_NS_ID = data.getData().getUid();
    Connection selectedJourney = data.getData().getConnections().get(0);
    ProvisionalBookingRequest request = new ProvisionalBookingRequest(selectedJourney,selectedClass);
    Dnr model = NSInternationalSpeechlet.API.getResponse(request);

    String gotoUrl = ("https://www.nsinternational.nl/en/traintickets#/passengers/" + model
        .getData().getDnrId() + "?signature=" + model.getData().getSignature());
    String email = DB.getUser(UNIQUE_USER_ID).getEmail();
    new MailClient(gotoUrl, selectedJourney, email).sendMail();

    String speechText = "Please go to your email to finish the booking";
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(speechText);
    cheapestRequest = null;
    return SpeechletResponse.newTellResponse(outputSpeech);
  }

  /**
   * This functions adds a user to the database, it asks the amazon api for name and email using the
   * accestoken. The user is stored in the database with the Amazon User ID, Amazon name and Amazon
   * email.
   *
   * @param accestoken String containing the accesstoken from Session
   */
  private void addUser(String accestoken) {
    AmazonApi aapi = new AmazonApi();
    GetUserRequest request = new GetUserRequest(accestoken);
    AmazonUser model = aapi.getResponse(request);
    DB.addUser(UNIQUE_USER_ID, model.getName(), model.getEmail());
  }

  /**
   * This function is called when the user is new, it asks the user for how many people the user
   * wants to book a ticket.
   *
   * @return Returns a speechletResponse
   */
  private SpeechletResponse newUser() {
    PlainTextOutputSpeech speechOutput = new PlainTextOutputSpeech();
    speechOutput.setText(
        "Thank you for enabling this skill, since this is your first time, with how many people do you travel?");
    Reprompt repromptOutput = new Reprompt();
    PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
    repromptSpeech.setText("If you want to exit, just say exit");
    repromptOutput.setOutputSpeech(repromptSpeech);
    isNewUser = false;
    return SpeechletResponse.newAskResponse(speechOutput, repromptOutput);
  }

  /**
   * This function is called when no accesstoken is available, it tells the user to link his amazon
   * account with the skill.
   *
   * @return a speechletResponse
   */
  private SpeechletResponse linkaccountCard() {
    LinkAccountCard card = new LinkAccountCard();
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText("Go to your alexa app to link this skill");
    return SpeechletResponse.newTellResponse(speech, card);
  }

  /**
   * This function is called when a journey has been selected.
   *
   * @return returns an image corresponding to the destination.
   */
  private Image createDestinationImage() {
    Image image = new Image();
    switch (destination) {
      case "Amsterdam":
        image.setSmallImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Flag_of_Amsterdam.svg/1200px-Flag_of_Amsterdam.svg.png");
        image.setLargeImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6d/Flag_of_Amsterdam.svg/1200px-Flag_of_Amsterdam.svg.png");
        break;
      case "Berlin":
        image.setSmallImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Brandenburger_Tor_nachts.jpg/1920px-Brandenburger_Tor_nachts.jpg");
        image.setLargeImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Brandenburger_Tor_nachts.jpg/1920px-Brandenburger_Tor_nachts.jpg");
        break;
      case "Paris":
        image.setSmallImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg/800px-Paris_-_Eiffelturm_und_Marsfeld2.jpg");
        image.setLargeImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6e/Paris_-_Eiffelturm_und_Marsfeld2.jpg/800px-Paris_-_Eiffelturm_und_Marsfeld2.jpg");
        break;
      case "London":
        image.setSmallImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Londencityhallentheshard.JPG/800px-Londencityhallentheshard.JPG");
        image.setLargeImageUrl(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/b/be/Londencityhallentheshard.JPG/800px-Londencityhallentheshard.JPG");
        break;
      default:
        image.setSmallImageUrl(
            "https://www.malibuvista.com/wp-content/uploads/2015/04/iStock_000001947025_Full.jpg");
        image.setLargeImageUrl(
            "https://www.malibuvista.com/wp-content/uploads/2015/04/iStock_000001947025_Full.jpg");
        break;

    }
    return image;
  }

  /**
   * This function is called to initialize the date and time, it selects the current date and time.
   * And stores it in the variables date and time
   */
  private void initializeDateandTime() {
    Calendar cal = Calendar.getInstance();
    date =
        "" + cal.get(Calendar.YEAR) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String
            .format("%02d", cal.get(Calendar.DAY_OF_MONTH));
    time = "" + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY) + 2) + String
        .format("%02d", cal.get(Calendar.MINUTE));
  }

  /**
   * Parse the time from HH:mm to HHmm
   *
   * @param newTime String in the format HH:mm
   * @return Returns a String in the format HHmm
   */
  private String parseTime(String newTime) {
    if (newTime.equals("EV") || newTime.equals("MO") || newTime.equals("NI") || newTime
        .equals("AF")) {
      switch (newTime) {
        case "NI":
        case "EV":
          newTime = "1800";
          break;
        case "MO":
          newTime = "0700";
          break;
        case "AF":
          newTime = "1500";
          break;
      }
    } else {
      newTime = newTime.replace(":", "");
    }
    return newTime;
  }

  /**
   * Parses the data from YYYY-MM-DD to YYYYMMDD
   *
   * @param newDate date String in the format YYYY-MM-DD
   * @return date String in the format YYYYMMDD
   */
  private String parseDate(String newDate) {
    return newDate.replace("-", "");
  }

  /**
   * This function is called for a dialog with the user. If information is missing from the intent,
   * then this function will ask for more information from the user.
   *
   * @return Returns a speechletResponse
   */
  private SpeechletResponse delegateDirective() {
    DelegateDirective dd = new DelegateDirective();

    ArrayList<Directive> directiveList = new ArrayList<>();
    directiveList.add(dd);

    SpeechletResponse speechletResp = new SpeechletResponse();
    speechletResp.setDirectives(directiveList);
    speechletResp.setNullableShouldEndSession(false);
    return speechletResp;
  }

  /**
   * Wrapper for creating the Ask response. The OutputSpeech and {@link Reprompt} objects are
   * created from the input strings.
   *
   * @param stringOutput the output to be spoken
   * @param repromptText the reprompt for if the user doesn't reply or is misunderstood.
   * @param card the card to be shown to the user on the alexa web interface
   * @return SpeechletResponse the speechlet response
   */
  private SpeechletResponse newAskResponse(String stringOutput, String repromptText,
      StandardCard card) {
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(stringOutput);

    PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
    repromptOutputSpeech.setText(repromptText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(repromptOutputSpeech);

    return SpeechletResponse.newAskResponse(outputSpeech, reprompt, card);
  }

  /**
   * Wrapper for creating the Ask response. The OutputSpeech and {@link Reprompt} objects are
   * created from the input strings.
   *
   * @param stringOutput the output to be spoken
   * @param repromptText the reprompt for if the user doesn't reply or is misunderstood.
   * @return SpeechletResponse the speechlet response
   */
  private SpeechletResponse newAskResponse(String stringOutput, String repromptText) {
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(stringOutput);

    PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
    repromptOutputSpeech.setText(repromptText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(repromptOutputSpeech);

    return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
  }
}
