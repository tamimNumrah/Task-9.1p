package com.tamim.task71p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.UUID;

public class CreateAdvertActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to update
    RadioGroup radioGroup;
    TextView header;
    RadioButton radio_1;
    RadioButton radio_2;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextDescription;
    EditText editTextDate;
    AutocompleteSupportFragment autocompleteFragment;
    String lat;
    String lng;
    Button getCurrentLocationButton;
    Button saveButton;
    POST_TYPE type = POST_TYPE.Lost;
    Boolean locationPermissionGranted = false;
    LocationManager mLocationManager;
    AdvertDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLocationPermission();
        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
        setContentView(R.layout.activity_create_advert);
        radioGroup = findViewById(R.id.radioGroup);
        header = findViewById(R.id.header);
        radio_1 = findViewById(R.id.radio_1);
        radio_2 = findViewById(R.id.radio_2);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextDate);
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        saveButton = findViewById(R.id.saveButton);
        getCurrentLocationButton = findViewById(R.id.getCurrentLocationButton);
        db = new AdvertDatabaseHelper(this);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonPressed();
            }
        });
        getCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocationButtonPressed();
            }
        });
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                System.out.println("Place: " + place.getName() + ", " + place.getId() + "LatLang: " + place.getLatLng().toString());
                lat = String.valueOf(place.getLatLng().latitude);
                lng = String.valueOf(place.getLatLng().longitude);
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                System.out.println("An error occurred: " + status);
            }
        });
        refreshSelection();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_1:
                if (checked)
                    type = POST_TYPE.Lost;
                break;
            case R.id.radio_2:
                if (checked)
                    type = POST_TYPE.Found;
                break;
        }
        refreshSelection();
    }

    private void refreshSelection() {
        int contentArray;
        switch (type) {
            case Lost:
                break;
            case Found:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private void getCurrentLocationButtonPressed() {
        System.out.println("getCurrentLocationButtonPressed");
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showToast("Location permission not enabled");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);
    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            System.out.println("Current location: "+location.getLatitude() + " " + location.getLongitude());
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
            autocompleteFragment.setText(+location.getLatitude() + ", " + location.getLongitude());
        }
    };
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        //updateLocationUI();
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
        if (lat == null && lng == null) {
            showToast("You did not enter location");
            return;
        }

        Advert advert = new Advert(UUID.randomUUID().toString(), name, type, phone, description, date, lat, lng);
        Boolean success = db.insertAdvert(advert);
        if (success) {
            showToast("Advert published successfully");
            editTextName.setText("");
            editTextPhone.setText("");
            editTextDescription.setText("");
            editTextDate.setText("");
            autocompleteFragment.setText("");
        } else {
            showToast("Advert could not be stored");
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}