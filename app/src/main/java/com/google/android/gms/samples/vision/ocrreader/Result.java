package com.google.android.gms.samples.vision.ocrreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Result extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    Intent intent;
    String value;
    String text, formattedDate;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private EditText input;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Intent intent = getIntent();
        text = intent.getStringExtra("results");


        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c.getTime());

        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder
                .setMessage(text
                        +"\n"
                        +"On: "
                        +formattedDate
                        +"\n"
                        +"\n"
                        +"Enter Driver's Name Below:")
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayResults();
                    }
                });
        EditText input = new EditText(this);
        a_builder.setView(input);

        a_builder.setNegativeButton("Scan Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent;
                intent = new Intent(Result.this, OcrCaptureActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alert = a_builder.create();
        alert.setTitle("Scanned Plate:");
        alert.show();
        alert.setCancelable(false);
    }

    private void displayResults() {
        ArrayList<String> animalNames = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);

        String driverName;
        driverName = input.getText().toString();




        // data to populate the RecyclerView with
//        animalNames.add("!st");
//        int insertIndex = 0;
//        animalNames.add(insertIndex, text +"\n" +"On: " +formattedDate +"\n" +"Driven By: "
//                +driverName);
//        adapter.notifyItemRangeChanged(0, animalNames.size());

        animalNames.add(
                text +"\n"
                +formattedDate +"\n"
                +"Driven By: " +driverName
        );

        // set up the RecyclerView
        recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
