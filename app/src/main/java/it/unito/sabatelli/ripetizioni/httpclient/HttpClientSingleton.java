package it.unito.sabatelli.ripetizioni.httpclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;
//Presa da guida di Android
// classe di supporto per Volley e per il controllo di sessione
public class HttpClientSingleton {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "JSESSIONID";
    public static final int SOCKET_TIMEOUT_DURATION = 30000;

    private static HttpClientSingleton _instance;

    private RequestQueue requestQueue; //Gestione coda richieste asincrone verso il server
    private static Context ctx;
    private SharedPreferences _preferences;

    private HttpClientSingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();


    }
    //Costruttore wrappato per assicurare che venga creata un'unica istanza in un programma in esecuzione
    public static synchronized HttpClientSingleton getInstance(Context context) {
        if (_instance == null) {
            _instance = new HttpClientSingleton(context);
        }
        return _instance;
    }
    //Get da utilizzare solo qunado il context non è accessibile ed è noto che il singleton è già istanziato
    public static  HttpClientSingleton getInstance() {
        if (_instance == null) {
            throw new RuntimeException("Singleton must be initialized");
        }
        return _instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
            _preferences  = PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext()); //classe che permette di tener traccia dei cookies
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        System.out.println("Calling "+ req.getMethod() + " -> "+req.getUrl());
        getRequestQueue().add(req);
    }

    public void invalidateSession() {

        SharedPreferences.Editor prefEditor = _preferences.edit();
        prefEditor.remove(SESSION_COOKIE);
        prefEditor.commit();
    }

    public String encodeUrl(String path, String queryString) {
        StringBuilder b = new StringBuilder();
        b.append(path);
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            b.append(";jsessionid=");
            b.append(sessionId);
        }
        if(queryString != null) {
            b.append(queryString);
        }
        return b.toString();
    }

    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     * @param headers Response Headers.
     */
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            System.out.println(SET_COOKIE_KEY+" -> "+cookie);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                if(splitSessionId.length <2) {
                    this.invalidateSession();
                }
                else {
                    cookie = splitSessionId[1];
                    SharedPreferences.Editor prefEditor = _preferences.edit();
                    prefEditor.putString(SESSION_COOKIE, cookie);
                    prefEditor.commit();
                }

            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
}
