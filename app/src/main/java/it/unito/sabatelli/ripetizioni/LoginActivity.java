package it.unito.sabatelli.ripetizioni;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

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

        //todo effettuare login vero
        User user = new User(username, "Nome", "Cognome");
        user.setRole("student");
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