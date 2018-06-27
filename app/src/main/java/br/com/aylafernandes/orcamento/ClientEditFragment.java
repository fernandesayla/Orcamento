package br.com.aylafernandes.orcamento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.aylafernandes.orcamento.dao.ClientDAO;
import br.com.aylafernandes.orcamento.models.Client;


public class ClientEditFragment extends Fragment {
    private TextView  cell;
    private TextView email;
    private TextView name;
    private TextView price;
    private TextView address;
    ClientsDelegate delegate;
    Client client = new Client();


    private String TAG = "ClientDetailFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ClientsDelegate) getActivity();
        delegate.nameActivity("Cadatro de Cliente");
    }

    public ClientEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_client_edit, container, false);

        setHasOptionsMenu(true);

        getUserView(v);
        Bundle parametros = getArguments();

        if (parametros != null) {
            this.client = (Client) parametros.getSerializable("client");

           // userKey = parametros.getString("user");
            populaCamposCom(client);

        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_cliente, menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getUserView(getView());

        client.setName(name.getText().toString());
        client.setCell(cell.getText().toString());
        client.setEmail(email.getText().toString());
        client.setAddress(address.getText().toString());
        client.setPrice(price.getText().toString());


        ClientDAO dao = new ClientDAO(getContext());

        if (item.getItemId()==R.id.menu_detail_client){


            if (!client.getName().isEmpty()){


                if(client.getUid() == null)
                {
                    dao.insere(client);
                }else{
                    dao.altera(client);
                }


                Snackbar.make(getView(), "Dados do Clientes salvos", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();


                 delegate.backToMain();
            }
            else {
                Snackbar.make(getView(), "Dados do Clientes precisam estar completos", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }


        }else if(item.getItemId()==R.id.menu_detail_client_delete){
            if (client.getUid()!=null){
                dao.deleta(client);

                Snackbar.make(getView(), "Cliente deletado", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                delegate.backToMain();
            }

        }

        dao.close();



        return super.onOptionsItemSelected(item);
    }

    private void getUserView(View view) {
        cell = view.findViewById(R.id.tv_cli_detail_cell);
        email = view.findViewById(R.id.tv_cli_detail_email);

        name = view.findViewById(R.id.tv_cli_detail_name);
        address = view.findViewById(R.id.tv_cli_detail_address);
        price = view.findViewById(R.id.tv_cli_detail_price);
    }


    void populaCamposCom(Client client){

        cell.setText(client.getCell());
        email.setText(client.getEmail());
        name.setText(client.getName());
        address.setText(client.getAddress());

        price.setText(client.getPrice().toString());


    }

}
