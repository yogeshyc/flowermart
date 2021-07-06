package com.softhub.flowermart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ViewHolder> {

    private List<FetchedListOfDriver> listOfDrivers;
    private Context context;
    String driver_id;


    public DriverListAdapter(List<FetchedListOfDriver> listOfDrivers, Context context) {
        this.listOfDrivers = listOfDrivers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drivers, parent, false);

        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfDriver listOfDriver = listOfDrivers.get(position);

        holder.driverName.setText(listOfDriver.getDriverName());
        holder.driverMobile.setText(listOfDriver.getDriverMobile());
        holder.driverPassword.setText(listOfDriver.getDriverPassword());
        holder.driverStatus.setText(listOfDriver.getDriverStatus());
        holder.deactive.setOnClickListener(v -> {

            driver_id = listOfDriver.getDriverId();
            deactiveDriver();

        });


    }

    private void deactiveDriver() {

        String uri = context.getResources().getString(R.string.base_url)+"deactivate_driver.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String data = "Y";
                        try {
                            JSONObject JO = new JSONObject(s);

                            if(JO.getString("data_code").equals("200")){
                                Toast.makeText(context, JO.getString("message"), Toast.LENGTH_SHORT).show();
                                //((DriverListActivity) context).loadRecyclerViewData();
                                Intent intent = new Intent(context, DriverListActivity.class);

                                context.startActivity(intent);

                            }else{
                                Toast.makeText(context, JO.getString("message"), Toast.LENGTH_SHORT).show();
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
                }){@Override
        public Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            params.put("driver_id", driver_id);
            return params;
        }
        };
        queue.add(stringRequest);

    }



    @Override
    public int getItemCount() {
        return listOfDrivers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView driverName;
        public TextView driverMobile;
        public TextView driverPassword;
        public TextView driverStatus;
        Button deactive;


        public ViewHolder(View itemView) {
            super(itemView);

            driverName = (TextView) itemView.findViewById(R.id.driverName);
            driverMobile = (TextView) itemView.findViewById(R.id.driverMobile);
            driverPassword = (TextView) itemView.findViewById(R.id.driverPassword);
            driverStatus = (TextView) itemView.findViewById(R.id.driverStatus);
            deactive = itemView.findViewById(R.id.deactive);
        }
    }
}