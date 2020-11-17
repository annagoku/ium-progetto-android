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

public class StringRequest extends Request<String> {
    private final Map<String, String> headers;
    private final Response.Listener<String> listener;


    public StringRequest(int method, String url,  Map<String, String> headers,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
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
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
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
