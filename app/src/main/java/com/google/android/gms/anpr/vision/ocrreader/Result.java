package com.google.android.gms.anpr.vision.ocrreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Result extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    Intent intent;
    String value;
    String text, formattedDate, formattedTime;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    List<String> animalNames;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        recyclerView = findViewById(R.id.rvAnimals);

        final Intent intent = getIntent();
        text = intent.getStringExtra("results");

        animalNames = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, animalNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault());
        SimpleDateFormat dd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(c.getTime());
        formattedTime = dd.format(c.getTime());

        final EditText input = new EditText(this);

        AlertDialog.Builder a_builder = new AlertDialog.Builder(this);
        a_builder
                .setMessage(text
                        +"\n"
                        +"On: "
                        +formattedDate
                        +"\n"
                        +"Time: "
                        +formattedTime
                        +"\n"
                        +"Enter Driver's Name Below:"
                        )
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(Result.this)
                                .setMessage("Are you sure this is the correct Name: "+input.getText()+"?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Save data to db
                                        Editable driver = input.getText();

                                            displayResults(driver);

                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();

                    }
                });

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


    public void displayResults(Editable driver) {

        String plate = text;
        String date = formattedDate;
        String time = formattedTime;
        String Driver = String.valueOf(driver);

        mydatabase mydatabase = new mydatabase(Result.this);
        boolean trt = (boolean) mydatabase.insertdata(plate, date, time, Driver);
        if (trt){
            Toast.makeText(Result.this, "Data Saved", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Result.this, WelcomeScreen.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onItemClick(View view, int position) {

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, WelcomeScreen.class);
        startActivity(intent);
    }
    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(listState);
        }
    }
}
