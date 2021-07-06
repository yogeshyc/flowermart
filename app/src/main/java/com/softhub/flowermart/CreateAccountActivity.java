package com.softhub.flowermart;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class CreateAccountActivity extends AppCompatActivity {

    TextView login_txt;
    private EditText regi_name;
    private EditText regi_mobile;
    private EditText regi_password;
    Button sign_up;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        progressDialog = new ProgressDialog(CreateAccountActivity.this);

        login_txt = findViewById(R.id.login_txt);
        regi_name = (EditText) findViewById(R.id.regi_name);
        regi_mobile = (EditText) findViewById(R.id.regi_mobile);
        regi_password = (EditText) findViewById(R.id.regi_password);
        sign_up = findViewById(R.id.sign_up);

        login_txt.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        String uri = getResources().getString(R.string.base_url)+"sign_up.php";
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
                                Intent intent = new Intent(CreateAccountActivity.this,DriverListActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(),"Account created successfully",Toast.LENGTH_LONG).show();
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

                params.put("name", regi_name.getText().toString());
                params.put("mobile", regi_mobile.getText().toString());
                params.put("password", regi_password.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);

    }
}