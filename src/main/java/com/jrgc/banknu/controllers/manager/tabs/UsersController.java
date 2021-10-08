package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.manager.ManagerController;
import com.jrgc.banknu.controllers.manager.NewUserController;
import com.jrgc.banknu.models.*;
import com.jrgc.banknu.utils.UserPersist;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class UsersController {

    @FXML
    public TableView<BankUser> usersTableView;

    @FXML
    public TableColumn<BankUser, String> userColumn;

    @FXML
    public TableColumn<BankUser, String> typeColumn;

    @FXML
    private Label emptyListText;

    @FXML
    public void initialize(){
        System.out.println("MyAccount initialize");
        refresh();
        setupColumns();
        checkListVisibility();
    }

    private void checkListVisibility() {
        boolean emptyAccounts = BankApplication.bankUsers.isEmpty();
        usersTableView.setManaged(!emptyAccounts);
        emptyListText.setManaged(emptyAccounts);
    }

    public void refresh() {
        usersTableView.getItems().setAll(BankApplication.bankUsers);
    }

    @FXML
    public void onMenuClientClick(){
        createNewUser(BankUser.UserType.CLIENT);
    }

    @FXML
    public void onMenuManagerClick(){
        createNewUser(BankUser.UserType.MANAGER);
    }

    private void createNewUser(BankUser.UserType userType){
        BankUser bankUser = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(BankApplication.class.getResource("manager/tabs/new-user-view.fxml"));
            Parent parent = fxmlLoader.load();
            NewUserController newUserController = fxmlLoader.getController();
            newUserController.setUserType(userType);

            Scene scene = new Scene(parent, 320, 250);
            Stage stage = new Stage();
            stage.setTitle("Novo Usuário");
            stage.setScene(scene);
            stage.showAndWait();

            bankUser = newUserController.getNewUser();
        }catch (IOException e){
            e.printStackTrace();
        }
        if (bankUser == null)
            return;

        usersTableView.getItems().add(bankUser);
        checkListVisibility();
    }

    private void delete(){
        BankUser deleteUser = usersTableView.getSelectionModel().getSelectedItem();
        BankApplication.bankUsers.remove(deleteUser);

        for (BankUser bankUser : BankApplication.bankUsers){
            if (bankUser instanceof Manager)
                ((Manager) bankUser).getClients().removeIf(client -> client.getUsername().equals(deleteUser.getUsername()));
        }

        refresh();
        UserPersist.storeUsers();
    }

    private void setupColumns() {
        final ContextMenu menu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Remover");
        deleteItem.setOnAction(event -> delete());
        menu.getItems().add(deleteItem);
        usersTableView.setContextMenu(menu);

        userColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankUser, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BankUser, String> param) {
                return new SimpleStringProperty(param.getValue().getUsername());
            }
        });

        typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankUser, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BankUser, String> param) {
                return new SimpleStringProperty(param.getValue().getUsertype().toString());
            }
        });
    }
}