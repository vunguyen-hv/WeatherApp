package com.plcoding.weatherapp.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.plcoding.weatherapp.domain.location.LocationTracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class GetLocation @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
    ): LocationTracker
{
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )==PackageManager.PERMISSION_GRANTED
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(!hasAccessFineLocationPermission||!hasAccessCoarseLocationPermission||!isGpsEnabled)
        {
            Log.e("haha","fail in permition")
            return null
        }

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                Log.e("haha","fail in permition3")
                if(isComplete){
                    if(isSuccessful){
                        Log.e("haha","fail in permition1")

                        cont.resume(result)
                    }else {
                        Log.e("haha","fail in permition2")

                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                Log.e("haha","fail in permition7")
                addOnSuccessListener {
                    it?.toString()?.let { it1 -> Log.e("haha", it1) }
                    cont.resume(it)
                }
                addOnCanceledListener {
                    Log.e("haha","fail in permition4")
                    cont.cancel()
                }
                addOnFailureListener{
                    Log.e("haha","fail in permition4")
                    cont.resume(null)
                }
            }
        }
    }

}