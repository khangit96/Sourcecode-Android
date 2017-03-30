package com.demokkaraoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String s = "nguyễn   hồ   duy khang";
        String sFix = s.replaceAll("\\s+", "-");
        String url = "http://lyric.tkaraoke.com/s.tim?q=nơi-anh&t=12";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Document document = null;
                        document = (Document) Jsoup.parse(response);
                        if (document != null) {
                            Elements subjectElements = document.select("div.div-result-item");
                            Toast.makeText(getApplicationContext(), "" + subjectElements.size(), Toast.LENGTH_LONG).show();
                            if (subjectElements != null && subjectElements.size() > 0) {
                                /*for (Element element : subjectElements) {
                                    Element imgSubject = element.getElementsByTag("img").first();


                                    if (imgSubject != null) {
                                        String imgUrl = "https:" + imgSubject.attr("data-cfsrc");
                                    }
                                }*/
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
}
