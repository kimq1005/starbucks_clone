package com.example.freshairapi

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.freshairapi.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource : CancellationTokenSource? =null


    private var mainActivitybinding : ActivityMainBinding? = null
    private val binding get() = mainActivitybinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivitybinding = ActivityMainBinding.inflate(layoutInflater) // getRoot 메서드로 레이아웃 내부의 최상위 위치 뷰의 // 인스턴스를 활용하여 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)



        initVariables()
        requestLocationPermissions()    //허가 요청함수

    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted = //로케이션 접근권한이 허가되었는
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if(locationPermissionGranted){
            finish()
        } else {
            //fetchData
                cancellationTokenSource = CancellationTokenSource()

                fusedLocationProviderClient.getCurrentLocation(
                    LocationRequest.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource!!.token

            ).addOnSuccessListener { location ->


                }
        }
    }

    private fun initVariables(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }


    private fun requestLocationPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )
    }

    companion object{
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
    }
}