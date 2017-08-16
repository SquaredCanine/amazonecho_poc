package nl.nsi.demo.echo;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import java.util.HashSet;
import java.util.Set;

/**
 * This class could be the handler for an AWS Lambda function powering an Alexa Skills Kit
 * experience. To do this, simply set the handler field in the AWS Lambda console to
 * "nl.nsi.demo.echo.NSInternationalSpeechletRequestStreamHandler" For this to work, you'll also need to build
 * this project using the {@code lambda-compile} Ant task and upload the resulting zip file to power
 * your function.
 */
public final class NSInternationalSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

  private static final Set<String> supportedApplicationIds;

  static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
    supportedApplicationIds = new HashSet<>();
    supportedApplicationIds.add("amzn1.ask.skill.df22d8e8-7b48-4d1a-b370-d0601cddcaee");
  }

  public NSInternationalSpeechletRequestStreamHandler() {
    super(new NSInternationalSpeechlet(), supportedApplicationIds);
  }
}
