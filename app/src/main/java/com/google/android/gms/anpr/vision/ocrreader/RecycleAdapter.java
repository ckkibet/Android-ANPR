package com.google.android.gms.anpr.vision.ocrreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.Myholder> {
    List<DataModel> dataModelArrayList;

    public RecycleAdapter(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView id,Numberplate,date, time, driver;

        public Myholder(View itemView) {
            super(itemView);

           // id = (TextView) itemView.findViewById(R.id.city1);
            Numberplate = (TextView) itemView.findViewById(R.id.number_plate);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            driver = (TextView) itemView.findViewById(R.id.driver);

        }
    }


    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_results,null);
        return new Myholder(view);

    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        DataModel dataModel=dataModelArrayList.get(position);
       // holder.id.setText(dataModel.getId());
        holder.Numberplate.setText(dataModel.getNumber_plate());
        holder.date.setText(dataModel.getDate());
        holder.time.setText(dataModel.getTime());
        holder.driver.setText(dataModel.getDriver());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}