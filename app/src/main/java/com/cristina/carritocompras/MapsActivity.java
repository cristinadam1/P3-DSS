package com.cristina.carritocompras;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gps;
    private LocationManager locManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static class Store {
        final String name;
        final LatLng location;

        Store(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }

    private final List<Store> stores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gps = new GPSTracker(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Almacenes
        stores.add(new Store("Almacén Central", new LatLng(40.416775, -3.703790))); // Madrid
        stores.add(new Store("Almacén Norte", new LatLng(43.362344, -5.849413)));   // Oviedo
        stores.add(new Store("Almacén Sur", new LatLng(37.389092, -5.984459)));     // Sevilla
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        locManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            Location location = gps.getLocation();
            
            if(location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng actual = new LatLng(latitude, longitude);

                Marker miposicion = mMap.addMarker(new MarkerOptions()
                        .position(actual)
                        .snippet("Mi posición actual"));

                mMap.moveCamera(CameraUpdateFactory.newLatLng(actual));
                
                if(miposicion != null) {
                    miposicion.showInfoWindow();
                }

                CameraPosition campos = new CameraPosition.Builder()
                        .target(actual)
                        .zoom(16)
                        .bearing(45)
                        .build();
                
                CameraUpdate camUp13 = CameraUpdateFactory.newCameraPosition(campos);
                mMap.animateCamera(camUp13);
            } else {
                Toast.makeText(getApplicationContext(), "No se pudo obtener la ubicación. Revisa el GPS.", Toast.LENGTH_LONG).show();
            }

            for (Store store : stores) {
                mMap.addMarker(new MarkerOptions().position(store.location).title(store.name));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}