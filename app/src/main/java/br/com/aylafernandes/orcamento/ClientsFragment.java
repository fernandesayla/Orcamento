package br.com.aylafernandes.orcamento;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import br.com.aylafernandes.orcamento.adapter.ClientsAdapter;
import br.com.aylafernandes.orcamento.adapter.listener.OnItemClientClickListener;
import br.com.aylafernandes.orcamento.dao.ClientDAO;
import br.com.aylafernandes.orcamento.models.Client;
import br.com.aylafernandes.orcamento.models.User;


/**
 * A simple {@link Fragment} subclass.
 */

public class ClientsFragment extends Fragment {

    List<Client> listClients;

    ClientsDelegate delegate;

    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    FloatingActionButton fab;


    public ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ClientsDelegate) getActivity();
        delegate.nameActivity("Lista de Clientes");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View rootView = inflater.inflate(R.layout.fragment_clients, container, false);
            setHasOptionsMenu(true);

            fab =  rootView.findViewById(R.id.fab);

            mRecycler = (RecyclerView) rootView.findViewById(R.id.rv_fragment_clientes);
            mRecycler.setHasFixedSize(true);


            loadClients();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delegate.lidaComClickDoFAB();

                }
            });



        setUpRecyclerView(listClients);
            return rootView;


    }

    private void loadClients() {
        ClientDAO dao = new ClientDAO(getContext());

        listClients =  dao.findAll();

        setUpRecyclerView(listClients);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_location_clients, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.location_menu_item_maps){
               delegate.goToMapa();

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



    private void setUpRecyclerView(List<Client> listClients ) {

        ClientsAdapter adapter = new ClientsAdapter(getContext(), listClients );

        mRecycler.setAdapter(adapter);

        adapter.setOnItemClientClickListener(new OnItemClientClickListener() {
            @Override
            public void onItemClick(Client client) {

                delegate.handleSelectClient(client);

            }
        });




    }



}
