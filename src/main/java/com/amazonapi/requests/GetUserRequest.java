package com.amazonapi.requests;

import com.requestInterface;

public class GetUserRequest implements requestInterface {
    private String accesToken;

    public GetUserRequest(String accesToken){
        this.accesToken = accesToken;
    }

    public String getRequestUrl(){
        return accesToken;
    }
    public String getAccesToken() {
        return accesToken;
    }

    public void setAccesToken(String accesToken) {
        this.accesToken = accesToken;
    }
}
