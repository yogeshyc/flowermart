package com.softhub.flowermart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private ProgressDialog progressDialog;

    private Session session;
    private DrawerLayout drawer;
    private TextView userMobileNumber;
    private TextView userName;
    private String id;
    private String user_id;
    LinearLayout add_flower_emp,flower_list_emp,purchase_list_emp,purchase_emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new Session(getApplicationContext());

        add_flower_emp = findViewById(R.id.add_flower_emp);
        flower_list_emp = findViewById(R.id.flower_list_emp);
        purchase_list_emp = findViewById(R.id.purchase_list_emp);
        purchase_emp = findViewById(R.id.purchase_emp);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        View nView = navigationView.inflateHeaderView(R.layout.nav_header_home);
        Menu menuNave = navigationView.getMenu();
        MenuItem logout = menuNave.findItem(R.id.logout);
        MenuItem login = menuNave.findItem(R.id.login);
        MenuItem account = menuNave.findItem(R.id.account);
        MenuItem orders = menuNave.findItem(R.id.orders);



       /* Intent intent = getIntent();
        id = intent.getStringExtra("id");*/

       // user_id = session.prefs.getString("user_id",null);



        userMobileNumber = (TextView) nView.findViewById(R.id.userMobileNumber);
        userName = (TextView) nView.findViewById(R.id.userName);

        if (session.loggedin()) {

            logout.setVisible(true);
            login.setVisible(false);

            userMobileNumber.setText(session.prefs.getString("Mbl", null));
            userName.setText(session.prefs.getString("Unm", null));

        }

       /* progressDialog = new ProgressDialog(EmployeePage.this);
        progressDialog.setMessage("Loading data...");*/
        //progressDialog.show();
        new A().execute();

        add_flower_emp.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeePage.this,AddFlowerActivity.class);
            startActivity(intent);
        });

        purchase_emp.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeePage.this,AddPurchaseActivity.class);
            startActivity(intent);
        });

        flower_list_emp.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeePage.this,FlowerListActivity.class);
            startActivity(intent);
        });
        purchase_list_emp.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeePage.this,VendorListActivity.class);
            startActivity(intent);
        });

    }


    class A extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            drawer.closeDrawer(Gravity.LEFT);
        }
//        else if (id == R.id.fruit) {
//            Intent i = new Intent(getApplicationContext(), Fruits.class);
//            startActivity(i);
//        } else if (id == R.id.vegetables) {
//            Intent i = new Intent(getApplicationContext(), Vegetables.class);
//            startActivity(i);
//        } else if (id == R.id.dryfruit) {
//            Intent i = new Intent(getApplicationContext(), DryFruits.class);
//            startActivity(i);
//        } else if (id == R.id.cutsandsprouts) {
//            Intent i = new Intent(getApplicationContext(), CutsandSprouts.class);
//            startActivity(i);
//        }
        else if (id == R.id.account) {
           /* Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);*/
        } else if (id == R.id.orders) {
           /* Intent i = new Intent(getApplicationContext(), OrderHistory.class);
            startActivity(i);*/
        } else if (id == R.id.login) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            logOut();
        } else if (id == R.id.aboutus) {
          /*  Intent i = new Intent(getApplicationContext(), AboutUs.class);
            startActivity(i);*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }



    public void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                if (session.loggedin()) {
                    session.setMobile("");
                    session.setUserRole("");
                    session.setLoggedin(false);
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "You are not logged in!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}