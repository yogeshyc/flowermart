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
import java.util.List;
import java.util.Map;

public class AddPurchaseActivity extends AppCompatActivity {

    //private Spinner spinnerVendor;
    //private ArrayList<String> vendors;
    private Spinner spinnerFlower;
    private ArrayList<String> flowers;
    private JSONArray result;
    private List<FetchedListOfVendor>purchaseList;
    TextView purchase_total,flower_name,reamaining;
    double total;
    EditText weight,rate,vendor_name,vendor_mobile,vendor_address,paid;
    double w,r,p,re;
    Button add_purchase;
    String id;
    private  Session session;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        vendor_name = findViewById(R.id.vendor_name_txt);
        flower_name = findViewById(R.id.flower_name_txt);
        vendor_mobile = findViewById(R.id.vendor_mobile_txt);
        vendor_address = findViewById(R.id.vendor_address_txt);
        session = new Session(getApplicationContext());
        weight = findViewById(R.id.weight);
        rate = findViewById(R.id.rate);
        paid = findViewById(R.id.paid);
        reamaining = findViewById(R.id.reamaining);
        add_purchase = findViewById(R.id.add_purchase);
        progressDialog = new ProgressDialog(AddPurchaseActivity.this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        //vendors = new ArrayList<String>();
        //spinnerVendor = findViewById(R.id.vendor);
        flowers = new ArrayList<String>();
        purchaseList = new ArrayList<>();
        spinnerFlower = findViewById(R.id.flower);
        //getData();
        getFlower();

       /* w = Double.parseDouble(weight.getText().toString());
        r = Double.parseDouble(rate.getText().toString());*/
        purchase_total = findViewById(R.id.purchase_total);
        //purchase_total.setText("RS  "+Double.toString(total));
        weight.addTextChangedListener(new TextWatcher() {
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

        rate.addTextChangedListener(new TextWatcher() {
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
                        purchase_total.setText("RS  "+Double.toString(total));
                    }

                }

            }
        });


        paid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString()!=null){
                    if (s.toString().isEmpty()==false){
                        p = Double.parseDouble(s.toString());
                        Log.d("p", String.valueOf(p));
                        re = total-p;
                        reamaining.setText("RS  "+Double.toString(re));
                    }

                }

            }
        });

        /*w = Double.parseDouble(weight.getText().toString());
        r = Double.parseDouble(rate.getText().toString());

        total = w*r;

        purchase_total.setText("RS  "+Double.toString(total));*/

       /* spinnerVendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vendor_name.setText(""+vendors.get(position));


                spinnerVendor.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

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
        add_purchase.setOnClickListener(v -> {
          addPurchase();
        });
    }

    private void addPurchase() {

        FetchedListOfVendor fetchedListOfVendor = new FetchedListOfVendor();
        fetchedListOfVendor.setVendorId("1");
        fetchedListOfVendor.setAdded_name(session.prefs.getString("Unm",""));
        fetchedListOfVendor.setFlowerName(flower_name.getText().toString());
        fetchedListOfVendor.setVendorName(vendor_name.getText().toString());
        fetchedListOfVendor.setFlowerRate(rate.getText().toString());
        fetchedListOfVendor.setFlowerWeight(weight.getText().toString());
        fetchedListOfVendor.setVendorMobile(vendor_mobile.getText().toString());
        fetchedListOfVendor.setVendorAddress(vendor_address.getText().toString());
        fetchedListOfVendor.setTotalAmount(purchase_total.getText().toString());
        fetchedListOfVendor.setPaidAmount(paid.getText().toString());
        fetchedListOfVendor.setReAmount(reamaining.getText().toString());

        purchaseList.add(fetchedListOfVendor);
        JSONArray jsArray = new JSONArray();

        for (int i=0;i<purchaseList.size();i++)
        {
            jsArray.put(purchaseList.get(i));
        }


        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"add_purchase.php";
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
                                Intent intent = new Intent(AddPurchaseActivity.this,Home.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext()," Purchase Order Added successfully",Toast.LENGTH_LONG).show();
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
                Map<String, JSONArray> params = new HashMap<>();

                /*String[] stringArray
                        = jsArray.(new String[size]);
*/

                params.put("vendor_name",jsArray);

                /*params.put("vendor_name", vendor_name.getText().toString());
                params.put("vendor_mobile", vendor_mobile.getText().toString());
                params.put("vendor_address", vendor_address.getText().toString());
                params.put("flower_name", flower_name.getText().toString());
                params.put("weight", weight.getText().toString());
                params.put("rate", rate.getText().toString());
                params.put("total", String.valueOf(total));
                params.put("paid", paid.getText().toString());
                params.put("reamaining", String.valueOf(re));
                params.put("added_by",session.prefs.getString("user_id",null));
                params.put("added_name",session.prefs.getString("Unm",null));*/
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

    /*private void getData() {
        final StringRequest stringRequest = new StringRequest(Config.DATA_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject j = null;
                try{
                    j = new JSONObject(response);
                    result = j.getJSONArray(Config.JSON_ARRAY);
                    getVendors(result);
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
    }*/
   /* private void getVendors(JSONArray j) {

        vendors.add("select vendor");
        for (int i=0; i<j.length();i++){

            try{
                JSONObject jsonObject = j.getJSONObject(i);
                vendors.add(jsonObject.getString(Config.TAG_VENDORNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        spinnerVendor.setAdapter(new ArrayAdapter<String>(AddPurchaseActivity.this,android.R.layout.simple_spinner_dropdown_item,vendors));


    }*/
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

        spinnerFlower.setAdapter(new ArrayAdapter<String>(AddPurchaseActivity.this,android.R.layout.simple_spinner_dropdown_item,flowers));


    }
}