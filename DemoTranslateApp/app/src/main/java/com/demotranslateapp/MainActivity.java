package com.demotranslateapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String requestUrl = "http://api.mymemory.translated.net/get?q=good&langpair=en|ja";
    ListView lv;
    ArrayAdapter<String> adapter;
    List<String> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        process();

    }

    public void addControls() {
        lv = (ListView) findViewById(R.id.lv);
        resultList = new ArrayList<>();

    }

    public void process() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final ProgressDialog pg = new ProgressDialog(MainActivity.this);
        pg.setMessage("loading...");
        pg.show();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, requestUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONObject jObResponseData = jsonObject.getJSONObject("responseData");
                            resultList.add(jObResponseData.getString("translatedText"));
                            JSONArray jArrMatch = jsonObject.getJSONArray("matches");
                            List<String> resultList = new ArrayList<>();

                            for (int i = 0; i < jArrMatch.length(); i++) {
                                JSONObject ob = jArrMatch.getJSONObject(i);
                                resultList.add(ob.getString("translation"));
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, resultList);
                            lv.setAdapter(adapter);
                            pg.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(jsObjRequest);
    }

}
