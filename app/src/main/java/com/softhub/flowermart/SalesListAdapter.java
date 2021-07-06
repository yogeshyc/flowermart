package com.softhub.flowermart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder> {

    private List<FetchedListOfSales> listOfSales;
    private Context context;


    public SalesListAdapter(List<FetchedListOfSales> listOfSales, Context context) {
        this.listOfSales = listOfSales;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfSales listOfsale = listOfSales.get(position);

        holder.custName.setText(listOfsale.getCustName());
        holder.custMobile.setText(listOfsale.getCustMobile());
        holder.marketName.setText(listOfsale.getMarketName());
        holder.flowerName.setText(listOfsale.getFlowersaleName());
        holder.flowerWeight.setText(listOfsale.getFlowersaleWeight());
        holder.flowerRate.setText(listOfsale.getFlowersaleRate());
        holder.totalAmount.setText(listOfsale.getTotalsaleAmount());
        holder.vehicleNo.setText(listOfsale.getVehicleNo());

    }

    @Override
    public int getItemCount() {
        return listOfSales.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView custName;
        public TextView custMobile;
        public TextView marketName;
        public TextView flowerName;
        public TextView flowerWeight;
        public TextView flowerRate;
        public TextView totalAmount;
        public TextView vehicleNo;




        public ViewHolder(View itemView) {
            super(itemView);

            custName = (TextView) itemView.findViewById(R.id.custName);
            custMobile = (TextView) itemView.findViewById(R.id.custMobile);
            marketName = (TextView) itemView.findViewById(R.id.marketName);
            flowerName = (TextView) itemView.findViewById(R.id.flowerName);
            flowerWeight = (TextView) itemView.findViewById(R.id.flowerWeight);
            flowerRate = (TextView) itemView.findViewById(R.id.flowerRate);
            totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
            vehicleNo = (TextView) itemView.findViewById(R.id.vehicleNo);
        }
    }
}