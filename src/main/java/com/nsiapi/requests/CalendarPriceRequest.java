package com.nsiapi.requests;

import com.RequestInterface;

public class CalendarPriceRequest implements RequestInterface {
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
