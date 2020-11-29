package it.unito.sabatelli.ripetizioni.api;

import android.app.Activity;
import android.content.Context;

import it.unito.sabatelli.ripetizioni.AbstractActivity;

public class ApiFactory {

    public static RipetizioniApiManager getRipetizioniApiManager(AbstractActivity act) {
        return new RipetizioniApiManagerImpl(act);
    }
}
