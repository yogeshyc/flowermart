package com.softhub.flowermart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.ViewHolder> {

    private List<FetchedListOfVendor> listOfVendors;
    private Context context;


    public VendorListAdapter(List<FetchedListOfVendor> listOfVendors, Context context) {
        this.listOfVendors = listOfVendors;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vendors, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfVendor listOfVendor = listOfVendors.get(position);

        holder.vendorName.setText(listOfVendor.getVendorName());
        holder.vendorMobile.setText(listOfVendor.getVendorMobile());
        holder.vendorAddress.setText(listOfVendor.getVendorAddress());
        holder.flowerName.setText(listOfVendor.getFlowerName());
        holder.flowerWeight.setText(listOfVendor.getFlowerWeight());
        holder.flowerRate.setText(listOfVendor.getFlowerRate());
        holder.totalAmount.setText(listOfVendor.getTotalAmount());
        holder.paidAmount.setText(listOfVendor.getPaidAmount());
        holder.reAmount.setText(listOfVendor.getReAmount());
        holder.added_by.setText(listOfVendor.getAdded_name());
    }

    @Override
    public int getItemCount() {
        return listOfVendors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView vendorName;
        public TextView vendorMobile;
        public TextView vendorAddress;
        public TextView flowerName;
        public TextView flowerWeight;
        public TextView flowerRate;
        public TextView totalAmount;
        public TextView paidAmount;
        public TextView reAmount;
        public TextView added_by;




        public ViewHolder(View itemView) {
            super(itemView);

            vendorName = (TextView) itemView.findViewById(R.id.vendorName);
            vendorMobile = (TextView) itemView.findViewById(R.id.vendorMobile);
            vendorAddress = (TextView) itemView.findViewById(R.id.vendorAddress);
            flowerName = (TextView) itemView.findViewById(R.id.flowerName);
            flowerWeight = (TextView) itemView.findViewById(R.id.flowerWeight);
            flowerRate = (TextView) itemView.findViewById(R.id.flowerRate);
            totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
            paidAmount = (TextView) itemView.findViewById(R.id.paidAmount);
            reAmount = (TextView) itemView.findViewById(R.id.reAmount);
            added_by = (TextView) itemView.findViewById(R.id.adderName);



        }
    }
}