package com.softhub.flowermart;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FlowerListAdapter extends RecyclerView.Adapter<FlowerListAdapter.ViewHolder> {

    private List<FetchedListOfFlower> listOfFlowers;
    private Context context;


    public FlowerListAdapter(List<FetchedListOfFlower> listOfFlowers, Context context) {
        this.listOfFlowers = listOfFlowers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flowers, parent, false);


        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FetchedListOfFlower listOfFlower = listOfFlowers.get(position);


        holder.flowerName.setText(listOfFlower.getFlowerName());


    }

    @Override
    public int getItemCount() {
        return listOfFlowers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView flowerName;


        public ViewHolder(View itemView) {
            super(itemView);

            flowerName = (TextView) itemView.findViewById(R.id.flowerName);

        }
    }
}