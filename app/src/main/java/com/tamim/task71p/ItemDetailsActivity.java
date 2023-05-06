package com.tamim.task71p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView typeTextView;
    TextView nameTextView;
    TextView phoneTextView;
    TextView descriptionTextView;
    TextView dateTextView;
    TextView locationTextView;
    Button removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        typeTextView = findViewById(R.id.typeTextView);
        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateTextView = findViewById(R.id.dateTextView);
        locationTextView = findViewById(R.id.locationTextView);
        removeButton = findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButtonPressed();
            }
        });
    }
    private void removeButtonPressed() {
        System.out.println("removeButtonPressed");
    }

}