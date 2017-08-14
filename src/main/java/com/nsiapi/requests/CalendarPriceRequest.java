package com.nsiapi.requests;

import com.requestInterface;

public class CalendarPriceRequest implements requestInterface {
    private String destinationCode;
    private String originCode;
    private String goToUrl = "calendarprices/";

    public CalendarPriceRequest(String destinationCode, String originCode){
        this.destinationCode = destinationCode;
        this.originCode = originCode;
    }

    public String getRequestUrl(){
        return (goToUrl + originCode + "/" + destinationCode + "/outbound?lang=nl");
    }
}
