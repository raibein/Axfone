package com.raben.axfone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawLayout;
    private ActionBarDrawerToggle mToggle;


    RequestQueue rq;

    EditText firstnameText, surnameText, addressText, cityText, countryText;

    TextView welcome;

    Toolbar toolbar;

    NavigationView nvDrawer;




    //private int mNavItemId;


    String id, firstname, surname, address, city, country;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rq = Volley.newRequestQueue(this);

        firstnameText = (EditText) findViewById(R.id.activity_main_firstname);
        surnameText = (EditText) findViewById(R.id.activity_main_surname);
        addressText = (EditText) findViewById(R.id.activity_main_address);
        cityText = (EditText) findViewById(R.id.activity_main_city);
        countryText = (EditText) findViewById(R.id.activity_main_country);

        welcome = (TextView) findViewById(R.id.activity_main_welcome);

        // Start : cannot edit to all the EditText
        firstnameText.setTag(firstnameText.getKeyListener());
        firstnameText.setKeyListener(null);

        surnameText.setTag(surnameText.getKeyListener());
        surnameText.setKeyListener(null);

        addressText.setTag(addressText.getKeyListener());
        addressText.setKeyListener(null);

        cityText.setTag(cityText.getKeyListener());
        cityText.setKeyListener(null);

        countryText.setTag(countryText.getKeyListener());
        countryText.setKeyListener(null);
        // End : cannot edit to all the EditText



        Cache cache = new DiskBasedCache(getCacheDir(), 1024+1024);
        Network network = new BasicNetwork(new HurlStack());
        rq = new RequestQueue(cache, network);
        rq.start();



        //nvDrawer.getMenu().findItem(mNavItemId).setChecked(true);


        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawLayout, R.string.open, R.string.close);
        mDrawLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        nvDrawer = (NavigationView) findViewById(R.id.nav_menu);
        nvDrawer.setNavigationItemSelectedListener(this);
        nvDrawer.bringToFront();

        //setupDrawerContent(nvDrawer);




        /*
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        String _token = globalVariable.getToken();

        if(_token.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Session has expired, please log in again", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        */

        //setupDrawerContent();

        sendjsonrequest();

        editRequest();

        updateRequest();

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }


    /*
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                public boolean onNavigationItemSelected(MenuItem menuItem){
                        onOptionsItemSelected(menuItem);
                        return true;
                    }
                }
        );
    }
    */

    /*
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        item.setChecked(true);
        mNavItemId = item.getItemId();


        return true;
    }
    */



    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }


        //respond to menu item selection

        switch (item.getItemId()) {
            case R.id.menu_main:
                startActivity(new Intent(this, MainActivity.class));
                return true;

            case R.id.menu_parking_right:
                startActivity(new Intent(this, CustomersActivity.class));
                return true;

            case R.id.menu_log_out:
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            /*
            case R.id.menu_parking_right:
                startActivity(new Intent(this, Help.class));
            */

            default:
                return super.onOptionsItemSelected(item);
        }
    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        item.setChecked(true);
        mDrawLayout.closeDrawers();

        Toast.makeText(this, "Checking...", Toast.LENGTH_SHORT).show();

        if(id == R.id.menu_parking_right){
            //Toast.makeText(this, "Parking Right", Toast.LENGTH_SHORT).show();

            Intent searchIntent =  new Intent(MainActivity.this, CustomersActivity.class);
            startActivity(searchIntent);
            Toast.makeText(this, "Checking...", Toast.LENGTH_SHORT).show();
            return true;
            //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.menu_log_out){
            Intent searchIntent =  new Intent(MainActivity.this, LoginActivity.class);
            startActivity(searchIntent);
            //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else{
            Intent searchIntent =  new Intent(this, MainActivity.class);
            startActivity(searchIntent);
            //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }







    public void editRequest(){

        Button mEditableButton = (Button) findViewById(R.id.activity_main_edit);

        mEditableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                firstnameText.setKeyListener((KeyListener) firstnameText.getTag());
                surnameText.setKeyListener((KeyListener) surnameText.getTag());
                addressText.setKeyListener((KeyListener) addressText.getTag());
                cityText.setKeyListener((KeyListener) cityText.getTag());
                countryText.setKeyListener((KeyListener) countryText.getTag());


                Toast.makeText(MainActivity.this, "Edit is enable", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateRequest(){


        Button mUpdateButton = (Button) findViewById(R.id.activity_main_update);


        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String first_name_text = firstnameText.getText().toString();
                final String surname_text = surnameText.getText().toString();
                final String address_text = addressText.getText().toString();
                final String city_text = cityText.getText().toString();
                final String country_text = countryText.getText().toString();


                SessionManager session = new SessionManager(getApplicationContext());

                session.checkLogin();

                HashMap<String, String> user = session.getUserDetails();

                String sess_id = user.get(SessionManager.KEY_userid);
                String sess_token = user.get(SessionManager.KEY_TOKEN);



                // Calling Application class (see application tag in AndroidManifest.xml)
                GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                // Get name and email from global/application context
                String _id  = globalVariable.getId();

                String url = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/usersContacts/"+sess_id;

                //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);

                                        String status = jsonResponse.getString("status");


                                        if (status.equals("success")) {

                                            Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                            startActivity(intent);

                                        }
                                        else{
                                            Toast.makeText(MainActivity.this, "Error in updating data", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){

                        }
                    })

                    {

                        @Override
                        public String getBodyContentType() {
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("firstname", first_name_text);
                            params.put("surname", surname_text);
                            params.put("address", address_text);
                            params.put("city", city_text);
                            params.put("country", country_text);

                            return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {


                            // Calling Application class (see application tag in AndroidManifest.xml)
                            GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                            // Get name and email from global/application context
                            String _token = globalVariable.getToken();

                            HashMap<String, String> headers = new HashMap<>();
                            String credentials = _token;
                            //String credentials = "0e1a95d424abaa55ea677aa07812061363040029";
                            String auth = "Bearer " + credentials;

                            headers.put("Content-Type", "application/json");
                            //headers.put("Content-Type", "application/x-www-form-urlencoded");
                            headers.put("Authorization", auth);

                            return headers;
                        }
                    };

                    //rq.add(jsonObjectRequest);
                    rq.add(stringRequest);
                }
            });
        }








    public void  sendjsonrequest(){

        // Calling Application class (see application tag in AndroidManifest.xml)
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        // Get name and email from global/application context
        String _id  = globalVariable.getId();

        String url = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/usersContacts/"+_id;

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    String status = jsonResponse.getString("status");


                    String result = jsonResponse.getString("result");

                    JSONObject newJOresult = new JSONObject(result);
                    String userContact = newJOresult.getString("userContact");

                    JSONObject usrContacts = new JSONObject(userContact);
                    id = usrContacts.getString("id");
                    firstname = usrContacts.getString("firstname");
                    surname = usrContacts.getString("surname");
                    address = usrContacts.getString("address");
                    city = usrContacts.getString("city");
                    country = usrContacts.getString("country");

                    String st_welcome = usrContacts.getString("firstname");
                    String st_surname = usrContacts.getString("surname");


                    if (status.equals("success")) {
                        firstnameText.setText(firstname);
                        surnameText.setText(surname);
                        addressText.setText(address);
                        cityText.setText(city);
                        countryText.setText(country);

                        String old =  "Welcome "+st_welcome + " " + st_surname;
                        welcome.setText(old);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Error in importing data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){

                }
            })
        {
            public Map<String, String> getHeaders() throws AuthFailureError {


                // Calling Application class (see application tag in AndroidManifest.xml)
                GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                // Get name and email from global/application context
                String _token = globalVariable.getToken();

                HashMap<String, String> headers = new HashMap<>();
                String credentials = _token;
                //String credentials = "0e1a95d424abaa55ea677aa07812061363040029";
                String auth = "Bearer " + credentials;

                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);

                return headers;
            }
        };

            //rq.add(jsonObjectRequest);
            rq.add(stringRequest);
        }
}
