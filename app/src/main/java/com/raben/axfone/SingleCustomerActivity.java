package com.raben.axfone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raben on 19-Oct-17.
 */
public class SingleCustomerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RequestQueue rq;

    private DrawerLayout mDrawLayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    NavigationView nvDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_customer);

        rq = Volley.newRequestQueue(this);

        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawLayout, R.string.open, R.string.close);
        mDrawLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nvDrawer = (NavigationView) findViewById(R.id.nav_menu);
        nvDrawer.setNavigationItemSelectedListener(this);
        nvDrawer.bringToFront();

        Bundle bundle = getIntent().getExtras();

        EditText sng_cust_id = (EditText) findViewById(R.id.activity_single_customer_id);
        EditText sng_cust_name = (EditText) findViewById(R.id.activity_single_customer_name);
        EditText sng_cust_lang_id = (EditText) findViewById(R.id.activity_single_customer_lang_id);
        EditText sng_cust_slots = (EditText) findViewById(R.id.activity_single_customer_slots);
        EditText sng_cust_slots_used = (EditText) findViewById(R.id.activity_single_customer_slots_used);
        EditText sng_cust_park_place_id = (EditText) findViewById(R.id.activity_single_customer_park_place_id);
        //TextView sng_cust_park_place_name = (TextView) findViewById(R.id.activity_single_customer_park_place_name);
        EditText sng_cust_tariff_id = (EditText) findViewById(R.id.activity_single_customer_tariff_id);


        if(bundle != null)
        {
            sng_cust_id.setText(bundle.getString("db_id"));
            sng_cust_name.setText(bundle.getString("db_name"));
            sng_cust_lang_id.setText(bundle.getString("db_langId"));
            sng_cust_slots.setText(bundle.getString("db_slots"));
            sng_cust_slots_used.setText(bundle.getString("db_slotsUsed"));
            sng_cust_park_place_id.setText(bundle.getString("db_parkPlaceId"));
            //sng_cust_park_place_name.setText(bundle.getString("db_parkPlaceName"));
            sng_cust_tariff_id.setText(bundle.getString("db_tariffId"));
        }


        editInfo(); // The function created to edit the information
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

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

            Intent searchIntent =  new Intent(SingleCustomerActivity.this, CustomersActivity.class);
            startActivity(searchIntent);
            Toast.makeText(this, "Checking...", Toast.LENGTH_SHORT).show();
            return true;
            //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.menu_log_out){
            Intent searchIntent =  new Intent(SingleCustomerActivity.this, LoginActivity.class);
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



    private void editInfo() {

        Button sn_update = (Button) findViewById(R.id.activity_single_customer_update);

        sn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText sng_id = (EditText) findViewById(R.id.activity_single_customer_id);
                EditText sng_name = (EditText) findViewById(R.id.activity_single_customer_name);
                EditText sng_lang_id = (EditText) findViewById(R.id.activity_single_customer_lang_id);
                EditText sng_slots = (EditText) findViewById(R.id.activity_single_customer_slots);
                EditText sng_slots_used = (EditText) findViewById(R.id.activity_single_customer_slots_used);
                EditText sng_park_place_id = (EditText) findViewById(R.id.activity_single_customer_park_place_id);
                EditText sng_tariff_id = (EditText) findViewById(R.id.activity_single_customer_tariff_id);


                final String idText = sng_id.getText().toString();
                final String nameText = sng_name.getText().toString();
                final String langIdText = sng_lang_id.getText().toString();
                final String slotsText = sng_slots.getText().toString();
                final String slotsUsedText = sng_slots_used.getText().toString();
                final String parkPlaceIdText = sng_park_place_id.getText().toString();
                final String tariffText = sng_tariff_id.getText().toString();

                String url = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/customers/"+idText;


                StringRequest stringRequest = new StringRequest(Request.Method.PATCH, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            String status = jsonResponse.getString("status");

                            if (status.equals("success")) {

                                Toast.makeText(SingleCustomerActivity.this, "successfully updated", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SingleCustomerActivity.this, MainActivity.class);

                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                        params.put("name", nameText);
                        params.put("langId", langIdText);
                        params.put("slots", slotsText);
                        //params.put("slotsUsed", slotsUsedText);
                        params.put("parkPlaceId", parkPlaceIdText);
                        params.put("tariffId", tariffText);

                        return params;
                    }


                    public Map<String, String> getHeaders() throws AuthFailureError {
                        // Calling Application class (see application tag in AndroidManifest.xml)
                        GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                        String _token = globalVariable.getToken();

                        HashMap<String, String> headers = new HashMap<>();
                        String credentials = _token;
                        String auth = "Bearer " + credentials;

                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", auth);

                        return headers;
                    }


                };

                rq.add(stringRequest);

            }
        });
     }


}