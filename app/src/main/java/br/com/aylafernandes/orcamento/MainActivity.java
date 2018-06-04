package br.com.aylafernandes.orcamento;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.aylafernandes.orcamento.models.Client;
import br.com.aylafernandes.orcamento.models.User;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextMessage;
    private  String nome;
    User user;
    private static final int REQUEST_PERMISSOES = 1;
    private MapsFragment mapsFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    goToFragment(new HomeFragment());
                    return true;
                case R.id.navigation_profile:

                    goToFragment(new ProfileFragment());
                    return true;
                case R.id.navigation_clients:

                    goToFragment(new ClientsFragment());
                    return true;
                case R.id.navigation_settings:
                     goToFragment( new SettingsFragment());
                    return true;
            }
            return false;
        }
    };



    public void goToFragment(Fragment fragment){
        Bundle parameters = new Bundle();
        parameters.putSerializable("user", user);
        fragment.setArguments(parameters);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, fragment);
        ft.commit();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSOES) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
//                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                new UserLocation(this, mapsFragment);
//            }
            for(int i = 0; i < grantResults.length; i++){
                int resultado = grantResults[i];
                String permissao = permissions[i];

                System.out.println(permissao+ " está garantida : " + (resultado == PackageManager.PERMISSION_GRANTED) );
                if (resultado == PackageManager.PERMISSION_GRANTED){
                //    new UserLocation(this, mapsFragment);
                }

                // pode fazer sua regra de negócio
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setUser();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {

                String[] permissoes = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissoes, REQUEST_PERMISSOES);
            }
        }
    }

    private void  setUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            String uid = firebaseUser.getUid();
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            Uri photoUrl = firebaseUser.getPhotoUrl();


             user = new User(uid, name, photoUrl.toString(), email);

        }
    }

    public void selectClient(Client client, Fragment fragment) {


        Bundle parameters = new Bundle();
        parameters.putString("user",user.getUid());
        parameters.putSerializable("client", client);
        fragment.setArguments(parameters);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void goToMapsClients() {

        mapsFragment =  new MapsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, mapsFragment);
        ft.addToBackStack(null);
        ft.commit();

    }
}
