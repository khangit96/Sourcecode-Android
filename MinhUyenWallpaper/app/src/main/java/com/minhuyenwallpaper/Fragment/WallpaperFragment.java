package com.minhuyenwallpaper.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.minhuyenwallpaper.Adapter.GridAdapter;
import com.minhuyenwallpaper.Model.Wallpaper;
import com.minhuyenwallpaper.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WallpaperFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ArrayList<Wallpaper> wallpapers;
    RecyclerView mRecyclerView;
    GridLayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    RequestQueue queue;
    static int CURRENT_PAGE = 1;
    static String URL = "https://wallpaperscraft.com/catalog/anime/540x960";
    View rootView;
    ProgressDialog pg;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public WallpaperFragment() {
    }

    public void loadMoreWallpaper() {
        String urlNew = URL;
        if (CURRENT_PAGE > 1) {
            urlNew += "/page" + CURRENT_PAGE;
            rootView.findViewById(R.id.pgloading).setVisibility(View.VISIBLE);
        }
        if (CURRENT_PAGE == 1) {
            pg = new ProgressDialog(getActivity());
            pg.setMessage("Loading data ...");
            pg.setCanceledOnTouchOutside(false);
            pg.show();

        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlNew,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (CURRENT_PAGE == 1) {
                            pg.dismiss();
                        }
                        rootView.findViewById(R.id.pgloading).setVisibility(View.GONE);
                        Document document = null;
                        document = (Document) Jsoup.parse(response);
                        if (document != null) {
                            Elements subjectElements = document.select("div.wallpaper_pre");

                            if (subjectElements != null && subjectElements.size() > 0) {
                                for (Element element : subjectElements) {
                                    Element imgSubject = element.getElementsByTag("img").first();


                                    if (imgSubject != null) {
                                        String imgUrl = "https:" + imgSubject.attr("data-cfsrc");
                                        wallpapers.add(new Wallpaper("", imgUrl));
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
        wallpapers = new ArrayList<>();
        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(getActivity(), wallpapers);
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

    /***
     *
     *
     * */
    public static WallpaperFragment newInstance(String param1, String param2) {
        WallpaperFragment fragment = new WallpaperFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        initRecylerView();

        queue = Volley.newRequestQueue(getActivity());

        loadMoreWallpaper();

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
