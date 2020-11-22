package it.unito.sabatelli.ripetizioni;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import it.unito.sabatelli.ripetizioni.model.User;
import it.unito.sabatelli.ripetizioni.ui.MainViewModel;
import it.unito.sabatelli.ripetizioni.ui.fragments.CatalogFragment;
import it.unito.sabatelli.ripetizioni.ui.fragments.CoursesFragment;
import it.unito.sabatelli.ripetizioni.ui.fragments.LessonsFragment;
import it.unito.sabatelli.ripetizioni.ui.fragments.TeacherCourseFragment;
import it.unito.sabatelli.ripetizioni.ui.fragments.TeacherFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MainViewModel vModel;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        vModel = ViewModelProviders.of(this).get(MainViewModel.class);
        User user = (User) intent.getSerializableExtra("USER");
        vModel.user = user;

        initializeViews();

        initializeDefaultFragment(savedInstanceState, 0);


    }

    /**
     * Initialize all widgets
     */
    private void initializeViews() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView) header.findViewById(R.id.userText)).setText(vModel.user.getName()+" "+vModel.user.getSurname());
        ((TextView) header.findViewById(R.id.usernameRoleText)).setText(vModel.user.getUsername()+" ("+vModel.user.getRole()+")");

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_app_bar_open_drawer_description, R.string.nav_app_bar_navigate_up_description);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     * Checks if the savedInstanceState is null - onCreate() is ran
     * If so, display fragment of navigation drawer menu at position itemIndex and
     * set checked status as true
     * @param savedInstanceState
     * @param itemIndex
     */
    private void initializeDefaultFragment(Bundle savedInstanceState, int itemIndex){
        if (savedInstanceState == null){
            MenuItem menuItem = navigationView.getMenu().getItem(itemIndex);
            onNavigationItemSelected(menuItem);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Iterates through all the items in the navigation menu and deselects them:
     * removes the selection color
     */
    private void deSelectCheckedState(){
        int noOfItems = navigationView.getMenu().size();
        for (int i=0; i<noOfItems;i++){
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.dr_action_lessons:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new LessonsFragment())
                        .commit();
                break;
            case R.id.dr_action_logout:
                logout(null);
                break;
            case R.id.dr_action_catalog:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new CatalogFragment())
                        .commit();
                break;
            case R.id.dr_action_courses:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new CoursesFragment())
                        .commit();
                break;
            case R.id.dr_action_teachers:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new TeacherFragment())
                        .commit();
                //Toast.makeText(this, "Insegnanti non ancora implementato", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dr_action_courses_teacher:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_id, new TeacherCourseFragment())
                        .commit();
                //Toast.makeText(this, " Associazione Insegnanti e corsi non ancora implementato", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "AZIONE NON DISPONIBILE", Toast.LENGTH_SHORT).show();
                break;

        }
        closeDrawer();

        return true;
    }

    /**
     * Checks if the navigation drawer is open - if so, close it
     */
    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    // ACTIONS
    public void logout(String message) {
        Intent intent = new Intent(this, LoginActivity.class);
        if(message != null)
            intent.putExtra("message", message);
        vModel.user = null;
        startActivity(intent);
        finish();
    }




}