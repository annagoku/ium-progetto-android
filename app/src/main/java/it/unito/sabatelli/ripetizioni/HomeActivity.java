package it.unito.sabatelli.ripetizioni;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import it.unito.sabatelli.ripetizioni.model.User;

public class HomeActivity extends AppCompatActivity {
    User user =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");

        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem userMenu = menu.findItem(R.id.userMenuItem);
        System.out.println(userMenu);
        userMenu.setTitle("Utente: "+user.getUsername());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
        }
        switch (id) {
            case R.id.action_logout:
                logout(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }



    // ACTIONS
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        user = null;
        startActivity(intent);
    }

}