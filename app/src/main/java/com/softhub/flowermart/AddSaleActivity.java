package com.softhub.flowermart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Map;

public class AddSaleActivity extends AppCompatActivity {

    private Spinner spinnerFlower;
    private ArrayList<String> flowers;
    private JSONArray result;
    TextView sale_total,flower_name;
    double total;
    EditText sale_weight,sale_rate,cust_name,cust_mobile,market_name,vehicle_no;
    double w,r;
    Button add_sale;
    private  Session session;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        cust_name = findViewById(R.id.cust_name_txt);
        flower_name = findViewById(R.id.flower_txt);
        cust_mobile = findViewById(R.id.cust_mobile_txt);
        market_name = findViewById(R.id.market_name_txt);
        session = new Session(getApplicationContext());
        sale_weight = findViewById(R.id.sale_weight);
        sale_rate = findViewById(R.id.sale_rate);
        vehicle_no = findViewById(R.id.vehicle_no_txt);
        add_sale = findViewById(R.id.add_sale);
        progressDialog = new ProgressDialog(AddSaleActivity.this);

        sale_total = findViewById(R.id.sale_total);

        flowers = new ArrayList<String>();
        spinnerFlower = findViewById(R.id.flower);
        getFlower();


        add_sale.setOnClickListener(v -> {

            addSales();
        });

        sale_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("weight",s.toString());
                if (s.toString()!=null){
                    if (s.toString().isEmpty()==false){
                        w = Double.parseDouble(s.toString());
                        Log.d("w", String.valueOf(w));
                    }

                }

            }
        });

        sale_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /*r = Double.parseDouble(s.toString());
                total = (w)*(r);

                purchase_total.setText("RS  "+Double.toString(total));

                Log.d("total", String.valueOf(purchase_total));*/

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.d("rate",s.toString());
                if (s.toString()!=null){
                    if (s.toString().isEmpty()==false){
                        r = Double.parseDouble(s.toString());
                        Log.d("r", String.valueOf(r));
                        total = (w)*(r);
                        sale_total.setText("RS  "+Double.toString(total));
                    }

                }

            }
        });

        spinnerFlower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flower_name.setText(""+flowers.get(position));


                spinnerFlower.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addSales() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"add_sales.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String result = JO.getString("data_code");
                            if(result.equals("200")){
                                //submit.setVisibility(View.INVISIBLE);
                                //Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_LONG).show();
                                //layoutOtp.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                                Intent intent = new Intent(AddSaleActivity.this,Home.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext()," Sales Order Added successfully",Toast.LENGTH_LONG).show();
//                                openApp();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), JO.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(), "Registration.Error!",Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("cust_name", cust_name.getText().toString());
                params.put("cust_mobile", cust_mobile.getText().toString());
                params.put("market_name", market_name.getText().toString());
                params.put("vehicle_no", flower_name.getText().toString());
                params.put("flower_name", flower_name.getText().toString());
                params.put("weight", sale_weight.getText().toString());
                params.put("rate", sale_rate.getText().toString());
                params.put("total", String.valueOf(total));
                return params;
            }
        };
        queue.add(stringRequest);


    }

    private void getFlower() {
        final StringRequest stringRequest = new StringRequest(Config.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject j = null;
                try{
                    j = new JSONObject(response);
                    result = j.getJSONArray(Config.JSON_ARRAY);
                    getFlowers(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void getFlowers(JSONArray j) {

        flowers.add("select flower");
        for (int i=0; i<j.length();i++){

            try{
                JSONObject jsonObject = j.getJSONObject(i);
                flowers.add(jsonObject.getString(Config.TAG_FLOWERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        spinnerFlower.setAdapter(new ArrayAdapter<String>(AddSaleActivity.this,android.R.layout.simple_spinner_dropdown_item,flowers));


    }
}