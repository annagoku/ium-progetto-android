package it.unito.sabatelli.ripetizioni;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import it.unito.sabatelli.ripetizioni.httpclient.GsonRequest;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.model.GenericResponse;
import it.unito.sabatelli.ripetizioni.model.User;
import it.unito.sabatelli.ripetizioni.model.UserSession;

public class LoginActivity extends AbstractActivity {
    User user = null;
    String message = null;

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //esplicita il layout di riferimento
        setContentView(R.layout.activity_login);
        try {
            this.message = (String ) getIntent().getSerializableExtra("message");
            if(this.message != null) {
                Toast.makeText(this, this.message, Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }




    /** Called when the user taps the Login button */
    public void login(View view) {
        System.out.println("----------LOGIN ");
        //Istanzia l'intent che servirà a trasmettere le info all'attività indicata
        Intent intent = new Intent(this, MainActivity.class);
        TextView errorView = findViewById(R.id.textErrorMessage);
        errorView.setText(null);
        errorView.setVisibility(View.INVISIBLE);
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        int idMessage = validate(username, password);

        if(idMessage > 0) {
            //allore c'è un errore
            errorView.setText(idMessage);
            errorView.setVisibility(View.VISIBLE);
            return;
        }
        ProgressBar progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.VISIBLE);
        LoginActivity act = this;

        apiManager.login(username, password, (v) -> {
            System.out.println("Effettuato login");
            //Passaggio intent e start MainActivity
            act.getUserAndStartActivity(intent);
        }, (error) -> {
            System.out.println("Errore al login -> "+error.getMessage());
            String message = "Si è verificato un errore";
            if(error.networkResponse != null && error.networkResponse.statusCode == 401) {
                message = "Credenziali di accesso errate";
            }
            progress.setVisibility(View.GONE);

            errorView.setText(message);
            errorView.setVisibility(View.VISIBLE);

        });

    }


    private void getUserAndStartActivity(Intent intent) {
        System.out.println("chiamato getUserAndStartActivity");
        ProgressBar progress = findViewById(R.id.progressBar);

        TextView errorView = findViewById(R.id.textErrorMessage);
        errorView.setText(null);
        errorView.setVisibility(View.INVISIBLE);
        Activity act = this;

        apiManager.getUserInfo((info) -> {
            if(info.getUser() == null) {
                errorView.setText("Errore nel reperire le informazioni utente");
                errorView.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);

            }
            else {
                //inserire le info nell'intent
                intent.putExtra("USER", info.getUser());
                intent.putExtra("SESSIONID", info.getSessionId());
                progress.setVisibility(View.GONE);
                act.startActivity(intent);
                //act.finish();
            }
        }, (error) -> {

            progress.setVisibility(View.GONE);
            errorView.setText("Errore di accesso");
            errorView.setVisibility(View.VISIBLE);
        });


    }

    private int validate(String user, String pwd) {
        if(user == null || "".equals(user) || pwd == null || "".equals(pwd))
            return R.string.msg_login_missing_inpus;
        else
            return -1;
    }

}