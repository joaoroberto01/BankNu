package com.jrgc.banknu.controllers;

import com.jrgc.banknu.models.*;
import com.jrgc.banknu.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class BankStatementController {
    @FXML
    public TableView<BankStatementItem> bankStatementTableView;

    @FXML
    public TableColumn<BankStatementItem, String> dateColumn;

    @FXML
    public TableColumn<BankStatementItem, String> operationCol;

    @FXML
    public TableColumn<BankStatementItem, Float> amountCol;

    @FXML
    public Text balanceText;

    @FXML
    public Text detailText;

    @FXML
    public Label titleLabel;

    protected BankAccount selectedAccount;

    @FXML
    public void initialize(){
        System.out.println("BankStatement initialize");
        setupColumns();
        bankStatementTableView.setSelectionModel(null);
    }

    public void setupAccount(BankAccount selectedAccount){
        this.selectedAccount = selectedAccount;
        titleLabel.setText(String.format("Extrato - Conta %d", selectedAccount.getNumber()));
        updateTable();
    }

    protected void updateTable() {
        if (selectedAccount == null)
            return;

        bankStatementTableView.getItems().setAll(selectedAccount.getBankStatement());

        balanceText.setText("Saldo: " + Utils.getCurrencyFormatted(selectedAccount.getBalance()));

        detailText.setVisible(!(selectedAccount instanceof SimpleBankAccount));
        if (selectedAccount instanceof SpecialBankAccount)
            detailText.setText("Limite Negativo: " + Utils.getCurrencyFormatted((float) ((SpecialBankAccount) selectedAccount).getLimit()));
        else if(selectedAccount instanceof SavingsBankAccount)
            detailText.setText(String.format("Taxa de Poupança: %.2f%%", ((SavingsBankAccount) selectedAccount).getTax()));
    }

    private void setupColumns() {
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankStatementItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BankStatementItem, String> param) {
                return new SimpleStringProperty(param.getValue().getDateTime());
            }
        });

        operationCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankStatementItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BankStatementItem, String> param) {
                return new SimpleStringProperty(param.getValue().getOperation().toString());
            }
        });

        amountCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankStatementItem, Float>, ObservableValue<Float>>() {
            @Override
            public ObservableValue<Float> call(TableColumn.CellDataFeatures<BankStatementItem, Float> param) {
                float amount = param.getValue().getAmount();
                if (param.getValue().getOperation() == BankStatementItem.BankOperation.WITHDRAW)
                    amount *= -1;
                return new SimpleObjectProperty<>(amount);
            }
        });

        amountCol.setCellFactory(new Callback<TableColumn<BankStatementItem, Float>, TableCell<BankStatementItem, Float>>() {
            @Override
            public TableCell<BankStatementItem, Float> call(TableColumn<BankStatementItem, Float> param) {
                return new TableCell<>(){
                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            return;
                        }

                        Paint paint = item < 0 ? Color.RED : Color.GREEN;

                        Label label = new Label(Utils.getCurrencyFormatted(item));
                        label.setTextFill(paint);

                        HBox hBox = new HBox();
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.getChildren().add(label);

                        setGraphic(hBox);
                    }
                };
            }
        });
    }
}