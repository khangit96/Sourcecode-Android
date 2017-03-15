package com.demojsoup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<String> imageUrls;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RequestQueue queue;
    static int CURRENT_PAGE = 1;
    static String URL = "https://wallpaperscraft.com/catalog/anime/540x960";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecylerView();

        queue = Volley.newRequestQueue(this);
        loadMoreWallpaper();

    }

    public void loadMoreWallpaper() {
        findViewById(R.id.pb).setVisibility(View.VISIBLE);
        String urlNew = URL;
        if (CURRENT_PAGE > 1) {
            urlNew += "/page" + CURRENT_PAGE;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlNew,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        findViewById(R.id.pb).setVisibility(View.GONE);
                        Document document = null;
                        document = (Document) Jsoup.parse(response);
                        if (document != null) {
                            Elements subjectElements = document.select("div.wallpaper_pre");

                            if (subjectElements != null && subjectElements.size() > 0) {
                                for (Element element : subjectElements) {
                                    Element imgSubject = element.getElementsByTag("img").first();


                                    if (imgSubject != null) {
                                        String imgUrl = "https:" + imgSubject.attr("data-cfsrc");
                                        imageUrls.add(imgUrl);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
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

    public void initRecylerView() {
        imageUrls = new ArrayList<>();
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(this, imageUrls);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            //  loading = false;
                            CURRENT_PAGE++;
                            loadMoreWallpaper();
                        }
                    }
                }
            }
        });
    }

}
