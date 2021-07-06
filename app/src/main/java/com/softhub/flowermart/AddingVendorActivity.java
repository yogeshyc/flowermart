package com.softhub.flowermart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddingVendorActivity extends AppCompatActivity {

    Button add_vendor;
    EditText vendor_name,vendor_mobile,vendor_address;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_vendor);

        add_vendor = findViewById(R.id.add_vendor_info);
        vendor_name = findViewById(R.id.vendor_name);
        vendor_mobile = findViewById(R.id.vendor_mobile);
        vendor_address = findViewById(R.id.vendor_address);
        progressDialog = new ProgressDialog(AddingVendorActivity.this);

        add_vendor.setOnClickListener(v -> {
            addVendor();
        });
    }

    private void addVendor() {
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"add_vendor.php";
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
                                Intent intent = new Intent(AddingVendorActivity.this,Home.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext()," Vendor Added successfully",Toast.LENGTH_LONG).show();
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

                params.put("vendor_name", vendor_name.getText().toString());
                params.put("vendor_mobile", vendor_mobile.getText().toString());
                params.put("vendor_address", vendor_address.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);

    }

}
