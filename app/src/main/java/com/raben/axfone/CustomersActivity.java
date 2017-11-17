package com.raben.axfone;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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
import com.raben.axfone.Adapter.CustomerAdapter;
import com.raben.axfone.Adapter.MyExpandableListAdapter;
import com.raben.axfone.Adapter.ParkingIDAdapter;
import com.raben.axfone.JsonHelper.XMLParser;
import com.raben.axfone.POJO.Customer;
import com.raben.axfone.POJO.ParentRow;
import com.raben.axfone.POJO.ParkingID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by raben on 18-Oct-17.
 */
public class CustomersActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, NavigationView.OnNavigationItemSelectedListener{

    RequestQueue rq;

    RequestQueue parkingRQ;

    ListView lv;

    Customer customer;

    CustomerAdapter customerList;

    ArrayList<Customer> customerArrayList = new ArrayList<>();

    ParkingID parkingID;

    ParkingIDAdapter parkingIdList;

    ArrayList<ParkingID> parkingArrayList = new ArrayList<>();

    int current_page;

    XMLParser parser;

    String TempItem;



    private DrawerLayout mDrawLayout;
    private ActionBarDrawerToggle mToggle;
    Toolbar toolbar;
    NavigationView nvDrawer;




    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    private MyExpandableListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<ParentRow> parentList = new ArrayList<ParentRow>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<ParentRow>();
    private MenuItem searchItem;



    EditText editSearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customers);

        lv = (ListView) findViewById(R.id.listview_display_customers);

        rq = Volley.newRequestQueue(this);

        parkingRQ= Volley.newRequestQueue(this);

        parser = new XMLParser();

        TempItem = getIntent().getStringExtra("ListViewValue");

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        editSearch = (EditText) findViewById(R.id.search);



        //parentList = new ArrayList<ParentRow>();
        //showTheseParentList = new ArrayList<ParentRow>();

        //displayList();

        //expandAll();


        /*
        adapter = new CustomerDetailAdapter(this, getData());
        ListView list = (ListView) findViewById(R.id.listview_display_customers);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Customer customer = (Customer) adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), SingleCustomerActivity.class);
                intent.putExtra(KEY_ID, customer.getId());
                intent.putExtra(KEY_NAME, customer.getName());

                startActivity(intent);
            }
        });
        */


        // This is for the search toolbar
        handleIntent(getIntent());


        Cache cache = new DiskBasedCache(getCacheDir(), 1024+1024);
        Network network = new BasicNetwork(new HurlStack());
        rq = new RequestQueue(cache, network);
        rq.start();

        parkingRQ = new RequestQueue(cache, network);
        parkingRQ.start();



        mDrawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawLayout, R.string.open, R.string.close);
        mDrawLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nvDrawer = (NavigationView) findViewById(R.id.nav_menu);
        nvDrawer.setNavigationItemSelectedListener(this);
        nvDrawer.bringToFront();


        /*
        GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        String _token = globalVariable.getToken();

        Toast.makeText(CustomersActivity.this, _token, Toast.LENGTH_SHORT).show();

        if(_token.isEmpty())
        {
            Toast.makeText(CustomersActivity.this, "Session has expired, please log in again", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CustomersActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        */


        getAllCustomerInformation(); // The function created to fetch all the related customer records



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

            Intent searchIntent =  new Intent(CustomersActivity.this, CustomersActivity.class);
            startActivity(searchIntent);
            Toast.makeText(this, "Checking...", Toast.LENGTH_SHORT).show();
            return true;
            //overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        else if(id == R.id.menu_log_out){
            Intent searchIntent =  new Intent(CustomersActivity.this, LoginActivity.class);
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




    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //searchView.setSearchableInfo(SearchManager.getSearchableinfo(getComponentName()));
        //searchItem.setOnQueryTextListener(this);

        return true;
    }
    */

    /*

    public boolean onCreateOptionMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);

        MenuItem searchActionBarItem = menu.findItem(R.id.search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchActionBarItem);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setIconifiedByDefault(true);

        return true;
    }
    */


    /*
    private ArrayList<CustomerDetail> getData(){
        ArrayList<CustomerDetail> objItem = new ArrayList<CustomerDetail>();


        String[] names = getResources().getStringArray(R.array.name);
        String[] ids = getResources().getStringArray(R.array.id);
        String[] times  = getResources().getStringArray(R.array.time);

        for(int i = 0; i < names.length; i++)
        {
            CustomerDetail customerDetail = new CustomerDetail();
            customerDetail.setId(ids[i]);
            customerDetail.setName(names[i]);
            customerDetail.setTime(times[i]);
        }

        return objItem;
    }
    */


    // Start : Search query handling
    public void  onNewIntent(Intent intent){
        setIntent(intent);
        handleIntent(intent);
    }

    public void onListItemClick(ListView l, View v, int position, long id){

    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String queryStr){

    }
    // End : Search query handling






    public void  getAllCustomerInformation(){

        String parkingurl = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/parkPlaces";

        final StringRequest parkingStringRequest = new StringRequest(Request.Method.GET, parkingurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                try {
                    JSONObject jR = new JSONObject(response1);

                    String status = jR.getString("status");
                    String result = jR.getString("result");
                    JSONObject newJOresult = new JSONObject(result);
                    String stOb = newJOresult.getString("parkPlaces");

                    JSONArray jar = new JSONArray(stOb); // This is for partplaces object




                    // Calling Application class (see application tag in AndroidManifest.xml)
                    GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                    // Get name and email from global/application context
                    String _id  = globalVariable.getId();

                    String url = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/customers?userId="+_id;






                    for (int j = 0; j < jar.length(); j++) {

                        JSONObject JOb = jar.getJSONObject(j);

                        final String p_id = JOb.getString("id").toString();
                        final String p_name = JOb.getString("name").toString();



                        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonResponse = new JSONObject(response);

                                    String status = jsonResponse.getString("status");
                                    String result = jsonResponse.getString("result");
                                    JSONObject newJOresult = new JSONObject(result);
                                    String strObjs = newJOresult.getString("customers");

                                    JSONArray ja = new JSONArray(strObjs); // This is for the customers object

                                    for (int i = 0; i < ja.length(); i++) {

                                        JSONObject JO = ja.getJSONObject(i);

                                        final String s_id = JO.getString("id").toString();
                                        final String s_name = JO.getString("name").toString();
                                        final String s_langId = JO.getString("langId").toString();
                                        final String s_slots = JO.getString("slots").toString();
                                        final String s_slotsUsed = JO.getString("slotsUsed").toString();
                                        final String s_parkPlaceId = JO.getString("parkPlaceId").toString();
                                        final String s_tariffId = JO.getString("tariffId").toString();

                                        if (p_id.equals(s_parkPlaceId)) {

                                            final String sname;

                                            sname = p_name;

                                            //customer = new Customer(s_id, s_name, s_langId, s_slots, s_slotsUsed, sname, s_tariffId);
                                            customer = new Customer(s_id, s_name, s_langId, s_slots, s_slotsUsed, s_parkPlaceId, sname, s_tariffId);
                                            customerArrayList.add(customer);

                                            // Customer(String id, String name, String langId, String slots, String slotsUsed, String parkPlaceId, String tariffId)
                                            //Toast.makeText(CustomersActivity.this, "Parking name : " + p_id + " " + p_name, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    if (status.equals("success")) {

                                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                Customer customer = customerArrayList.get(position);

                                                //CustomerDetail obj = (CustomerDetail) adapter.getItem(position);

                                                Intent intent = new Intent(CustomersActivity.this, SingleCustomerActivity.class);

                                                //intent.putExtra("db_name", lv.getItemAtPosition(position).toString());
                                                intent.putExtra("db_id", customer.getId().toString());
                                                intent.putExtra("db_name", customer.getName().toString());
                                                intent.putExtra("db_langId", customer.getLangId().toString());
                                                intent.putExtra("db_slots", customer.getSlots().toString());
                                                intent.putExtra("db_slotsUsed", customer.getSlotsUsed().toString());
                                                intent.putExtra("db_parkPlaceId", customer.getParkPlaceId().toString());
                                                intent.putExtra("db_parkPlaceName", customer.getParkPlaceName().toString());
                                                intent.putExtra("db_tariffId", customer.getTariffId().toString());

                                                Toast.makeText(CustomersActivity.this, "Data : " + String.valueOf(position).toString(), Toast.LENGTH_SHORT).show();


                                                startActivity(intent);

                                                finish();

                                            }
                                        });

                                        //Button btnLoadMore = new Button(CustomersActivity.this);
                                        //btnLoadMore.setText("Click for load more...");

                                        //lv.addFooterView(btnLoadMore);

                                        CustomerAdapter customerAdaptor = new CustomerAdapter(CustomersActivity.this, customerArrayList);
                                        lv.setAdapter(customerAdaptor);

                                /*
                                btnLoadMore.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        new loadMoreListView().execute();
                                    }
                                });
                                */
                                    } else {
                                        Toast.makeText(CustomersActivity.this, "Error in importing data", Toast.LENGTH_SHORT).show();
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


                        //Toast.makeText(CustomersActivity.this, "Parking name : " + p_id + " " + p_name, Toast.LENGTH_SHORT).show();

                        parkingID = new ParkingID(p_id, p_name);
                        parkingArrayList.add(parkingID);

                    }

                    if(status.equals("success")){
                        Toast.makeText(CustomersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        parkingRQ.add(parkingStringRequest);

    }



    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        //expandAll();
        getAllCustomerInformation();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        //expandAll();
        getAllCustomerInformation();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        //expandAll();
        getAllCustomerInformation();
        return false;
    }

    private class loadMoreListView extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            ProgressDialog pDialog = new ProgressDialog(CustomersActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            */
        }

        @Override
        protected Void doInBackground(Void... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    current_page = 0;

                    // Calling Application class (see application tag in AndroidManifest.xml)
                    GlobalClass globalVariable = (GlobalClass) getApplicationContext();

                    // Get name and email from global/application context
                    String _id  = globalVariable.getId();

                    String url = "http://offstreet-dev-1.axfone.eu/myAPI/api/v1/customers?userId="+_id+"/page="+current_page;

                    //xml = parser.getXmlFromUrl(url);
                    //doc = parser.getDomElement(xml);



                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                String status = jsonResponse.getString("status");
                                String result = jsonResponse.getString("result");
                                JSONObject newJOresult = new JSONObject(result);
                                String strObjs = newJOresult.getString("customers");

                                JSONArray ja= new JSONArray(strObjs);

                    /*
                    JSONArray strArr = newJOresult.getJSONArray("customers");
                    int length = strArr.length();
                    */


                                for(int i = 0; i < ja.length(); i++) {

                                    final int j = i;

                                    JSONObject JO = ja.getJSONObject(i);

                                    final String s_id = JO.getString("id").toString();
                                    final String s_name = JO.getString("name").toString();
                                    final String s_langId = JO.getString("langId").toString();
                                    final String s_slots = JO.getString("slots").toString();
                                    final String s_slotsUsed= JO.getString("slotsUsed").toString();
                                    final String s_parkPlaceId= JO.getString("parkPlaceId").toString();
                                    final String s_parkPlaceName= JO.getString("parkPlaceName").toString();
                                    final String s_tariffId = JO.getString("tariffId").toString();


                                    customer = new Customer(s_id, s_name, s_langId, s_slots, s_slotsUsed, s_parkPlaceId, s_parkPlaceName, s_tariffId);
                                    customerArrayList.add(customer);

                                }

                                if (status.equals("success")) {

                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            Customer customer = customerArrayList.get(position);

                                            //CustomerDetail obj = (CustomerDetail) adapter.getItem(position);

                                            Intent intent = new Intent(CustomersActivity.this, SingleCustomerActivity.class);

                                            //intent.putExtra("db_name", lv.getItemAtPosition(position).toString());
                                            intent.putExtra("db_id", customer.getId().toString());
                                            intent.putExtra("db_name", customer.getName().toString());
                                            intent.putExtra("db_langId", customer.getLangId().toString());
                                            intent.putExtra("db_slots", customer.getSlots().toString());
                                            intent.putExtra("db_slotsUsed", customer.getSlotsUsed().toString());
                                            intent.putExtra("db_parkPlaceId", customer.getParkPlaceId().toString());
                                            intent.putExtra("db_parkPlaceName", customer.getParkPlaceName().toString());
                                            intent.putExtra("db_tariffId", customer.getTariffId().toString());


                                            Toast.makeText(CustomersActivity.this, "Data : " + String.valueOf(position).toString(), Toast.LENGTH_SHORT).show();


                                            startActivity(intent);

                                            finish();

                                        }
                                    });

                                    CustomerAdapter customerAdaptor = new CustomerAdapter(CustomersActivity.this, customerArrayList);
                                    lv.setAdapter(customerAdaptor);

                                    int currentPosition = lv.getFirstVisiblePosition();

                                    //lv.setSelectionFromTop(currentPosition + 1, 0);
                                    lv.setSelection(currentPosition+1);

                                    Toast.makeText(CustomersActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(CustomersActivity.this, "Error in importing data", Toast.LENGTH_SHORT).show();
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //super.onPostExecute(aVoid);
            ProgressDialog pDialog = new ProgressDialog(CustomersActivity.this);
            pDialog.dismiss();
        }
    }

    /*
    private void displayList(){
        getAllCustomerInformation();

        myList = (ExpandableListView) findViewById(R.id.expandableListView_search);
        listAdapter = new MyExpandableListAdapter(CustomersActivity.this, parentList);

        myList.setAdapter(listAdapter);
    }
    */
}
