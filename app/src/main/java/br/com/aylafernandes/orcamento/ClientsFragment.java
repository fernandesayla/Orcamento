package br.com.aylafernandes.orcamento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.aylafernandes.orcamento.adapter.ClientAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsFragment extends Fragment {


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
            ClientAdapter adapter = new ClientAdapter(new String[]{"Example One", "Example Two", "Example Three", "Example Four", "Example Five" , "Example Six" , "Example Seven"});
            rv.setAdapter(adapter);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);

            return rootView;


    }

}
