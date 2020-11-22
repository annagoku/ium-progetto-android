package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public static void hideKeyboard( Context context ) {

        try {
            InputMethodManager inputManager = ( InputMethodManager ) context.getSystemService( Context.INPUT_METHOD_SERVICE );

            View view = ( (Activity) context ).getCurrentFocus();
            if ( view != null ) {
                inputManager.hideSoftInputFromWindow( view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS );
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }






    public static void showKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void addKeyboardVisibilityListener(View rootLayout, OnKeyboardVisibiltyListener onKeyboardVisibiltyListener) {
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootLayout.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootLayout.getRootView().getHeight();

            // r.bottom is the position above soft keypad or device button.
            // if keypad is shown, the r.bottom is smaller than that before.
            int keypadHeight = screenHeight - r.bottom;

            boolean isVisible = keypadHeight > screenHeight * 0.15; // 0.15 ratio is perhaps enough to determine keypad height.
            onKeyboardVisibiltyListener.onVisibilityChange(isVisible);
        });
    }

    public interface OnKeyboardVisibiltyListener {
        void onVisibilityChange(boolean isVisible);
    }



}



