package com.example.home_zone_checker

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    // ── UI references (Member 3) ──────────────────────────────────────────────
    private lateinit var btnCheck: Button
    private lateinit var tvResult: TextView
    private lateinit var tvStatusIcon: TextView
    private lateinit var tvDistance: TextView
    private lateinit var tvDistanceUnit: TextView
    private lateinit var tvRefLat: TextView
    private lateinit var tvRefLng: TextView
    private lateinit var tvRadius: TextView
    private lateinit var tvMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Fused Location Provider (Member 1)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // ── Bind UI views (Member 3) ──────────────────────────────────────────
        btnCheck       = findViewById(R.id.btn_check)
        tvResult       = findViewById(R.id.tv_result)
        tvStatusIcon   = findViewById(R.id.tv_status_icon)
        tvDistance     = findViewById(R.id.tv_distance)
        tvDistanceUnit = findViewById(R.id.tv_distance_unit)
        tvRefLat       = findViewById(R.id.tv_ref_lat)
        tvRefLng       = findViewById(R.id.tv_ref_lng)
        tvRadius       = findViewById(R.id.tv_radius)
        tvMessage      = findViewById(R.id.tv_message)

        // Populate static zone configuration values from ZoneConfig (Member 2)
        tvRefLat.text = ZoneConfig.REFERENCE_LAT.toString()
        tvRefLng.text = ZoneConfig.REFERENCE_LNG.toString()
        tvRadius.text = "${ZoneConfig.RADIUS_METERS.toInt()} m"

        // Basic trigger for Member 1's logic
        btnCheck.setOnClickListener {
            hideMessage()
            checkLocationPermissions()
        }
    }

    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        // Fused Location Provider implementation (Member 1)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Hand-off to Member 2: Calculate Zone Logic
                    onLocationReceived(location)
                } else {
                    showMessage(getString(R.string.msg_location_unavailable))
                }
            }
        }
    }

    private fun onLocationReceived(location: Location) {
        // ── Zone calculation delegated to Member 2's ZoneChecker (Member 3 UI hook) ──
        val result: ZoneResult = ZoneChecker.checkZone(location.latitude, location.longitude)
        displayZoneResult(result)
    }

    // ── UI update helper (Member 3) ───────────────────────────────────────────
    private fun displayZoneResult(result: ZoneResult) {
        if (result.isInside) {
            tvStatusIcon.text = "✅"
            tvResult.text     = getString(R.string.status_inside)
            tvResult.setTextColor(Color.parseColor("#16A34A"))   // accent_green
        } else {
            tvStatusIcon.text = "🚫"
            tvResult.text     = getString(R.string.status_outside)
            tvResult.setTextColor(Color.parseColor("#DC2626"))   // accent_red
        }
        tvDistance.text = "%.1f".format(result.distanceMeters)
        tvDistanceUnit.visibility = View.VISIBLE
    }

    private fun showMessage(msg: String) {
        tvMessage.text = msg
        tvMessage.visibility = View.VISIBLE
    }

    private fun hideMessage() {
        tvMessage.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else {
                showMessage(getString(R.string.msg_permission_denied))
            }
        }
    }
}