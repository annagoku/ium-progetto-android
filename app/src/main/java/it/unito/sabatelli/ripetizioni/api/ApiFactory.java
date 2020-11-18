package it.unito.sabatelli.ripetizioni.api;

import android.app.Activity;
import android.content.Context;

public class ApiFactory {

    public static RipetizioniApiManager getRipetizioniApiManager(Activity act) {
        return new RipetizioniApiManagerImpl(act);
    }
}
