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

public class SalesListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfSales> listOfSales;
    private List<FetchedListOfSales> filteredListSales;
    EditText customer_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        customer_search = findViewById(R.id.cust_search);
        recyclerView = (RecyclerView) findViewById(R.id.show_sales);
        recyclerView.setLayoutManager(new LinearLayoutManager(SalesListActivity.this));
        listOfSales = new ArrayList<>();
        filteredListSales = new ArrayList<>();
        adapter = new SalesListAdapter(listOfSales,getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData();

        customer_search.addTextChangedListener(new TextWatcher() {
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

        filteredListSales.clear();
        for (FetchedListOfSales flp : listOfSales) {
            if(flp.getCustName().toLowerCase().contains(s.toLowerCase())){
                filteredListSales.add(flp);
            }
        }

        if(s!=null && s!=""){
            adapter = new SalesListAdapter(filteredListSales,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            adapter = new SalesListAdapter(listOfSales,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadRecyclerViewData(){

        String uri = getResources().getString(R.string.base_url)+"sale.php";
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
                                    FetchedListOfSales flp = new FetchedListOfSales(JO1.getString("id"),
                                            JO1.getString("cust_name"),
                                            JO1.getString("cust_mobile"),
                                            JO1.getString("market_name"),
                                            JO1.getString("flower_name"),
                                            JO1.getString("vehicle_no"),
                                            JO1.getString("weight"),
                                            JO1.getString("rate"),
                                            JO1.getString("total"));
                                    listOfSales.add(flp);
                                }

                                adapter = new SalesListAdapter(listOfSales,getApplicationContext());
                                recyclerView.setAdapter(adapter);


                            }else{
                                Toast.makeText(SalesListActivity.this, "check", Toast.LENGTH_SHORT).show();
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