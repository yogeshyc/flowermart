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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfVendor> listOfVendors;
    private List<FetchedListOfVendor> filteredListVendors;
    EditText vendor_search;
    private  Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list);

        vendor_search = findViewById(R.id.vendor_search);
        session = new Session(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.show_vendors);
        recyclerView.setLayoutManager(new LinearLayoutManager(VendorListActivity.this));
        listOfVendors = new ArrayList<>();
        filteredListVendors = new ArrayList<>();
        adapter = new VendorListAdapter(listOfVendors,getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData();

        vendor_search.addTextChangedListener(new TextWatcher() {
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

        filteredListVendors.clear();
        for (FetchedListOfVendor flp : listOfVendors) {
            if(flp.getVendorName().toLowerCase().contains(s.toLowerCase())){
                filteredListVendors.add(flp);
            }
        }

        if(s!=null && s!=""){
            adapter = new VendorListAdapter(filteredListVendors,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            adapter = new VendorListAdapter(listOfVendors,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadRecyclerViewData(){

        String uri = getResources().getString(R.string.base_url)+"vendor_list.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
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
                                    FetchedListOfVendor flp = new FetchedListOfVendor(JO1.getString("id"),
                                            JO1.getString("vendor_name"),
                                            JO1.getString("added_name"),
                                            JO1.getString("vendor_mobile"),
                                            JO1.getString("vendor_address"),
                                            JO1.getString("flower_name"),
                                            JO1.getString("weight"),
                                            JO1.getString("rate"),
                                            JO1.getString("total"),
                                            JO1.getString("paid"),
                                            JO1.getString("reamaining"));
                                    listOfVendors.add(flp);
                                }

                                adapter = new VendorListAdapter(listOfVendors,getApplicationContext());
                                recyclerView.setAdapter(adapter);


                            }else{
                                Toast.makeText(VendorListActivity.this, "check", Toast.LENGTH_SHORT).show();
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
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("id",session.prefs.getString("user_id",null));
                params.put("role",session.prefs.getString("userRole", ""));

                return params;
            }
        };
        queue.add(stringRequest);
    }
}