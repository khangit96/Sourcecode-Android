package khangit96.demorequestimagevolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 6/27/2016.
 */
public class MySingleton {
    public static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public MySingleton(Context mcontext) {
        context=mcontext;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
           requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        return  requestQueue;
    }
    public static  synchronized MySingleton getmInstance(Context context){
        if(mInstance==null){
            mInstance=new MySingleton(context);
        }
        return mInstance;
    }
    public <T> void addToRequestQueue(Request<T> request){
        requestQueue.add(request);
    }
}
