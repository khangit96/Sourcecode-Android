package khangit96.demomakejsonobjectrequestvolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 6/28/2016.
 */
public class Mysingleton {
    public static Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public Mysingleton(Context mcontext) {
        context = mcontext;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized Mysingleton getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Mysingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}


