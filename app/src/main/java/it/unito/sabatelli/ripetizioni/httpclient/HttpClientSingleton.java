package it.unito.sabatelli.ripetizioni.httpclient;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HttpClientSingleton {
    private static HttpClientSingleton _instance;

    private RequestQueue requestQueue;
    private static Context ctx;


    private HttpClientSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }

    public static synchronized HttpClientSingleton getInstance(Context context) {
        if (_instance == null) {
            _instance = new HttpClientSingleton(context);
        }
        return _instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
