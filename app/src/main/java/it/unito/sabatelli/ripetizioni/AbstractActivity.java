package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.VolleyError;

import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;

public abstract class AbstractActivity extends AppCompatActivity  {
    protected MainViewModel vModel;
    protected RipetizioniApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiFactory.getRipetizioniApiManager(this);
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);

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
        Intent intent = new Intent(this, LoginActivity.class);
        if(message != null)
            intent.putExtra("message", message);
        vModel.user = null;
        startActivity(intent);
        finish();
    }

}
