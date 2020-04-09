package com.OnlineEvent.umangburman.event;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity

        //  implements ActivityDrawerHandler, NavigationView.OnNavigationItemSelectedListener {
{
    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setupNavigation();

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener
                (this, instanceIdResult -> {
                    String mToken = instanceIdResult.getToken();
                    Log.i("hhhhh", "token" + mToken);
                });


    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        // toolbar = findViewById(R.id.toolbar);
        //drawerLayout = findViewById(R.id.drawer_layout);

     /*   setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);          // to hide Navigation icon
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));*/


        //  navigationView = findViewById(R.id.navigationView);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        // NavigationUI.setupWithNavController(navigationView, navController);
        // navigationView.setNavigationItemSelectedListener(this);

    }

   /* @Override
    public boolean onSupportNavigateUp() {
       // return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(this, R.id.nav_host_fragment));
    }*/

    @Override
    public void onBackPressed() {
       /* if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {*/
        super.onBackPressed();
    }


   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

       *//* menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.first:
                navController.navigate(R.id.HomeFragment);
                break;

            case R.id.second:
                navController.navigate(R.id.secondFragment);
                break;

            case R.id.third:
                navController.navigate(R.id.thirdFragment);
                break;
            case R.id.logout:
                this.finish();

                break;

        }

        *//*
        return true;

    }*/


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

   /* @Override
    public void setDrawerLocked(Boolean shouldLock) {
        if (shouldLock) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }*/

   /* @Override
    public void hideToolbar(Boolean shouldLock) {
        if (shouldLock) {
            if (toolbar == null) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().hide();
            }
        } else {
            getSupportActionBar().show();

        }
    }
*/
    /*@Override
    public void showDrwer(Boolean show) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }*/

    /*@Override
    public void hideItem(String menuId) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.second).setVisible(false);

    }

    @Override
    public void showItem(String menuId) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.second).setVisible(true);

    }*/
}
