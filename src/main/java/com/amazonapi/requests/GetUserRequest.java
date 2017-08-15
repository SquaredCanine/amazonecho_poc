package com.amazonapi.requests;

import com.RequestInterface;

public class GetUserRequest implements RequestInterface {
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
