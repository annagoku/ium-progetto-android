package it.unito.sabatelli.ripetizioni.httpclient;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
//da manuale di Android per gestire gli oggetti restituiti da Http request
public class StringRequest extends Request<String> {
    private final Map<String, String> headers;
    private final Response.Listener<String> listener;
    private final String path;
    private final String queryString;


    public StringRequest(int method, String path, String queryString,  Map<String, String> headers,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, path, errorListener);
        this.path = path;
        this.queryString = queryString;
        this.headers = headers;
        this.listener = listener;
        setRetryPolicy(new DefaultRetryPolicy(HttpClientSingleton.SOCKET_TIMEOUT_DURATION, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    @Override
    public String getUrl() {
        return HttpClientSingleton.getInstance().encodeUrl(path, queryString);
    }

    //Gestione Header
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> _headers = headers != null ? headers : super.getHeaders();

        if (_headers == null
                || _headers.equals(Collections.emptyMap())) {
            _headers = new HashMap<>();
        }

        HttpClientSingleton.getInstance().addSessionCookie(_headers);
        _headers.put("Accept", "application/json");

        return _headers;
    }

    //metodo per restituire la risposta
    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) { //response in byte
        try {

            HttpClientSingleton.getInstance().checkSessionCookie(response.headers);

            String data = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            System.out.println("Response status -> "+response.statusCode);
            System.out.println("StringRequest -> data -> "+data);
            return Response.success(
                    data,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
