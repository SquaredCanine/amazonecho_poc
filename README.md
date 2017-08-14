# amazonecho_poc
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
