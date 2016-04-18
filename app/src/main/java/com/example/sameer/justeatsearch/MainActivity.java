package com.example.sameer.justeatsearch;

import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // URL to get venues JSON
    private String jsonURLFormat = "http://public.je-apis.com:80/restaurants?c=&name=&q=";

    private String jsonURL;

    // JSON Node names
    private static final String TAG_RESPONSE = "response";
    private static final String TAG_RESTAURANTS = "Restaurants";
    private static final String TAG_ID = "Id";
    private static final String TAG_NAME = "Name";
    private static final String TAG_CUISINETYPES = "CuisineTypes";
    private static final String TAG_RATING = "RatingStars";
    private static final String TAG_LOGO = "Logo";
    private static final String TAG_LOGO_URL = "StandardResolutionURL";

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ((EditText)findViewById(R.id.editText)).setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        ((ImageButton)findViewById(R.id.gps)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationMangaer = (LocationManager)
                        getSystemService(LOCATION_SERVICE);
                if (locationMangaer!=null){
                    locationMangaer.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            Geocoder gcd = new Geocoder(getBaseContext(),
                                    Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                if (addresses.size() > 0)
                                    ((EditText) findViewById(R.id.editText)).setText(addresses.get(0).getPostalCode());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                        }
                    }, getMainLooper());
                }
            }
        });

        ((ImageButton)findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.editText);
                String postCode = editText.getText().toString();
                postCode = postCode.replaceAll("\\s+","");//remove all spaces
                jsonURL = jsonURLFormat + postCode;

                //clear existing list
                SearchedContent.restaurantItemsList.clear();

                // Calling async task to get Restaurants from json
                new GetRestaurants().execute();
            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetRestaurants extends AsyncTask<Void, Void, Boolean> {

        // restaurants JSONArray
        JSONArray restaurants = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            boolean result = false;

            // Creating service handler class instance
            httpHandler sh = new httpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(jsonURL, httpHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null &&
                    jsonStr.length()>0) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);//string to JSON object
//                    jsonObj = jsonObj.getJSONObject(TAG_RESPONSE);//get response json object
                    // Getting JSON Array node
                    restaurants = jsonObj.getJSONArray(TAG_RESTAURANTS);

                    // looping through All restaurants
                    for (int i = 0; i < restaurants.length(); i++) {
                        JSONObject obj = restaurants.getJSONObject(i);

                        // adding restaurants to restaurants list
                        SearchedContent.RestaurantItem item = new SearchedContent.RestaurantItem();

                        item.setId( obj.getInt(TAG_ID) );
                        item.setName( obj.getString(TAG_NAME) );
                        item.setRating( obj.getInt(TAG_RATING) );

                        JSONArray logos = obj.getJSONArray(TAG_LOGO);
                        item.setLogo( logos.getJSONObject(0).getString(TAG_LOGO_URL) );

                        JSONArray cuisines = obj.getJSONArray(TAG_CUISINETYPES);
                        ArrayList<String> cuisineTypes = new ArrayList<>();
                        for (int counter = 0; counter < cuisines.length(); counter++) {
                            cuisineTypes.add( cuisines.getJSONObject(counter).getString(TAG_NAME) );
                        }
                        item.setCuisineTypes( cuisineTypes );

                        SearchedContent.restaurantItemsList.add(item);
                    }
                    result = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("httpHandler", "Couldn't get any data from the url");
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            if (result)//success
                ((ListView)findViewById(R.id.searchListView)).setAdapter(new SearchListAdaptor(getApplicationContext()));
            else {//failed to get the data
                // adding a dummy item to show no data!
                SearchedContent.RestaurantItem item = new SearchedContent.RestaurantItem();
                SearchedContent.restaurantItemsList.add(item);
            }
        }
    }
}
