package br.com.aylafernandes.orcamento.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import br.com.aylafernandes.orcamento.R;
import br.com.aylafernandes.orcamento.adapter.listener.OnItemClientClickListener;
import br.com.aylafernandes.orcamento.models.Client;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder> {

    private final List<Client> clients;
    private final Context context;


    private OnItemClientClickListener onItemClientClickListener;

    public void setOnItemClientClickListener(OnItemClientClickListener onItemClientClickListener) {
        this.onItemClientClickListener = onItemClientClickListener;
    }


    public ClientsAdapter(Context context, List<Client> clients){
        this.context = context;
        this.clients = clients;
    }

    @Override
    public  ClientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.fragment_card_client_item, parent, false);
        return new ClientsViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ClientsViewHolder holder, int position) {
        Client nota = clients.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }



    public void adiciona(Client nota){
        clients.add(nota);
        notifyDataSetChanged();
    }

    public class ClientsViewHolder extends RecyclerView.ViewHolder   {
        private final TextView name;
        private final TextView cell;
        private final TextView email;
        private final TextView price;
        public CardView mCardView;

        Client client;


        public ClientsViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_card_item_name);
            cell = itemView.findViewById(R.id.tv_cell);
            email = itemView.findViewById(R.id.tv_email);
            price =  itemView.findViewById(R.id.tv_price);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onItemClientClickListener.onItemClick(client);
                }
            });
        }

        public void vincula(Client client){

            this.client = client;
            preencheCampo(client);
        }

        private void preencheCampo(Client client) {
            name.setText(client.getName());
            cell.setText(client.getCell());
            email.setText(client.getEmail());
            price.setText(client.getPrice().toString());


        }


    }
}