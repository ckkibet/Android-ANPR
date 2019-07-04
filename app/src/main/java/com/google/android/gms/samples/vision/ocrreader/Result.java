package com.google.android.gms.samples.vision.ocrreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Result extends AppCompatActivity {
    Intent intent;
    String value;
    String text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();

        // data to populate the RecyclerView with
//        ArrayList<String> animalNames = new ArrayList<>();
//        animalNames.add("Horse");
//        animalNames.add("Cow");
//        animalNames.add("Camel");
//        animalNames.add("Sheep");
//        animalNames.add("Goat");

//        // set up the RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyRecyclerViewAdapter(this, animalNames);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);


//    @Overrde
//    public void onItemClick(View view, int position) {
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
//    }

//        text.setText(getIntent().getStringExtra("results"));
        text = intent.getStringExtra("results");

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate = df.format(c.getTime());

        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder
                .setMessage(text +"\n" +"On: " +formattedDate)
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GetDriverName();
                    }
                });

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

    }

    private void GetDriverName() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Driver Details");
        alert.setMessage("Name:");

        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Save Record", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog alertDialog = alert.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if(input.getText().toString().trim().equals("")){
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setEnabled(false);
//                    if (button != null){
//                        button.setEnabled(false);
//                    }
                }
            }
        });


        alert.show();


//        button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        String name;
//        name = input.getText().toString();
//
//        if(!name.matches("[a-zA-Z]")){
//            button.setEnabled(false);
//            input.requestFocus();
//            input.setError("Please Enter Driver's Name");
//            input.clearFocus();
//            return;
//
//        if (TextUtils.isEmpty(input.getText().toString())){
//                button.setEnabled(false);
//            }



// Set an EditText view to get user input


//        alert.setPositiveButton("Save Record", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
////                input.setError(null);
//
//                boolean cancel = false;
//                View focusview = null;
//
//
//                }
//                alert.dismiss();
//            }
//
//        });
//        alert.setCancelable(false);
//        alert.show();
    }

    }

