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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView create_account;
    EditText m,p;
    String mobile,name,userRole,user_id;
    private Session session;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_btn);
        create_account = findViewById(R.id.account_txt);
        m = findViewById(R.id.m);
        p = findViewById(R.id.p);
        session = new Session(getApplicationContext());
        progressDialog = new ProgressDialog(LoginActivity.this);

        create_account.setOnClickListener(v -> {
        Intent intent = new Intent(LoginActivity.this,CreateAccountActivity.class);
        startActivity(intent);
        });

        login.setOnClickListener(v -> {
            /*Intent intent = new Intent(LoginActivity.this,Home.class);
            startActivity(intent);*/
            loginUser();
        });

    }

    private void loginUser() {

        progressDialog.setMessage("Verifying...");
        progressDialog.show();

        String uri = getResources().getString(R.string.base_url)+"login.php";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject JO = new JSONObject(s);
                            String code = JO.getString("data_code");
                            if(code.equals("200")){
                                JSONArray JA = JO.getJSONArray("item_list");
                                for(int i=0; i<JA.length(); i++){
                                    JSONObject JO1 = JA.getJSONObject(i);
                                    user_id = JO1.getString("id");
                                    name = JO1.getString("name");
                                    mobile = JO1.getString("mobile");
                                    userRole = JO1.getString("role");
                                }
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Logged In Successfully!", Toast.LENGTH_SHORT).show();
                                openApp();
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
                        //progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration.Error!",Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("mobile", m.getText().toString());
                params.put("password", p.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);

    }
    private void openApp() {
        session.setLoggedin(true);
        session.setMobile(mobile);
        session.setUserName(name);
        session.setUserRole(userRole);
        session.setUserId(user_id);
        if(userRole.equals("Admin")){
            Intent i = new Intent(getApplicationContext(), Home.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }else{
            Intent i = new Intent(getApplicationContext(), EmployeePage.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("id",user_id);
            startActivity(i);
        }

    }
}