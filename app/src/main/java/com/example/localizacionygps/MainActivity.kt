package com.example.localizacionygps

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    companion object{
        const val PERMISSION_ID = 33
    }
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var tvLatitude: TextView
    lateinit var tvLongitude: TextView
    lateinit var btnLocate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        btnLocate = findViewById(R.id.btnLocate)
        btnLocate.setOnClickListener{
            getLocation()
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkGranted(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(this, permission) ==
                PackageManager.PERMISSION_GRANTED
    }
    private fun checkPermissions() =
        checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                checkGranted(Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                    LocationManager.NETWORK_PROVIDER)
    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                    tvLatitude.text = location?.latitude.toString()
                    tvLongitude.text = location?.longitude.toString()
                }
            }
        } else{
            requestPermissions()
        }
    }
}
