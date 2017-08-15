package chooseCity;

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


public class chooseCitySpeechlet implements Speechlet {

  /**
   * Database word gebruikt voor alle com.database handelingen, de unique user id is het ID van de
   * gebruiker van de echo. Deze word ingesteld op het moment dat de skill gestart word. De NSIApi
   * word gebruikt voor alle api requests aan de NSI Api
   */
  public static final Database DB = new Database();
  public static final NSIApi API = new NSIApi();
  private static final Logger log = LoggerFactory.getLogger(chooseCitySpeechlet.class);
  /**
   * Deze Strings zijn de namen van de slots die in de intents zitten.
   */
  private static final String DATE_SLOT = "date";
  private static final String CITY_ORIGIN = "fromCity";
  private static final String CITY_DESTINATION = "toCity";
  private static final String OPTION = "option";
  private static final String LOCATION = "locationidentifier";
  private static final String CITY = "cityname";
  private static final String COMPOSITION = "numberOfPassengers";
  private static final String JUNCTURE = "juncture";
  private static final String TIME = "time";


  public static String UNIQUE_USER_ID = "";
  public static String UNIQUE_NS_ID = "";
  private String[] ORDINAL_NUMBER_LIST = new String[]{"first", "second", "third", "fourth",
      "fifth"};
  private boolean isNewUser = false;
  private PriceAndTimeRequest cheapestRequest;

  /**
   * Als een reis word opgevraagd worden de eerste 3 opties opgeslagen in een Connections object.
   * Zodat er later door de gebruiker een keuze uit kan worden gemaakt.
   */
  private ArrayList<Connection> connectionOptions;

  /**
   * Op het moment dat de gebruiker 3 ritjes aan hem gepresenteerd krijgt word deze boolean true,
   * zodat de chooseintent kan worden aangeroepen.
   */
  private boolean journeyHasBeenSelected = false;

  /**
   * Deze Strings bevatten
   *
   * @Date De datum die gebruikt word voor de requests in het formaat: YYYYMMDD
   * @Origin De vertreklocatie (naam) zoals: Amsterdam,Paris,Berlin of Londen.
   * @Destination De aankomstlocatie (naam) zoals: Amsterdam,Paris,Berlin of Londen.
   * @Time De tijd die gebruikt word voor de requests in het formaat: HHmm
   */
  private String date = "";
  private String origin = "";
  private String destination = "";
  private String time = "";

  /**
   * Deze functie word als eerste aangeroepen als de skill word geactiveerd, meeste hiervan is
   * geschreven door Amazon. Maar ik stel hier ook de huidige tijd en datum in zodat ik een request
   * kan uitvoeren als dat nodig is. Gebruiker word toegevoegd aan de com.database. Naam is een
   * placeholder, kan worden aangepast als account linking word toegepast.
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
    if (user == null || user.getName().equals(null) || user.getName().equals("")) {
      isNewUser = true;
    }
  }

  /**
   * Deze functie word aangeroepen als de skill word gestart zonder intent. De functie roept hierna
   * getUpdatedResponse aan, deze zal de gebruiker op de hoogte brengen van eventuele veranderingen
   * in zijn geboekte reizen.
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
   * Als de skill word aangeroepen met een intent, gebeurt dat hier. Er word gecheckt welke Intent
   * het is, en daarna word de bijbehorende functie aangeroepen.
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

    if ("Traveler".equals(intentName)) {
      if (request.getDialogState() == STARTED || request.getDialogState() == IN_PROGRESS) {
        return delegateDirective();
      } else {
        return getTravelerResponse(intent);
      }
    } else if ("Cheapest".equals(intentName)) {
      return getCheapestOption(intent);
    } else if ("LocationIntent".equals(intentName)) {
      if (request.getDialogState() == STARTED || request.getDialogState() == IN_PROGRESS) {
        return delegateDirective();
      } else {
        return getLocationIntentResponse(intent);
      }
    } else if ("CompositionIntent".equals(intentName)) {
      if (request.getDialogState() == STARTED || request.getDialogState() == IN_PROGRESS) {
        return delegateDirective();
      } else {
        return getCompositionIntentResponse(intent);
      }
    } else if ("ChooseIntent".equals(intentName) && journeyHasBeenSelected) {
      return getChooseIntentResponse(intent);

    } else if ("AMAZON.StopIntent".equals(intentName)) {
      PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
      outputSpeech.setText("Goodbye");

      return SpeechletResponse.newTellResponse(outputSpeech);
    } else if ("AMAZON.CancelIntent".equals(intentName)) {
      PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
      outputSpeech.setText("Goodbye");

      return SpeechletResponse.newTellResponse(outputSpeech);
    } else {
      throw new SpeechletException("Invalid Intent");
    }
  }

  /**
   * Functie die als laatst word aangeroepen, hier doe ik momenteel niks mee.
   */
  @Override
  public void onSessionEnded(final SessionEndedRequest request, final Session session)
      throws SpeechletException {
    log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
        session.getSessionId());
    // any cleanup logic goes here
  }

  /**
   * Deze functie word aangeroepen als de Traveler intent binnen komt. Alle gegevens worden
   * ingevoerd, en een request word gestuurd Hierna worden 3 mogelijke reis opties teruggegeven aan
   * de gebruiker, als de reiziger heeft aangegeven dat het om vertrek gaat. Dan worden de
   * vertrektijden gegeven, maar default is aankomsttijden.
   */
  private SpeechletResponse getTravelerResponse(Intent intent) {

    //Create placeholder speech output
    String speechText = "";
    Slot cityDestination = intent.getSlot(CITY_DESTINATION);
    Slot cityOrigin = intent.getSlot(CITY_ORIGIN);
    Slot slotDate = intent.getSlot(DATE_SLOT);
    Slot juncture = intent.getSlot(JUNCTURE);
    Slot timeSlot = intent.getSlot(TIME);

    boolean arrival = false;
    if (cityOrigin != null && cityOrigin.getValue() != null) {
      origin = cityOrigin.getValue();
      speechText = "You have chosen " + origin + " as your place of departure. ";
    }
    if (cityDestination != null && cityDestination.getValue() != null) {
      destination = cityDestination.getValue();
      speechText += "and you want to go to " + destination + ". ";
    }
    if (slotDate != null && slotDate.getValue() != null) {
      date = slotDate.getValue();
      date = date.replace("-", "");
      speechText += "On " + slotDate.getValue() + ". ";
    }
    if (juncture != null && juncture.getValue() != null) {
      if (juncture.getValue().equals("arrival")) {
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
        speechText += "There are " + connectionOptions.size() + " options available. ";
      }
      if (arrival) {
        for (Connection element : connectionOptions) {
          speechText +=
              "The " + ORDINAL_NUMBER_LIST[i] + " option is arrival at " + element.getArrivalTime()
                  + ". ";
          i++;
        }
      } else {
        for (Connection element : connectionOptions) {
          speechText += "The " + ORDINAL_NUMBER_LIST[i] + " option is departure at " + element
              .getDepartureTime() + ". ";
          i++;
        }
      }
      speechText += "Which option do you choose?";
    } else {
      speechText = "Something went wrong with collection data of your journey, please try again later";
    }
    StandardCard card = new StandardCard();
    card.setTitle("chooseCity");
    card.setText(speechText);

    String repromptText = "If you want to cancel the order, just say exit.";

    return newAskResponse(speechText, repromptText, card);
  }

  /**
   * Deze functie word aangeroepen als de Chooseintent intent binnenkomt, en de boolean
   * JourneySelected true is. Hier word extra informatie over de reis teruggegeven aan de gebruiker,
   * en de bijbehorende data opgeslagen in de Database.
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
    DB.addJourney(UNIQUE_USER_ID, Integer.toString(journeyidentifier),
        selectedJourney.getOrigin().getCode(),
        selectedJourney.getDestination().getCode(),
        selectedJourney.getDBDepartureTime(),
        selectedJourney.getDBDepartureDate(),
        selectedJourney.getOffers().get(0).getSalesPrice().getAmount());
    ProvisionalBookingRequest request = new ProvisionalBookingRequest(
        chooseCitySpeechlet.UNIQUE_NS_ID,
        selectedJourney.getId(),
        selectedJourney.getOffers().get(0).getId(),
        "true",
        selectedJourney.getOrigin().getCode(),
        selectedJourney.getDestination().getCode());
    Dnr model = chooseCitySpeechlet.API.getResponse(request);

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

    return SpeechletResponse.newTellResponse(speech, card);
  }

  /**
   * WIP, deze functie word aangeroepen als de skill word geactiveerd zonder intent.
   */
  private SpeechletResponse getUpdatedResponse() {

    StandardCard card = new StandardCard();
    card.setTitle("Get your update while it's fresh");
    String name = DB.getUser(UNIQUE_USER_ID).getName();
    String altSpeech = "Hi, " + name + ". ";
    ArrayList<JourneyModel> reizen = DB.getJourneys(UNIQUE_USER_ID);
    if (reizen.size() != 0) {
      PriceAndTimeRequest request = new PriceAndTimeRequest(reizen.get(0).getOriginCode(),
          reizen.get(0).getDestinationCode(), reizen.get(0).getDeparturedate(),
          reizen.get(0).getDeparturetime());
      Connections reis = API.getResponse(request);
      if (reis.getData().getConnections().get(0).getDuration().getDelay()) {
        altSpeech +=
            "Your trip to " + reis.getData().getConnections().get(0).getDestination().getName()
                + " has been delayed";
      } else {
        altSpeech +=
            "Your trip to " + reis.getData().getConnections().get(0).getDestination().getName()
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
   * Deze functie word aangeroepen met de LocationIntent intent. En slaat een locatie op onder de
   * identifier die de gebruiker aangaf Nu gelimiteerd tot home en work. Station word opgezocht aan
   * de hand van de opgegeven stad.
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
      System.out.println("Helaas");
      speech.setText(
          "Location adding failed, you can try again if you want, or tell me where you want to travel");
    }
    repromptText.setText(
        "If you want to quit you can say exit, or you can say I want to travel to Amsterdam");
    reprompt.setOutputSpeech(repromptText);
    return SpeechletResponse.newAskResponse(speech, reprompt);
  }

  /**
   * De functie word aangeroepen met de CompositionIntent intent. En slaat de gezinscompositie op.
   * Dit is een aantal, en zijn altijd adults. In de toekomst kan je hier nog andere age types aan
   * toevoegen.
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
   * Deze functie word aangeroepen met de Cheapest intent. En zoekt de goedkoopste reisdatum + prijs
   * op voor de gegeven origin + destination. Je kan de functie aanroepen met alleen een
   * destination, de functie kiest dan Amsterdam Centraal als origin.
   */
  private SpeechletResponse getCheapestOption(Intent intent) {
    if (cheapestRequest == null) {
      return getCheapestOptionFromServer(intent);
    } else {
      return bookCheapestOption();
    }
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
  private SpeechletResponse newAskResponse(String stringOutput, String repromptText,StandardCard card) {
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(stringOutput);

    PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
    repromptOutputSpeech.setText(repromptText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(repromptOutputSpeech);

    return SpeechletResponse.newAskResponse(outputSpeech, reprompt, card);
  }

  private SpeechletResponse newAskResponse(String stringOutput, String repromptText) {
    PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
    outputSpeech.setText(stringOutput);

    PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
    repromptOutputSpeech.setText(repromptText);
    Reprompt reprompt = new Reprompt();
    reprompt.setOutputSpeech(repromptOutputSpeech);

    return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
  }

  /**
   * Zoekt een leuk plaatje bij je gekozen reis, super leuk, super tof, super gaaf.
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
   * Parse the time from HH:mm to HHmm
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
   */
  private String parseDate(String newDate) {
    return newDate.replace("-", "");
  }

  private void addUser(String accestoken) {
    AmazonApi aapi = new AmazonApi();
    GetUserRequest request = new GetUserRequest(accestoken);
    AmazonUser model = aapi.getResponse(request);
    DB.addUser(UNIQUE_USER_ID, model.getName(), model.getEmail());
  }

  private void initializeDateandTime() {
    Calendar cal = Calendar.getInstance();
    date =
        "" + cal.get(Calendar.YEAR) + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + String
            .format("%02d", cal.get(Calendar.DAY_OF_MONTH));
    time = "" + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY) + 2) + String
        .format("%02d", cal.get(Calendar.MINUTE));
  }

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

  private SpeechletResponse linkaccountCard() {
    LinkAccountCard card = new LinkAccountCard();
    PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
    speech.setText("Go to your alexa app to link this skill");
    return SpeechletResponse.newTellResponse(speech, card);
  }

  private SpeechletResponse delegateDirective() {
    DelegateDirective dd = new DelegateDirective();

    ArrayList<Directive> directiveList = new ArrayList<>();
    directiveList.add(dd);

    SpeechletResponse speechletResp = new SpeechletResponse();
    speechletResp.setDirectives(directiveList);
    speechletResp.setNullableShouldEndSession(false);
    return speechletResp;
  }

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

  private SpeechletResponse bookCheapestOption() {

    Connections data = API.getResponse(cheapestRequest);
    UNIQUE_NS_ID = data.getData().getUid();
    Connection selectedJourney = data.getData().getConnections().get(0);
    ProvisionalBookingRequest request = new ProvisionalBookingRequest(
        chooseCitySpeechlet.UNIQUE_NS_ID,
        selectedJourney.getId(),
        selectedJourney.getOffers().get(1).getId(),
        "true",
        selectedJourney.getOrigin().getCode(),
        selectedJourney.getDestination().getCode());
    Dnr model = chooseCitySpeechlet.API.getResponse(request);

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
}
