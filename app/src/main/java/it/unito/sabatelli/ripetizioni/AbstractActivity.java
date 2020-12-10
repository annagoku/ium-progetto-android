package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.VolleyError;

import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
//attività astratta
public abstract class AbstractActivity extends AppCompatActivity  {
    protected MainViewModel vModel;
    protected RipetizioniApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiFactory.getRipetizioniApiManager(this); // Api manager per la gestione delle chiamate al server
        vModel = ViewModelProviders.of(this).get(MainViewModel.class); // per la visualizzazione dei dati su UI

    }

    public RipetizioniApiManager getApiManager() {
        return this.apiManager;
    }

    public MainViewModel getMainViewModel () {
        return this.vModel;
    }

    /**
     * Da chiamare nel caso in cui ci si accorge che la sessione server è invalida oppure
     * il server non riconosce più l'utente loggato
     */
    public void forceClientLogout(String message) {
        System.out.println("Force client logout -> message : "+message);
        Intent intent = new Intent(this, LoginActivity.class);
        if(message != null)
            intent.putExtra("message", message);
        vModel.user = null; // elimino dal view model l'utente loggato
        startActivity(intent);
        finish();
    }


    //nasconde la tastiera con un touch su un punto qualsiasi dello schermo
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}
