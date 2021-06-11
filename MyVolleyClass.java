package com.chetana.singletonedesignpattern;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyVolleyClass {

    private static RequestQueue requestQueue;
    private static StringRequest mStringRequest;
    private static JsonObjectRequest jsonObjectRequest;
    private static String responseBody ="";

    public static String getDataRequest(Context contetxt, String url){
        requestQueue = Volley.newRequestQueue(contetxt);

        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseBody = response.replaceAll("\n","");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","Error :" + error.toString());
            }
        });

        requestQueue.add(mStringRequest);

        return responseBody;

    }

    public static void postDataRequest(Context contetxt, String url,Map<String,String> params){
        requestQueue = Volley.newRequestQueue(contetxt);

        mStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String val = response;
                Toast.makeText(contetxt, "Data send to API", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contetxt, "Error during post call", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", "Alif");
                params.put("domain", "http://itsalif.info");

                // at last we are
                // returning our params.
                return params;
            }
        };

        requestQueue.add(mStringRequest);

    }

    public static void postJSONDataRequest(Context contetxt, String url,Map<String,String> params){
        requestQueue = Volley.newRequestQueue(contetxt);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(contetxt, "Data send to API", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }
}
