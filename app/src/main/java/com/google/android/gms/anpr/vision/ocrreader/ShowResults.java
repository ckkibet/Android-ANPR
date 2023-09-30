package com.google.android.gms.anpr.vision.ocrreader;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ShowResults extends AppCompatActivity {
    Button show;
    mydatabase database;
    RecyclerView recyclerView;
    RecycleAdapter recycler;
    List<DataModel> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view);
        datamodel =new ArrayList<DataModel>();
        recyclerView = (RecyclerView) findViewById(R.id.recycle);


        database = new mydatabase(ShowResults.this);
        datamodel=  database.getdata();
        recycler =new RecycleAdapter(datamodel);


        RecyclerView.LayoutManager reLayoutManager =new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(reLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycler);

    }

    public void onBackPressed(){
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }

}