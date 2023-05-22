package com.tamim.task71p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView typeTextView;
    TextView nameTextView;
    TextView phoneTextView;
    TextView descriptionTextView;
    TextView dateTextView;
    TextView locationTextView;
    Button removeButton;
    Advert advert;
    AdvertDatabaseHelper db;
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
        Intent intent = getIntent();
        advert = (Advert) getIntent().getSerializableExtra("advert");
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeButtonPressed();
            }
        });

        setupData();
        db = new AdvertDatabaseHelper(this);
    }

    public void setupData() {
        typeTextView.setText(advert.getType().getPostTypeValue());
        nameTextView.setText(advert.getName());
        phoneTextView.setText(advert.getPhone());
        descriptionTextView.setText(advert.getDescription());
        dateTextView.setText(advert.getDate());
        locationTextView.setText("Lat: "+advert.getLat()+" Long: "+ advert.getLng());
    }
    private void removeButtonPressed() {
        System.out.println("removeButtonPressed");
        Boolean result = db.deleteEntry(advert.getId());
        if (result) {
            showToast("Deleted successfully");
            this.finish();
        } else {
            showToast("Deletion failed");
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}