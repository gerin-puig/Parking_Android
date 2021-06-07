package com.jk.parkingproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.jk.parkingproject.databinding.ActivityAddNewParkingBinding;
import com.jk.parkingproject.models.Parking;
import com.jk.parkingproject.viewmodels.ParkingViewModel;

import java.util.Calendar;

public class AddNewParking extends AppCompatActivity {

    ActivityAddNewParkingBinding binding;
    String[] cars = {"Tesla - CD12 AQ8238", "Benz - RD007 BNZ143", "Hummer EV - RUDE BST"};
    String[] noOfHours = {"less than an hour", "less than 4 hours", "less than 12 hours", "24 hours"};

    private Parking newParking = new Parking();
    private ParkingViewModel parkingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hide action bar
        getSupportActionBar().hide();

        this.binding = ActivityAddNewParkingBinding.inflate(getLayoutInflater());
        View view = this.binding.getRoot();
        setContentView(view);

        this.parkingViewModel = ParkingViewModel.getInstance(this.getApplication());
        newParking = new Parking();

        ArrayAdapter carListAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cars);
        carListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerSelectCar.setAdapter(carListAdapter);


        ArrayAdapter noOfHoursAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, noOfHours);
        noOfHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spinnerNoOfHours.setAdapter(noOfHoursAdapter);

        this.binding.btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewParking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        AddNewParking.this.binding.btnSelectDate.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.date_time_selected)));
                        AddNewParking.this.binding.btnSelectDate.setTextColor(getResources().getColor(R.color.black));
                        AddNewParking.this.binding.btnSelectDate.setText(day+" - "+month+" - "+year);
                        newParking.setDateOfParking(c.getTime());
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        this.binding.btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();

                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewParking.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                        AddNewParking.this.binding.btnSelectTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.date_time_selected)));
                        AddNewParking.this.binding.btnSelectTime.setTextColor(getResources().getColor(R.color.black));

                        AddNewParking.this.binding.btnSelectTime.setText(hour+" : "+minute);
                    }
                }, mHour, mMinute, true);

                timePickerDialog.show();

            }
        });

        this.binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateData()){
                    // add parking to Firebase

                    saveDataToFirebase();

                }



            }
        });

    }

    private Boolean validateData(){

        Boolean isValid = true;

        if(this.binding.etBuildingCode.getText().toString().trim().isEmpty()){

            this.binding.etBuildingCode.setError("Enter a building code");
            isValid = false;

        }

        else if(this.binding.etBuildingCode.getText().toString().trim().length() != 5){
            Snackbar.make(this, this.binding.getRoot(), "Building code should be exactly 5 characters", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.binding.etSuiteNumber.getText().toString().trim().isEmpty()){
            this.binding.etSuiteNumber.setError("Enter a suite number");
            isValid = false;
        }

        else if(this.binding.etSuiteNumber.getText().toString().trim().length() < 2 || this.binding.etSuiteNumber.getText().toString().trim().length() > 5 ){
            Snackbar.make(this, this.binding.getRoot(), "Suit number code should be 2 to 5 characters long", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.binding.btnSelectDate.getText().toString().equalsIgnoreCase("select date")){
            Snackbar.make(this, this.binding.getRoot(), "Please provide the date of parking", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }

        else if(this.binding.btnSelectTime.getText().toString().equalsIgnoreCase("select time")){
            Snackbar.make(this, this.binding.getRoot(), "Please provide the time of parking", Snackbar.LENGTH_SHORT).show();
            isValid = false;
        }


        return isValid;
    }

    private void saveDataToFirebase(){

        this.newParking.setCarNumber(this.binding.spinnerSelectCar.getSelectedItem().toString());
        this.newParking.setBuildingCode(this.binding.etBuildingCode.getText().toString());
        this.newParking.setHostSuiteNumber(this.binding.etSuiteNumber.getText().toString());
        this.newParking.setNoOfHours(this.binding.spinnerNoOfHours.getSelectedItem().toString());

        this.parkingViewModel.addParking(newParking);

    }
}