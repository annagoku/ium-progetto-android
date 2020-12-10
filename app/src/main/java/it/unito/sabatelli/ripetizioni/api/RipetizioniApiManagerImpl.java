package it.unito.sabatelli.ripetizioni.api;

import android.app.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unito.sabatelli.ripetizioni.AbstractActivity;
import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.httpclient.GsonRequest;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.CatalogItem;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.GenericResponse;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.SessionInfoResponse;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.User;

public class RipetizioniApiManagerImpl implements RipetizioniApiManager {
    private final AbstractActivity activity;
    private final HttpClientSingleton client;

    //costruttore
    public RipetizioniApiManagerImpl(AbstractActivity activity) {
        this.activity = activity;
        this.client = HttpClientSingleton.getInstance(activity.getApplicationContext());
    }

    private VolleyError checkServerError (VolleyError error) {
        String errorMessage = "Si è verificato un errore";
        if(error.networkResponse != null) {
            System.out.println("Response status "+error.networkResponse.statusCode);
            if(error.networkResponse.statusCode == 401 || error.networkResponse.statusCode == 403 || error.networkResponse.statusCode == 440) {
                System.out.println("Ricevuto status "+error.networkResponse.statusCode+" forzo il logout utente");
                client.invalidateSession();
                activity.forceClientLogout("Sessione scaduta o problemi con autorizzazione. Rieffettuare login");
                activity.finish();
                return error;
            }
            else {
                // controllo se è possibile che la risposta sia una generic response
                // in quel caso contiene il messaggio di errore da far comparire
                String contentType = error.networkResponse.headers.get("Content-Type");
                System.out.println("Response contentType "+contentType);

                if(contentType.startsWith("application/json")) {
                    try {
                        String json = new String(
                                error.networkResponse.data,
                                HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        GenericResponse gr = new Gson().fromJson(json, GenericResponse.class);
                        System.out.println("GenericResponse error -> "+errorMessage);
                        errorMessage = gr.getErrorOccurred();

                    }
                    catch (Exception e) {
                        System.out.println("L'errore server non è in formato GenericResponse");
                    }
                }
                return new VolleyError(errorMessage);
            }
        }
        else {
            error.printStackTrace();

            return new VolleyError(errorMessage);
        }
    }

    @Override
    public void login(String username, String password, SuccessListener<Void> listener, ErrorListener errorListener) {
        String path = activity.getString(R.string.main_server_url)+"/public/login";
        client.invalidateSession();

        GsonRequest<GenericResponse> loginRequest = new GsonRequest<GenericResponse>(Request.Method.POST, path, null, GenericResponse.class, null,
                new Response.Listener<GenericResponse>() {
                    @Override
                    public void onResponse(GenericResponse response) {
                        System.out.println("Risposta da login "+response);
                        activity.runOnUiThread(() -> { //assicura che qualsiasi cosa relativa all'interfaccia  giri sul thread dell'UI
                            listener.onSuccess(null);
                        });

                    }
                },
                (error) -> {

                    activity.runOnUiThread(() -> {
                        errorListener.onError(error);
                    });
                }) {
            @Override // metodo Volley per passare i parametri in post
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user", username);
                params.put("psw", password);

                return params;
            }
        };
        this.client.addToRequestQueue(loginRequest);
    }

    @Override
    public void logout(SuccessListener<Void> listener, ErrorListener errorListener) {
        GsonRequest<GenericResponse> request = new GsonRequest<GenericResponse>(Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/logout", null,
                GenericResponse.class,
                null, (response) -> {
            activity.runOnUiThread(() -> {
                listener.onSuccess(null);
            });
        },(error) -> {
            activity.runOnUiThread(() -> {
                errorListener.onError(checkServerError(error));
            });
        });
        this.client.addToRequestQueue(request);
    }

    @Override
    public void getUserInfo(SuccessListener<SessionInfoResponse> listener, ErrorListener errorListener) {

        GsonRequest<SessionInfoResponse> request = new GsonRequest<SessionInfoResponse>(Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/userlog",null,
                SessionInfoResponse.class,
                null, (response) -> {
            System.out.println("Ricevute info utente -> "+response);
            activity.runOnUiThread(() -> {
                listener.onSuccess(response);
            });
        },(error) -> {

            activity.runOnUiThread(() -> {
                errorListener.onError(checkServerError(error));
            });
        });
        this.client.addToRequestQueue(request);
    }

    @Override
    public void getReservations(SuccessListener<List<Lesson>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/lessons", "?list", null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                                ArrayList<Lesson> lessons = new ArrayList<>();
                                for(JsonElement el : array) {
                                    lessons.add(gson.fromJson(el, Lesson.class));

                                }
                                listener.onSuccess(lessons);
                            }
                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                errorListener.onError(checkServerError(error));
                            }
                        });

                    }
                });

        this.client.addToRequestQueue(request);
    }

    @Override
    public void getCatalog(SuccessListener<List<Lesson>> listener, ErrorListener errorListener) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/catalog", null,null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                                ArrayList<Lesson> lessons = new ArrayList<>();
                                for(JsonElement el : array) {
                                    lessons.add(gson.fromJson(el, Lesson.class));

                                }
                                listener.onSuccess(lessons);
                            }
                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                errorListener.onError(checkServerError(error));
                            }
                        });

                    }
                });

        this.client.addToRequestQueue(request);
    }

    @Override
    public void getCourses(SuccessListener<List<Course>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/public/courses", "?filter=home", null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        activity.runOnUiThread(() -> {
                            Gson gson = new Gson();
                            JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                            ArrayList<Course> arrayList = new ArrayList<>();

                            for(JsonElement el : array) {
                                arrayList.add(gson.fromJson(el, Course.class));

                            }
                            listener.onSuccess(arrayList);

                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activity.runOnUiThread(() -> {
                            errorListener.onError(checkServerError(error));

                        });

                    }
                });

        this.client.addToRequestQueue(request);
    }

    @Override
    public void getTeachers(SuccessListener<List<Teacher>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/public/teachers", "?filter=home", null,
                new Response.Listener<String> () {

                    @Override
                    public void onResponse(String response) {


                        activity.runOnUiThread(() -> {
                            Gson gson = new Gson();
                            JsonArray array = new JsonParser().parse(response).getAsJsonArray();

                            ArrayList<Teacher> arrayList = new ArrayList<>();

                            for(JsonElement el : array) {
                                arrayList.add(gson.fromJson(el, Teacher.class));

                            }
                            listener.onSuccess(arrayList);

                        });


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activity.runOnUiThread(() -> {
                            errorListener.onError(checkServerError(error));

                        });

                    }
                });

        this.client.addToRequestQueue(request);

    }

    @Override
    public void changeLessonState(Lesson lesson, String action, int newStateCode, SuccessListener<Void> listener, ErrorListener errorListener) {

        GsonRequest<GenericResponse> req = new GsonRequest<GenericResponse>(Request.Method.POST, activity.getString(R.string.main_server_url)+"/private/lessons", null, GenericResponse.class, null,
                new Response.Listener<GenericResponse>() {
                    @Override
                    public void onResponse(GenericResponse response) {
                        System.out.println("Response "+response);
                        activity.runOnUiThread(() -> {
                            listener.onSuccess(null);

                        });

                    }
                },
                (error) -> {
                    activity.runOnUiThread(() -> {
                        errorListener.onError(checkServerError(error));
                    });
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idLesson", ""+lesson.getId());
                params.put("stateLesson", ""+newStateCode);
                params.put("action", action);

                return params;
            }
        };
        this.client.addToRequestQueue(req);
    }

    @Override
    public void saveNewReservation(Lesson lesson, SuccessListener<User> listener, ErrorListener errorListener) {


        GsonRequest<GenericResponse> req = new GsonRequest<GenericResponse>(Request.Method.POST, activity.getString(R.string.main_server_url)+"/private/newreservation", null, GenericResponse.class, null,
                new Response.Listener<GenericResponse>() {
                    @Override
                    public void onResponse(GenericResponse response) {
                        System.out.println("Response "+response);
                        activity.runOnUiThread(() -> {
                            listener.onSuccess(null);

                        });

                    }
                },
                (error) -> {
                    activity.runOnUiThread(() -> {
                        errorListener.onError(checkServerError(error));
                    });
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("infoCatalogItemSelected", new Gson().toJson(new CatalogItem(lesson)));
                params.put("checkFirst", "true");
                return params;
            }
        };
        this.client.addToRequestQueue(req);

    }

    public Activity getActivity() {
        return activity;
    }
}
