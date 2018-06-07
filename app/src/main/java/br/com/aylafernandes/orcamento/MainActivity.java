package br.com.aylafernandes.orcamento;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity implements ClientsDelegate {

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

                case R.id.navigation_profile:

                    Bundle parameters = new Bundle();
                    ProfileFragment fragment = new ProfileFragment();

                    parameters.putSerializable("user", user);

                    fragment.setArguments(parameters);
                    goToFragment(fragment, false);
                    return true;
                case R.id.navigation_clients:

                    goToFragment(new ClientsFragment(), false);
                    return true;
                case R.id.navigation_settings:
                     goToFragment( new SettingsFragment(), false);
                    return true;
            }
            return false;
        }
    };





    public void goToFragment(Fragment fragment, Boolean empilha){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (empilha){
            ft.addToBackStack(null);
        }

        ft.replace(R.id.flMain, fragment);
        ft.commit();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSOES) {

            for(int i = 0; i < grantResults.length; i++){
                int resultado = grantResults[i];
                String permissao = permissions[i];

                System.out.println(permissao+ " está garantida : " + (resultado == PackageManager.PERMISSION_GRANTED) );
                if (resultado == PackageManager.PERMISSION_GRANTED){

                }


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
        navigation.setSelectedItemId(0);


        setUser();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {

                String[] permissoes = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissoes, REQUEST_PERMISSOES);
            }
        }

        goToFragment(new ClientsFragment(), true);
    }

    private void  setUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            String uid = firebaseUser.getUid();
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            Uri photoUrl = firebaseUser.getPhotoUrl();

            if (photoUrl==null){
                photoUrl = Uri.parse("https://cbsnews2.cbsistatic.com/hub/i/r/2017/05/11/759f70c0-804e-4afa-8493-f80fc1b09ab3/thumbnail/620x350/f1625b916682d938a132af464524193a/istock-526142422.jpg");
            }
             user = new User(uid, name, photoUrl.toString(), email);

        }
    }



    @Override
    public void lidaComClickDoFAB() {


        goToFragment(new ClientEditFragment(), true);
    }

    @Override
    public void goToMapa() {
        Bundle parameters = new Bundle();
        MapsFragment fragment = new MapsFragment();


        fragment.setArguments(parameters);


        parameters.putSerializable("user", user);

        fragment.setArguments(parameters);

        goToFragment(fragment, true);
    }


    @Override
    public void backToMain() {
        goToFragment(new ClientsFragment(), false);
    }

    @Override
    public void handleSelectClient(Client client) {

        Bundle parameters = new Bundle();

        parameters.putSerializable("client", client);
        ClientEditFragment fragment = new ClientEditFragment();

        fragment.setArguments(parameters);


        goToFragment(fragment, true);
    }

    @Override
    public void nameActivity(String name) {
        setTitle(name);
    }


}
