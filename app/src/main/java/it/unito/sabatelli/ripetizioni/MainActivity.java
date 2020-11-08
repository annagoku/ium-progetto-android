package it.unito.sabatelli.ripetizioni;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import it.unito.sabatelli.ripetizioni.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /** Called when the user taps the Login button */
    public void login(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
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

        //todo effettuare login vero
        User user = new User(username, "Nome", "Cognome");
        intent.putExtra("USER", user);

        startActivity(intent);

    }

    private int validate(String user, String pwd) {
        if(user == null || "".equals(user) || pwd == null || "".equals(pwd))
            return R.string.msg_login_missing_inpus;
        else
            return -1;
    }




}