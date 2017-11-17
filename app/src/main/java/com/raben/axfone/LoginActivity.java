package com.raben.axfone;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText eUsername = (EditText) findViewById(R.id.activity_login_username);
        final EditText epassword = (EditText) findViewById(R.id.activity_login_password);

        final Button mLoginButton = (Button) findViewById(R.id.activity_login_login);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = eUsername.getText().toString();
                final String password = epassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "The fileds must not empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Handling", Toast.LENGTH_SHORT).show();
                }



                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            SessionManager session = new SessionManager(getApplicationContext());

                            JSONObject jsonResponse = new JSONObject(response);

                            String status = jsonResponse.getString("status");


                            String result = jsonResponse.getString("result");

                            JSONObject newJOresult = new JSONObject(result);
                            String loginToken = newJOresult.getString("loginToken");

                            JSONObject joLoginToken = new JSONObject(loginToken);
                            String userId = joLoginToken.getString("userId");
                            String token = joLoginToken.getString("token");


                            // Calling Application class (see application tag in AndroidManifest.xml)
                            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                            //Set name and email in global/application context
                            globalVariable.setId(userId);
                            globalVariable.setToken(token);


                            if (status.equals("success")) {

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                session.createLoginSession(userId, token);

                                /*
                                intent.putExtra("userId", _userId);
                                intent.putExtra("token", _token);
                                */


                                LoginActivity.this.startActivity(intent);
                            }
                            else
                            {
                                //Toast.makeText(LoginActivity.this, "Fail to login", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login failed").setNegativeButton("Retry", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(username, password, responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);





                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login failed").setNegativeButton("Retry", null).create().show();
                    }
                };
            }
        });
    }
}