package com.softhub.flowermart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerview;
    private RecyclerView.Adapter adapter;


    private RecyclerView recyclerViewSubCategory;
    private RecyclerView.Adapter adapterSubCategory;


    private RecyclerView recyclerViewCategory;
    private RecyclerView.Adapter adapterCategory;

    private RecyclerView recyclerViewBanner;
    private RecyclerView.Adapter adapterBanner;

    private ImageView cart;
    private ImageView search;
    public TextView notification;
    public TextView viewAll;

    TextView add_flower,vendor,purchase_list,sales_list,driver_list;
    LinearLayout sales;
    LinearLayout flower_list;
    LinearLayout purchase;
    TextView add_driver;

    private com.softhub.flowermart.Session session;
    private TextView userMobileNumber;
    private TextView userName;
    private DrawerLayout drawer;
    String id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        //cart = findViewById(R.id.cart);
        //notification = findViewById(R.id.notification);
        //search = findViewById(R.id.search);
        purchase = findViewById(R.id.purchase);
         session = new Session(getApplicationContext());
        sales = findViewById(R.id.sales);
         flower_list = findViewById(R.id.flower_list);
         purchase_list = findViewById(R.id.purchase_list);
         sales_list = findViewById(R.id.sales_list);
         add_driver = findViewById(R.id.add_driver);
         driver_list = findViewById(R.id.driver_list);

         add_flower = findViewById(R.id.add_flower);
         //vendor = findViewById(R.id.vendor);

        drawer = findViewById(R.id.drawer_layout);
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

        userMobileNumber = (TextView) nView.findViewById(R.id.userMobileNumber);
        userName = (TextView) nView.findViewById(R.id.userName);


        if (session.loggedin()) {

            logout.setVisible(true);
            account.setVisible(true);
            orders.setVisible(true);
            login.setVisible(false);

            userMobileNumber.setText(session.prefs.getString("Mbl", null));
            userName.setText(session.prefs.getString("Unm", null));

        }

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AddSaleActivity.class);
                startActivity(intent);
            }
        });

        add_flower.setOnClickListener(v -> {
            Intent i = new Intent(Home.this,AddFlowerActivity.class);
            startActivity(i);
        });

        /*layout.setOnClickListener(v -> {
            Intent i = new Intent(Home.this,AddingVendorActivity.class);
            startActivity(i);
        });*/

       flower_list.setOnClickListener(v -> {
       Intent in = new Intent(Home.this,FlowerListActivity.class);
       startActivity(in);
       });

       purchase_list.setOnClickListener(v -> {

           Intent intent1 = new Intent(Home.this,VendorListActivity.class);
           startActivity(intent1);
       });

        purchase.setOnClickListener(v -> {
            Intent intent2 = new Intent(Home.this,AddPurchaseActivity.class);
            intent.putExtra("id",id);
            startActivity(intent2);
        });

        sales_list.setOnClickListener(v -> {

            Intent intent3 = new Intent(Home.this,SalesListActivity.class);
            startActivity(intent3);
        });

        add_driver.setOnClickListener(v -> {

            Intent inte = new Intent(Home.this,CreateAccountActivity.class);
            startActivity(inte);
        });

        driver_list.setOnClickListener(v -> {

            Intent intent5 = new Intent(Home.this,DriverListActivity.class);
            startActivity(intent5);


        });

    }

    /* public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.search_view,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });

        return true;

    }*/

    /*private void filter(String newText) {

        ArrayList<FetchedListOfItem>listOfItems =new ArrayList<>();

        for (FetchedListOfItem item: listItemsSubCategory){

            if (item.getItemName().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                listOfItems.add(item);
            }
        }

        if (listOfItems.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
        }
    }*/
    private void scrollItems() {

        final int speedScroll = 4000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;

            @Override
            public void run() {
                if (count < adapterBanner.getItemCount()) {
                    if (count == adapterBanner.getItemCount() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    recyclerViewBanner.smoothScrollToPosition(count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };

        handler.postDelayed(runnable, speedScroll);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
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
        else if (id == R.id.payment) {
           /* Intent i = new Intent(getApplicationContext(), PaymentMode.class);
            startActivity(i);*/
        }
        else if (id == R.id.account) {
           /* Intent i = new Intent(getApplicationContext(), Account.class);
            startActivity(i);*/
        } else if (id == R.id.orders) {
           /* Intent i = new Intent(getApplicationContext(), OrderHistory.class);
            startActivity(i);*/
        } else if (id == R.id.login) {
            /*Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);*/
        } else if (id == R.id.logout) {
            logOut();
        } else if (id == R.id.aboutus) {
           /* Intent i = new Intent(getApplicationContext(), AboutUs.class);
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
                    session.setUserName("");
                    /*session.setEmailId("");
                    session.setUserRole("");*/
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
