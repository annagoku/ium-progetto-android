package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.View;

import com.android.volley.VolleyError;

public abstract class Utility {

    public static boolean isSessionExpired(VolleyError error, View view) {
        int status = error.networkResponse.statusCode;

        Activity act = getActivity(view.getContext());

        if(status == 401) {
            System.err.println("Needs to authenticate. Session could be expired");
            Intent intent = new Intent(act, LoginActivity.class);
            act.startActivity(intent);
            return true;
        }
        return false;
    }

    public static final Activity getActivity(Context context) {
        while (!(context instanceof Activity)) {
            if (!(context instanceof ContextWrapper)) {
                context = null;
            }
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper == null) {
                return null;
            }
            context = contextWrapper.getBaseContext();
            if (context == null) {
                return null;
            }
        }
        return (Activity) context;
    }
}
