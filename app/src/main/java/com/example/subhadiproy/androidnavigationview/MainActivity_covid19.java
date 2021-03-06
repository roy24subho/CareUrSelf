package com.example.subhadiproy.androidnavigationview;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity_covid19 extends AppCompatActivity {

    TextView tvCases,tvTodayCases,tvTotalDeaths,tvTodayDeaths,tvRecovered,tvActive,tvCritical,tvAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button trackCountries,trackStates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_covid19);

        try {

            tvCases = (TextView) findViewById(R.id.tvCases);
            tvRecovered = (TextView) findViewById(R.id.tvRecovered);
            tvCritical = (TextView) findViewById(R.id.tvCritical);
            tvActive = (TextView) findViewById(R.id.tvActive);
            tvTodayCases = (TextView) findViewById(R.id.tvTodayCases);
            tvTotalDeaths = (TextView) findViewById(R.id.tvTotalDeaths);
            tvTodayDeaths = (TextView) findViewById(R.id.tvTodayDeaths);
            tvAffectedCountries = (TextView) findViewById(R.id.tvAffectedCountries);

            trackCountries = (Button) findViewById(R.id.track_btn);
            trackStates = (Button) findViewById(R.id.track_btn_state);

            simpleArcLoader = (SimpleArcLoader) findViewById(R.id.loader);
            scrollView = (ScrollView) findViewById(R.id.scrollStats);
            pieChart = (PieChart) findViewById(R.id.piechart);


            trackStates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!haveNetwork()) {

                        Toast.makeText(MainActivity_covid19.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();

                    } else {

                        startActivity(new Intent(MainActivity_covid19.this, AffectedStates.class));
                    }
                }
            });

            trackCountries.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!haveNetwork()) {

                        Toast.makeText(MainActivity_covid19.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();

                    } else {

                        startActivity(new Intent(MainActivity_covid19.this, AffectedCountries.class));
                    }
                }
            });
            fetchData();
        }
        catch (Exception e){

            e.printStackTrace();
            Toast.makeText(MainActivity_covid19.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();

        }
    }
    private void fetchData() {
        try {
            String url = "https://corona.lmao.ninja/v2/all/";
            simpleArcLoader.start();
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());

                        tvCases.setText(jsonObject.getString("cases"));
                        tvRecovered.setText(jsonObject.getString("recovered"));
                        tvCritical.setText(jsonObject.getString("critical"));
                        tvActive.setText(jsonObject.getString("active"));
                        tvTodayCases.setText(jsonObject.getString("todayCases"));
                        tvTotalDeaths.setText(jsonObject.getString("deaths"));
                        tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                        tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                        pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#01b7ff")));
                        pieChart.addPieSlice(new PieModel("Recoverd", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#00ff0c")));
                        pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#ff0500")));

                        pieChart.startAnimation();

                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        simpleArcLoader.stop();
                        simpleArcLoader.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity_covid19.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        }
        catch (Exception e){

            e.printStackTrace();
            Toast.makeText(MainActivity_covid19.this, "No Internet Connection!!", Toast.LENGTH_SHORT).show();

        }

    }

    private boolean haveNetwork(){

        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info:networkInfos){

            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI = true;
            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData = true;

        }

        return have_MobileData||have_WIFI;
    }


}
