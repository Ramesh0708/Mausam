package com.rameshchoudhary.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText etCity,etCountry;
    TextView tvresult;
    private static final String URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String API_KEY = "";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = findViewById(R.id.etcity);
        etCountry = findViewById(R.id.etcountry);
        tvresult = findViewById(R.id.tvResult);

    }

    public void getwheatherupdate(View view) {

        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Add your OpenWeatherMap API key to MainActivity.API_KEY", Toast.LENGTH_LONG).show();
            return;
        }

        if (city.equals("")) {
            Toast.makeText(getApplicationContext(), "Please enter a city name", Toast.LENGTH_SHORT).show();
            return;
        }

        String tempUrl = URL + "q=" + city + (country.isEmpty() ? "" : "," + country) + "&appid=" + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                try {
                    JSONObject jsonresponse = new JSONObject(response);
                    JSONArray jsonArray = jsonresponse.getJSONArray("weather");
                    JSONObject jsonObjectweather = jsonArray.getJSONObject(0);
                    String description = jsonObjectweather.getString("description");
                    JSONObject jsonObjectmain = jsonresponse.getJSONObject("main");
                    double temp = jsonObjectmain.getDouble("temp") -273.15;
                    double feelsLike = jsonObjectmain.getDouble("temp") -273.15 ;
                    float pressure = jsonObjectmain.getInt("pressure");
                    int humidity = jsonObjectmain.getInt("humidity");
                    JSONObject jsonObjectwind = jsonresponse.getJSONObject("wind");
                    String wind = jsonObjectwind.getString("speed");
                    JSONObject jsonObjectclouds = jsonresponse.getJSONObject("clouds");
                    String clouds = jsonObjectclouds.getString("all");
                    JSONObject jsonObjectsys = jsonresponse.getJSONObject("sys");
                    String countryname = jsonObjectsys.getString("country");
                    String cityname = jsonresponse.getString("name");
                    tvresult.setTextColor(Color.rgb(68,134,199));
                    output += "Current weather of " + cityname + " ( " + countryname + " ) "
                            + "\n temp: " + df.format(temp) + "°C"
                            + "\n Feels like: " + df.format(feelsLike) + " °C"
                            + " \n Humidity: " + humidity + "%"
                            + "\n Description: " + description
                            + "\n Wind Speed:" + wind + "m/s (meters per seconds)"
                            + "\n Cloudiness: " + clouds + "%"
                            + "\n Pressure:" + pressure +  "hPa";
                    tvresult.setText(output);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();


            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
