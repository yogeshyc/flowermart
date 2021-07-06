package com.softhub.flowermart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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

public class FlowerListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<FetchedListOfFlower> listOfFlowers;
    private List<FetchedListOfFlower> filteredListFlowers;
    EditText flower_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower_list);

        flower_search = findViewById(R.id.flower_search);

        recyclerView = (RecyclerView) findViewById(R.id.show_flowers);
        recyclerView.setLayoutManager(new LinearLayoutManager(FlowerListActivity.this));
        listOfFlowers = new ArrayList<>();
        filteredListFlowers = new ArrayList<>();

        adapter = new FlowerListAdapter(listOfFlowers,getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadRecyclerViewData();

        flower_search.addTextChangedListener(new TextWatcher() {
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

        filteredListFlowers.clear();
        for (FetchedListOfFlower flp : listOfFlowers) {
            if(flp.getFlowerName().toLowerCase().contains(s.toLowerCase())){
                filteredListFlowers.add(flp);
            }
        }

        if(s!=null && s!=""){
            adapter = new FlowerListAdapter(filteredListFlowers,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }else{
            adapter = new FlowerListAdapter(listOfFlowers,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
    }
    private void loadRecyclerViewData(){

        String uri = getResources().getString(R.string.base_url)+"flower_list.php";
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
                                    FetchedListOfFlower flp = new FetchedListOfFlower(JO1.getString("id"),
                                            JO1.getString("flower_name"));
                                    listOfFlowers.add(flp);
                                }

                                adapter = new FlowerListAdapter(listOfFlowers,getApplicationContext());
                                recyclerView.setAdapter(adapter);


                            }else{
                                Toast.makeText(FlowerListActivity.this, "check", Toast.LENGTH_SHORT).show();
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