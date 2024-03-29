package com.jk.parkingproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.type.LatLng;
import com.jk.parkingproject.databinding.ActivityParkingDetailsBinding;
import com.jk.parkingproject.helpers.LocationHelper;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class ParkingDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityParkingDetailsBinding binding;
    String TAG = "QWERTY";
    Parking currentParking;
    ParkingViewModel parkingViewModel;
    LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityParkingDetailsBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        parkingViewModel = ParkingViewModel.getInstance(getApplication());
        locationHelper = LocationHelper.getInstance();
//        currentParking = (Parking) getIntent().getSerializableExtra("currentParking");

        parkingViewModel.getCurrentParking(getIntent().getStringExtra(" "));

        parkingViewModel.currentParking.observe(this, new Observer<Parking>() {
            @Override
            public void onChanged(Parking parking) {

                currentParking = parking;
                loadParkingInfoDetails();
            }
        });

//        loadParkingInfoDetails();

        this.binding.btnDeleteParkingParkingDetails.setOnClickListener(this);
        this.binding.btnEditParkingParkingDetails.setOnClickListener(this);
        binding.btnMap.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        parkingViewModel.getCurrentParking(getIntent().getStringExtra("parkingId"));
    }

    private void loadParkingInfoDetails() {

        this.binding.tvCarNumberParkingDetails.setText(currentParking.getCarPlateNumber());
        this.binding.tvBuildingCodeParkingDetails.setText(currentParking.getBuildingCode());
        this.binding.tvHouseSuiteNumberParkingDetails.setText(currentParking.getHostSuiteNumber());
        this.binding.tvNoOfHoursParkingDetails.setText(currentParking.getNoOfHours());
        this.binding.tvDateAndTimeOfParkingParkingDetails.setText(formatDate(currentParking.getDateOfParking()));
        Location currentLocation = new Location("");
        currentLocation.setLatitude(currentParking.getLatitude());
        currentLocation.setLongitude(currentParking.getLongitude());
        this.binding.tvParkingLocationParkingDetails.setText(locationHelper.getAddress(this, currentLocation));

    }

    private void deleteParking() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Parking");
        builder.setMessage("Are you sure you want to delete this parking?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        parkingViewModel.deleteParking(currentParking.getId());
                        currentParking = null;
                        Toast.makeText(ParkingDetailsActivity.this, "Parking deleted successfully", Toast.LENGTH_SHORT).show();
                        finishAndRemoveTask();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        builder.show();

    }

    private void editParking() {

        Intent intent = new Intent(ParkingDetailsActivity.this, AddNewParking.class);
        intent.putExtra("currentParking", currentParking);
        startActivity(intent);

    }

    private String formatDate(Date date){

        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnEditParking_ParkingDetails:
                editParking();
                break;
            case R.id.btnDeleteParking_ParkingDetails:
                deleteParking();
                break;
            case R.id.btnMap:
                Intent i = new Intent(this, MapsActivity.class);
                i.putExtra("parking_lat", currentParking.getLatitude());
                i.putExtra("parking_long", currentParking.getLongitude());
                startActivity(i);
                break;
        }
    }
}