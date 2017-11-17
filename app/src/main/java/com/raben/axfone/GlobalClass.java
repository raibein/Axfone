package com.raben.axfone;

import android.app.Application;

/**
 * Created by raben on 13-Oct-17.
 */
public class GlobalClass extends Application {

    private String id;
    private String token;


    public String getId() {

        return id;
    }

    public void setId (String aId) {

        id = aId;

    }

    public String getToken() {

        return token;
    }

    public void setToken(String aToken) {

        token = aToken;
    }

}
