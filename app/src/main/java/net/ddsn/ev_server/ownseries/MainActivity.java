package net.ddsn.ev_server.ownseries;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Adapters.SeriesAdapter;
import Lib.Ajax;

public class MainActivity extends AppCompatActivity
{

    RecyclerView listOfSeries;
    Ajax Fetch;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = getString(R.string.ws) + "/get-series/1";
        Fetch = new Ajax(this);
        Fetch.GET(url, new Ajax.Fetch() {
            @Override
            public void response(JSONObject data) throws JSONException {

                JSONArray series = data.getJSONObject("data").getJSONArray("series");
                listOfSeries = findViewById(R.id.list_series);
                listOfSeries.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                SeriesAdapter adapter = new SeriesAdapter(getApplicationContext(),series);
                listOfSeries.setAdapter(adapter);
            }
        });

    }


}
