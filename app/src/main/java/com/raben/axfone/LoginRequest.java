package com.raben.axfone;

/**
 * Created by raben on 12-Oct-17.
 */

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raben on 10/9/2017.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/users/loginAttempt";
    public Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    public Map<String, String> getParams(){
        return params;
    }

}
