package net.ddsn.ev_server.ownseries;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Adapters.ChapterAdapter;
import Lib.Ajax;

public class Season extends AppCompatActivity {

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        ImageView im = findViewById(R.id.back_arrow);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Ajax Fetch = new Ajax(this);
        String url = getString(R.string.ws) + "/get-seasons/" + id;
        final String picture = intent.getStringExtra("image");

        Fetch.GET(url, new Ajax.Fetch() {
            @Override
            public void response(final JSONObject data) throws JSONException {

                Spinner dropdown = findViewById(R.id.dropdown);
                int size = data.getJSONObject("data").getJSONArray("seasons").length() + 1;
                String[] items = new String[size];

                for(int i = 0; i < size; i++)
                {
                    String index = Integer.toString(i + 1);
                    items[i] = "Temporada " + index;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, items);
                dropdown.setAdapter(adapter);

                dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            getChapters(data.getJSONObject("data").getJSONArray("seasons").getJSONObject(position));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                ImageView image = findViewById(R.id.season_image);
                Picasso.get().load(picture).into(image);
                getChapters(data.getJSONObject("data").getJSONArray("seasons").getJSONObject(0));
            }
        });
    }

    final void getChapters(JSONObject season) throws JSONException {
        Ajax Fetch = new Ajax(this);
        String url = getString(R.string.ws) + "/get-chapters/" + id + "/" + season.getString("_id");

        Fetch.GET(url, new Ajax.Fetch() {
            @Override
            public void response(JSONObject data) throws JSONException {
                RecyclerView rv = findViewById(R.id.season_list);
                ChapterAdapter adapter = new ChapterAdapter(getApplicationContext(), data.getJSONObject("data").getJSONArray("chapters"));
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rv.setNestedScrollingEnabled(false);
                rv.setAdapter(adapter);
            }
        });
    }
}
