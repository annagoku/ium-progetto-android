package it.unito.sabatelli.ripetizioni;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import it.unito.sabatelli.ripetizioni.httpclient.GsonRequest;
import it.unito.sabatelli.ripetizioni.httpclient.HttpClientSingleton;
import it.unito.sabatelli.ripetizioni.model.User;

public class LoginActivity extends AppCompatActivity {
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    /** Called when the user taps the Login button */
    public void login(View view) {
        System.out.println("----------LOGIN ");

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

        //todo effettuare login vero

        String path = getString(R.string.main_server_url)+"/public/login";
        System.out.println("calling "+path);

        StringRequest loginRequest = new StringRequest(Request.Method.POST, path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Risposta da login "+response);
                        getUserAndStartActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);

                        errorView.setText("Credenziali di accesso errate");
                        errorView.setVisibility(View.VISIBLE);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        progress.setVisibility(View.VISIBLE);
        HttpClientSingleton.getInstance(this.getApplicationContext()).addToRequestQueue(loginRequest);

        /*User user = new User(username, "Nome", "Cognome");
        user.setRole("student");
        intent.putExtra("USER", user);

        startActivity(intent);*/

    }

    private void getUserAndStartActivity(Intent intent) {
        System.out.println("chiamato getUserAndStartActivity");
        ProgressBar progress = findViewById(R.id.progressBar);

        TextView errorView = findViewById(R.id.textErrorMessage);
        errorView.setText(null);
        errorView.setVisibility(View.INVISIBLE);
        GsonRequest<User> request = new GsonRequest<User>(Request.Method.GET,
                getString(R.string.main_server_url)+"/private/sessionInfo",
                User.class,
                null, new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        System.out.println("Ricevute info utente -> "+response);
                        intent.putExtra("USER", response);
                        progress.setVisibility(View.GONE);

                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        errorView.setText("Errore di accesso");
                        errorView.setVisibility(View.VISIBLE);
                    }
                });
        HttpClientSingleton.getInstance(this.getApplicationContext()).addToRequestQueue(request);

    }

    private int validate(String user, String pwd) {
        if(user == null || "".equals(user) || pwd == null || "".equals(pwd))
            return R.string.msg_login_missing_inpus;
        else
            return -1;
    }




}