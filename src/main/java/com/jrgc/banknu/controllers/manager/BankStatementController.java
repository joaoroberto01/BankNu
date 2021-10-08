package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.models.BankAccount;
import com.jrgc.banknu.models.BankStatementItem;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.NumberFormat;
import java.util.Locale;

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
    public ChoiceBox<BankAccount> accountsChoiceBox;

    @FXML
    public Text balanceText;

    @FXML
    public Label titleLabel;

    private BankAccount selectedAccount;

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

    private void updateTable() {
        if (selectedAccount == null)
            return;

        bankStatementTableView.getItems().setAll(selectedAccount.getBankStatement());

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        balanceText.setText("Saldo: " + numberFormat.format(selectedAccount.getBalance()));
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

                        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
                        Label label = new Label(numberFormat.format(item));
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