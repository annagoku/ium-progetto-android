package it.unito.sabatelli.ripetizioni.httpclient;

import com.android.volley.AuthFailureError;
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

import it.unito.sabatelli.ripetizioni.LoginActivity;

public class GsonRequest <T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;


    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        Map<String, String> _headers = headers != null ? headers : super.getHeaders();

        if (_headers == null
                || _headers.equals(Collections.emptyMap())) {
            _headers = new HashMap<>();
        }

        HttpClientSingleton.getInstance().addSessionCookie(_headers);

        return _headers;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            HttpClientSingleton.getInstance().checkSessionCookie(response.headers);

            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            System.out.println("Response status -> "+response.statusCode);
            System.out.println("GsonRequest -> data -> "+json);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}