package br.com.aylafernandes.orcamento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import static com.google.android.gms.location.LocationServices.*;


public class UserLocation  implements GoogleApiClient.ConnectionCallbacks, LocationListener{

    private final GoogleApiClient user;

    MapsFragment mapsFragment;

    public UserLocation(Context context, MapsFragment mapsFragment) {


        user = new GoogleApiClient.Builder(context)
                .addApi(API)
                .addConnectionCallbacks(this)
                .build();

        user.connect();



        this.mapsFragment = mapsFragment;
    }




    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = new LocationRequest();
        request.setSmallestDisplacement(50);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        FusedLocationApi.requestLocationUpdates(user, request, this);


    }


    @Override
    public void onConnectionSuspended(int i) {
        FusedLocationApi.removeLocationUpdates(user, this);
        this.user.disconnect();

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coordenada = new LatLng(location.getLatitude(), location.getLongitude());
        mapsFragment.centralizaEm(coordenada);


    }
}
