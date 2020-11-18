package it.unito.sabatelli.ripetizioni.api;

import android.app.Activity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unito.sabatelli.ripetizioni.R;
import it.unito.sabatelli.ripetizioni.Utility;
import it.unito.sabatelli.ripetizioni.httpclient.GsonRequest;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.httpclient.StringRequest;
import it.unito.sabatelli.ripetizioni.model.Course;
import it.unito.sabatelli.ripetizioni.model.GenericResponse;
import it.unito.sabatelli.ripetizioni.model.Lesson;
import it.unito.sabatelli.ripetizioni.model.Teacher;
import it.unito.sabatelli.ripetizioni.model.User;

public class RipetizioniApiManagerImpl implements RipetizioniApiManager {

    private final Activity activity;

    public RipetizioniApiManagerImpl(Activity activity) {
        this.activity = activity;
        HttpClientSingleton.getInstance(activity.getApplicationContext());
    }


    @Override
    public void login(String username, String password, SuccessListener<Void> listener, ErrorListener errorListener) {
        String path = activity.getString(R.string.main_server_url)+"/public/login";

        GsonRequest loginRequest = new GsonRequest(Request.Method.POST, path, GenericResponse.class, null,
                new Response.Listener<GenericResponse>() {
                    @Override
                    public void onResponse(GenericResponse response) {
                        System.out.println("Risposta da login "+response);
                        activity.runOnUiThread(() -> {
                            listener.onSuccess(null);
                        });

                    }
                },
                (error) -> {
                    activity.runOnUiThread(() -> {
                        errorListener.onError(error);
                    });
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user", username);
                params.put("psw", password);

                return params;
            }
        };
        HttpClientSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(loginRequest);
    }

    @Override
    public void logout(SuccessListener<Void> listener, ErrorListener errorListener) {

    }

    @Override
    public void getUserInfo(SuccessListener<User> listener, ErrorListener errorListener) {

        GsonRequest<User> request = new GsonRequest<User>(Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/userlog",
                User.class,
                null, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                System.out.println("Ricevute info utente -> "+response);
                activity.runOnUiThread(() -> {
                    listener.onSuccess(response);
                });

            }
        },(error) -> {
            activity.runOnUiThread(() -> {
                errorListener.onError(error);
            });
        });
        HttpClientSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    public void getReservations(SuccessListener<List<Lesson>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/lessons?list", null,
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
                                errorListener.onError(error);
                            }
                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);
    }

    @Override
    public void getCatalog(SuccessListener<List<Lesson>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/private/catalog", null,
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
                                errorListener.onError(error);
                            }
                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);
    }

    @Override
    public void getCourses(SuccessListener<List<Course>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/public/courses?filter=home", null,
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
                            errorListener.onError(error);

                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);
    }

    @Override
    public void getTeachers(SuccessListener<List<Teacher>> listener, ErrorListener errorListener) {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                activity.getString(R.string.main_server_url)+"/public/teachers?filter=home", null,
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
                            errorListener.onError(error);

                        });

                    }
                });

        HttpClientSingleton.getInstance().addToRequestQueue(request);

    }

    @Override
    public void changeLessonState(Lesson lesson, int newStateCode, SuccessListener<Void> listener, ErrorListener errorListener) {
        //TODO controllare path
        GsonRequest req = new GsonRequest(Request.Method.POST, activity.getString(R.string.main_server_url)+"/private/changelessonstate", GenericResponse.class, null,
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
                        errorListener.onError(error);
                    });
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lessonId", ""+lesson.getId());
                params.put("state", ""+newStateCode);

                return params;
            }
        };
        HttpClientSingleton.getInstance(activity.getApplicationContext()).addToRequestQueue(req);
    }

    public Activity getActivity() {
        return activity;
    }
}
