package br.com.aylafernandes.orcamento;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.aylafernandes.orcamento.adapter.ClientsAdapter;
import br.com.aylafernandes.orcamento.adapter.listener.OnItemClientClickListener;
import br.com.aylafernandes.orcamento.dao.ClientDao;
import br.com.aylafernandes.orcamento.models.Client;
import br.com.aylafernandes.orcamento.models.User;


/**
 * A simple {@link Fragment} subclass.
 */

public class ClientsFragment extends Fragment {
    List<Client> listClients =  new ArrayList <Client>();
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mclientsRef;
    String TAG = "FireBase";
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    FloatingActionButton fab;


    public ClientsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
            setHasOptionsMenu(true);
            fab =  rootView.findViewById(R.id.fab);
            mRecycler = (RecyclerView) rootView.findViewById(R.id.rv_fragment_clientes);
            mRecycler.setHasFixedSize(true);

            Bundle parametros = getArguments();
            User user = (User) parametros.getSerializable("user");

            mclientsRef = mRootRef.child("users").child(user.getUid()).child("clients");


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.selectClient(new Client(), new ClientEditFragment());


                }
            });

            updateFireBase(user);

        setUpRecyclerView(listClients);
            return rootView;


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_location_clients, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.location_menu_item_maps){
                 MainActivity mainActivity = (MainActivity) getActivity();
            ArrayList<String> address = new ArrayList <>();
            for (Client client : listClients){
                    Log.d(TAG, client.getAddress());
                    address.add(client.getAddress());
                 }

            mainActivity.goToMapsClients(address);

        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

    }


    private   void updateFireBase(User user){

        DatabaseReference mclientsRef = mRootRef.child("users").child(user.getUid()).child("clients");
        mclientsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listClients = new ArrayList <Client>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String clientId = (String) messageSnapshot.getKey();
                    String address = (String) messageSnapshot.child("address").getValue();
                    String name = (String) messageSnapshot.child("name").getValue();
                    String cell = (String) messageSnapshot.child("cell").getValue();
                    String email = (String) messageSnapshot.child("email").getValue();
                    String photoUrl = (String) messageSnapshot.child("photourl").getValue();
                    String hair = (String) messageSnapshot.child("hair").getValue();

                    //String photoUrl = "";
                    Log.w(TAG, name + " - " + cell + " - " + hair + " - " + clientId);
                    Client client = new Client(clientId, name, cell, email, photoUrl, hair, address);


                    listClients.add(client);
                }

                setUpRecyclerView(listClients);



            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void setUpRecyclerView(List<Client> listClients ) {


        ClientsAdapter adapter = new ClientsAdapter(getContext(), listClients );


        mRecycler.setAdapter(adapter);

        adapter.setOnItemClientClickListener(new OnItemClientClickListener() {
            @Override
            public void onItemClick(Client client) {


                MainActivity mainActivity = (MainActivity) getActivity();

                mainActivity.selectClient(client, new ClientDetailFragment());

            }
        });




    }



}
