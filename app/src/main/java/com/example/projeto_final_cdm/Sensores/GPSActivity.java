package com.example.projeto_final_cdm.Sensores;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;

import com.example.projeto_final_cdm.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;


public class GPSActivity extends AppCompatActivity {
    public static final String TAG = "GPAActivity";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 3, new LocationListener(){
                                    @Override
                                    public void onLocationChanged (@NonNull Location location){
                                        Log.d(TAG, "onLocationChanged: " + location);
                                    }
                                });
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Somente localização aproximada autorizada
                            } else {
                                // Nenhuma localização autorizada
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }
}