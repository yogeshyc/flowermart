package com.softhub.flowermart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
import java.util.List;

public class DriverListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfDriver> listOfDrivers;
    private List<FetchedListOfDriver> filteredListDrivers;
    EditText driver_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        driver_search = findViewById(R.id.driver_search);
        recyclerView = (RecyclerView) findViewById(R.id.show_drivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(DriverListActivity.this));
        listOfDrivers = new ArrayList<>();
        filteredListDrivers = new ArrayList<>();
        adapter = new DriverListAdapter(listOfDrivers,getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData();

        driver_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filterItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterItem(String s) {

        filteredListDrivers.clear();
        for (FetchedListOfDriver flp : listOfDrivers) {
            if(flp.getDriverName().toLowerCase().contains(s.toLowerCase())){
                filteredListDrivers.add(flp);
            }
        }

        if(s!=null && s!=""){
            adapter = new DriverListAdapter(filteredListDrivers,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            adapter = new DriverListAdapter(listOfDrivers,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }

    public void loadRecyclerViewData(){

        String uri = getResources().getString(R.string.base_url)+"driver_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String data = "Y";
                        try {
//                            JSONObject JO = new JSONObject(s);
                            JSONArray JA = new JSONArray(s);
//                            String code = JO.getString("data_code");
                            if(JA.length()>0){

                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    FetchedListOfDriver flp = new FetchedListOfDriver(JO1.getString("id"),
                                            JO1.getString("name"),
                                            JO1.getString("mobile"),
                                            JO1.getString("password"),
                                            JO1.getString("status"));
                                    listOfDrivers.add(flp);
                                }

                                adapter = new DriverListAdapter(listOfDrivers,getApplicationContext());
                                recyclerView.setAdapter(adapter);



                            }else{
                                Toast.makeText(DriverListActivity.this, "check", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // Toast.makeText(getContext(), "Error.Response",Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
    }
}