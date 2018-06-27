package br.com.aylafernandes.orcamento;


import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.IOException;
import java.util.List;
import br.com.aylafernandes.orcamento.dao.ClientDAO;
import br.com.aylafernandes.orcamento.models.Client;
import br.com.aylafernandes.orcamento.models.User;


public class MapsFragment extends SupportMapFragment  implements OnMapReadyCallback  {


    private MapsFragment mapsFragment;

    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mclientsRef;
    private GoogleMap googleMap;
    User user;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ClientsDelegate delegate = (ClientsDelegate) getActivity();
        delegate.nameActivity(getResources().getString(R.string.title_maps));


        Bundle parametros = getArguments();
        if (parametros!=null){
            user = (User) parametros.getSerializable("user");
        }


        getMapAsync(this);
    }


    private LatLng getGeoLocation(String location) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (!addresses.isEmpty()){
                LatLng posicao = new LatLng( addresses.get(0).getLatitude(),addresses.get(0).getLongitude() );
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        addMarkersToMap(googleMap);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        new UserLocation(getContext(), this);
    }

    private void addMarkersToMap(final GoogleMap googleMap) {

        ClientDAO dao = new ClientDAO(getContext());
        List<Client> listClients  = dao.findAll();
                for (Client client: listClients) {
                    if (client.getAddress()!=null){
                        googleMap.addMarker(new MarkerOptions().position(getGeoLocation(client.getAddress())).title(client.getName()));
                    }
                }
    }
    public void centralizaEm(LatLng coordenada) {
        if (googleMap != null) {
            if (coordenada==null)
                return;
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            googleMap.moveCamera(update);
        }
    }
}
