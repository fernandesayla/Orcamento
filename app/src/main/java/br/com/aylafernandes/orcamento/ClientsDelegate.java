package br.com.aylafernandes.orcamento;

import br.com.aylafernandes.orcamento.models.Client;

public interface ClientsDelegate {

    void lidaComClickDoFAB();
//    void  makeACall();

    void goToMapa();
    void backToMain();
    void handleSelectClientDetail(Client client);

    void handleSelectClient(Client client);
    void nameActivity(String name);


}
