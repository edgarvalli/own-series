package Lib;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ajax
{

    String url, token;
    Context ctx;

    public Ajax(Context ctx)
    {
        this.ctx = ctx;
        this.token = "=a144sDajAcoimA2MAdjs#==";
    }

    public interface Fetch {
        void response(JSONObject data) throws JSONException;
    }

    public void GET(String url, final Fetch fetch)
    {

        RequestQueue q = Volley.newRequestQueue(ctx.getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest
        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject resp) {
                try {
                    fetch.response(resp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.getNetworkTimeMs();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Token", token);
                return headers;
            }
        };

        q.add(request);

    }
}
