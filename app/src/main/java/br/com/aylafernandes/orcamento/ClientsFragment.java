package br.com.aylafernandes.orcamento;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.aylafernandes.orcamento.adapter.ClientsAdapter;
import br.com.aylafernandes.orcamento.models.Clients;


/**
 * A simple {@link Fragment} subclass.
 */

public class ClientsFragment extends Fragment {
    List<Clients> listClients =  new ArrayList <Clients>();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mclientsRef = mRootRef.child("clients");
    String TAG = "FireBase";
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;



    public ClientsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_clients, container, false);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.rv_fragment_clientes);
            mRecycler.setHasFixedSize(true);

            updateFireBase();


            return rootView;


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

    }


    private   void updateFireBase(){
        mclientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                        String name = (String) messageSnapshot.child("name").getValue();
                        String message = (String) messageSnapshot.child("cell").getValue();
                        String photoUrl = (String) messageSnapshot.child("photourl").getValue();
                        String cabelo = (String) messageSnapshot.child("cabelo").getValue();

                        //String photoUrl = "";
                        Log.w(TAG, name + " - " + message + " - " + cabelo);
                        Clients client = new Clients(name, message, photoUrl, cabelo);

                        listClients.add(client);
                }

                setUpRecyclerView(listClients);



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void setUpRecyclerView(List<Clients> listClients) {


        ClientsAdapter adapter = new ClientsAdapter(getContext(), listClients );


        mRecycler.setAdapter(adapter);






    }

}
