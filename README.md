# amazonecho_poc
<a href="https://squaredcanine.github.io/amazonecho_poc">Java Docs</a><br>
For NS International I made an application for the Amazon Echo, which allows you to order tickets to popular destinations in Europe.

To make it work for you you need to add a java file in src/main/java/chooseCity. containing the following:

```java
package chooseCity;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

/**
 * This class could be the handler for an AWS Lambda function powering an Alexa Skills Kit
 * experience. To do this, simply set the handler field in the AWS Lambda console to
 * "chooseCity.chooseCitySpeechletRequestStreamHandler" For this to work, you'll also need to build
 * this project using the {@code lambda-compile} Ant task and upload the resulting zip file to power
 * your function.
 */
public final class chooseCitySpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds;

    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds = new HashSet<String>();
        supportedApplicationIds.add("amzn1.ask.skill.[UNIQUE-VALUE-HERE]");
    }

    public chooseCitySpeechletRequestStreamHandler() {
        super(new chooseCitySpeechlet(), supportedApplicationIds);
    }
}
```

You can find the database model and create statement in sql/.
You also have to create a few environment variables like or replace the following:
```java
System.getenv("username_DB"); //Username for the database
System.getenv("password_DB"); //Password for the database
System.getenv("location_DB"); //Location on the web for the database
System.getenv("password_email); //Password for the email used to send the booking
System.getenv("username_email); //Username(or email address) used to send the booking
```

And account linking must be setup, you can use <a href="https://developer.amazon.com/blogs/post/Tx3CX1ETRZZ2NPC/Alexa-Account-Linking-5-Steps-to-Seamlessly-Link-Your-Alexa-Skill-with-Login-wit">this</a> tutorial.

