package br.com.aylafernandes.orcamento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.aylafernandes.orcamento.adapter.ClientAdapter;
import br.com.aylafernandes.orcamento.adapter.ClientsAdapter;
import br.com.aylafernandes.orcamento.models.Clients;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsFragment extends Fragment {
    List<Clients> listClients = Collections.emptyList();
    private DatabaseReference mDatabase;

    public ClientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_clients, container, false);


            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_fragment_clientes);


            rv.setHasFixedSize(true);
            ClientsAdapter adapter = new ClientsAdapter(getContext(), criaBase());


           // ClientAdapter adapter = new ClientAdapter(new String[]{"Example One", "Example Two", "Example Three", "Example Four", "Example Five" , "Example Six" , "Example Seven"});
            rv.setAdapter(adapter);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);

            return rootView;


    }

    private List<Clients> criaBase(){

        Clients provaPortugues = new Clients("Portugues", "25/05/2016" );


        Clients provaMatematica = new Clients("Matematica", "27/05/2016");

        List<Clients> clients = Arrays.asList(provaPortugues, provaMatematica);

        return clients;
    }

}
