package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import it.unito.sabatelli.ripetizioni.api.ApiFactory;
import it.unito.sabatelli.ripetizioni.api.RipetizioniApiManager;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;

public abstract class AbstractActivity extends AppCompatActivity  {
    protected RipetizioniApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager = ApiFactory.getRipetizioniApiManager(this);
    }

}
