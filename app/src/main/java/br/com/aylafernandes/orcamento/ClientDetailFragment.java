package br.com.aylafernandes.orcamento;



import android.content.Intent;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import br.com.aylafernandes.orcamento.models.Client;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientDetailFragment extends Fragment {

    private TextView cell;
    private TextView email;
    private TextView name;
    private TextView price;
    private TextView address;

    ClientsDelegate delegate;
    Client client;


    private Intent mShareIntent;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        delegate = (ClientsDelegate) getActivity();
        delegate.nameActivity("Cliente");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_client_detail, container, false);

        setHasOptionsMenu(true);

        cell = v.findViewById(R.id.t_cli_detail_cell);
        email = v.findViewById(R.id.t_cli_detail_email);

        name = v.findViewById(R.id.t_cli_detail_name);
        address = v.findViewById(R.id.t_cli_detail_address);

        price = v.findViewById(R.id.t_cli_detail_price);

        Bundle parametros = getArguments();

        if (parametros != null) {
            this.client = (Client) parametros.getSerializable("client");

            populaCamposCom(client);

        }
        ImageButton button = (ImageButton) v.findViewById(R.id.call_client);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                makePhoneCall();

            }
        });

        return v;
    }

    private void makePhoneCall(){
        try{
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:"+client.getCell()));
            startActivity(call);

        }catch (Error error){
            Snackbar.make(getView(), "Fazer ligação:"+error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit_cliente, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menu_edit_client){

            delegate.handleSelectClient(client);

        }if (item.getItemId()==R.id.menu_share_client){

            setupShareIntent();

        }

        return super.onOptionsItemSelected(item);
    }

    private void setupShareIntent() {
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        String textToShare =  "Boa tarde, "+ client.getName() +"! O valor total é de "  + client.getPrice();
        mShareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        startActivity(Intent.createChooser(mShareIntent, "Share using"));

    }


        void populaCamposCom(Client client){

        cell.setText(client.getCell());
        email.setText(client.getEmail());
        name.setText(client.getName());
        price.setText(client.getPrice().toString());
        address.setText(client.getAddress());




    }

}
