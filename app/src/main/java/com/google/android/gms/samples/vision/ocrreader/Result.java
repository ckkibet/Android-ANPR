package com.google.android.gms.samples.vision.ocrreader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Result extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    Intent intent;
    String value;
    String text, formattedDate, formattedTime;
    MyRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private EditText input;
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
        animalNames.add("The list below shows a list of all vehicles scanned");

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

    public void displayResults() {
        String string = text
                +"\n"
                +"On: "
                +formattedDate
                +"\n"
                +"Time: "
                +formattedTime
                +"\n";
        int insertIndex = 1;
        animalNames.add(insertIndex, string);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, OcrCaptureActivity.class);
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
