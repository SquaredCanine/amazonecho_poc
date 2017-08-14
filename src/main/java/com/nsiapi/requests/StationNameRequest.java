package com.nsiapi.requests;

import com.requestInterface;

/**
 * Created by doombringer on 7/31/2017.
 */
public class StationNameRequest implements requestInterface {
    private String city;
    private String gotoUrl = "stations/";

    public StationNameRequest(String city){
        this.city = city;
    }

    public String getRequestUrl(){
        return (gotoUrl + "?name=" + city);
    }
}
