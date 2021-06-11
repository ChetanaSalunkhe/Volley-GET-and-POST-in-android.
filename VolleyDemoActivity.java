package com.chetana.singletonedesignpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyDemoActivity extends AppCompatActivity {
    Context parent;
    Button buttonRequest, buttonPostRequest;

    private static final String TAG = VolleyDemoActivity.class.getName();
    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";
    private RequestQueue requestQueue;
    private StringRequest mStringRequest;

    JSONArray jsonArray;
    JSONObject jObj;
    ArrayList<String> listName;

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_demo);
        this.getSupportActionBar().setTitle("Volley Demo GET API");

        init();

        setListeners();
    }

    public void init(){
        parent= VolleyDemoActivity.this;

        buttonRequest = findViewById(R.id.buttonget);
        buttonPostRequest = findViewById(R.id.buttonpost);

        list = findViewById(R.id.list);

        listName = new ArrayList<String>();

    }

    public void setListeners(){

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAndRequestResponse();
            }
        });

        buttonPostRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> postParam= new HashMap<String, String>();
                postParam.put("un", "xyz@gmail.com");
                postParam.put("p", "somepasswordhere");

                try{
                    MyVolleyClass.postJSONDataRequest(parent,"http://httpbin.org/post",postParam);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendAndRequestResponse(){
        try{
            parseJson(MyVolleyClass.getDataRequest(this,url));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void parseJson(String res){
        listName.clear();
        String id="",name="",email="",gender="",contact="", mobile="",home="",office="";

        String usr = null;

        try {
            jObj = new JSONObject(res);
            usr = jObj.getString("users");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonArray = new JSONArray(usr);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listName.add(jsonObject.getString("name")+", "+jsonObject.getString("email"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listName);
        list.setAdapter(adapter);

    }
}
