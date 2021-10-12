package com.jrgc.banknu.controllers.client;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.client.tabs.ClientBankStatementController;
import com.jrgc.banknu.controllers.client.tabs.ClientDepositController;
import com.jrgc.banknu.controllers.client.tabs.MyAccountsController;
import com.jrgc.banknu.controllers.client.tabs.ClientWithdrawController;
import com.jrgc.banknu.models.BankUser;
import com.jrgc.banknu.models.Client;
import com.jrgc.banknu.models.Manager;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

import java.util.function.UnaryOperator;

public class ClientController implements InvalidationListener {

    @FXML
    public MyAccountsController myAccountsController;

    @FXML
    public ClientWithdrawController withdrawController;

    @FXML
    public ClientDepositController depositController;

    @FXML
    public ClientBankStatementController bankStatementController;

    @FXML
    public TabPane tabPane;

    private Client currentClient;

    @FXML
    protected void initialize(){
        currentClient = (Client) BankApplication.currentUser;
        tabPane.getSelectionModel().select(0);

        currentClient.getBankAccounts().addListener(this);
        setTabsEnabled();
    }

    @FXML
    public void onTabRefresh(){
        myAccountsController.onRefresh();
        withdrawController.onRefresh();
        depositController.onRefresh();
        bankStatementController.onRefresh();
    }

    public void setTabsEnabled(){
        boolean emptyList = currentClient.getBankAccounts().isEmpty();
        for (int i = 2; i < tabPane.getTabs().size(); i++)
            tabPane.getTabs().get(i).setDisable(emptyList);
    }

    //Accounts List changed
    @Override
    public void invalidated(Observable observable) {
        System.out.println("Accounts changed");

        for (BankUser bankUser : BankApplication.bankUsers) {
            if (bankUser instanceof Manager) {
                ((Manager) bankUser).getClients().replaceAll(new UnaryOperator<Client>() {
                    @Override
                    public Client apply(Client client) {
                        if (client.getUsername().equals(currentClient.getUsername()))
                            client.setBankAccounts(currentClient.getBankAccounts());
                        return client;
                    }
                });
            }
        }

        setTabsEnabled();
    }
}