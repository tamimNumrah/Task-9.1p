package com.tamim.task71p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView header;
    RadioButton radio_1;
    RadioButton radio_2;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextDescription;
    EditText editTextDate;
    EditText editTextLocation;
    Button saveButton;
    POST_TYPE type = POST_TYPE.LOST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        radioGroup = findViewById(R.id.radioGroup);
        header = findViewById(R.id.header);
        radio_1 = findViewById(R.id.radio_1);
        radio_2 = findViewById(R.id.radio_2);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        editTextLocation = findViewById(R.id.editTextLocation);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
        refreshSelection();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_1:
                if (checked)
                    type = POST_TYPE.LOST;
                break;
            case R.id.radio_2:
                if (checked)
                    type = POST_TYPE.FOUND;
                break;
        }
        refreshSelection();
    }
    private void refreshSelection() {
        int contentArray;
        switch (type) {
            case LOST:
                break;
            case FOUND:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
    private void saveButtonPressed() {
        System.out.println("SaveButtonPressed");
        String name = editTextName.getText().toString();
        if (name.matches("")) {
            showToast("You did not enter name");
            return;
        }
        String phone = editTextPhone.getText().toString();
        if (phone.matches("")) {
            showToast("You did not enter phone");
            return;
        }
        String description = editTextDescription.getText().toString();
        if (description.matches("")) {
            showToast("You did not enter description");
            return;
        }
        String date = editTextDate.getText().toString();
        if (date.matches("")) {
            showToast("You did not enter date");
            return;
        }
        String location = editTextLocation.getText().toString();
        if (location.matches("")) {
            showToast("You did not enter location");
            return;
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}