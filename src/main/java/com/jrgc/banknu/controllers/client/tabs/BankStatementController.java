package com.jrgc.banknu.controllers.client.tabs;

import com.jrgc.banknu.controllers.client.ClientController;
import com.jrgc.banknu.models.BankStatementItem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Predicate;

public class BankStatementController {

//    @FXML
//    public ListView<BankStatementItem> bankStatementListView;
    @FXML
    public TableView<BankStatementItem> bankStatementTableView;

    @FXML
    public TableColumn<BankStatementItem, String> dateColumn;

    @FXML
    public TableColumn<BankStatementItem, String> operationCol;

    @FXML
    public TableColumn<BankStatementItem, Float> amountCol;

    @FXML
    public void initialize(){
        System.out.println("BankStatement initialize");
        setupColumns();
    }

    private void setupColumns() {
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankStatementItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BankStatementItem, String> param) {
                return new SimpleStringProperty(param.getValue().getDate());
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

    public void onRefresh() {

        bankStatementTableView.getItems().setAll(ClientController.bankStatement.filtered(new Predicate<BankStatementItem>() {
            @Override
            public boolean test(BankStatementItem bankStatementItem) {
                return true;
            }
        }));
        for (BankStatementItem b : ClientController.bankStatement)
            System.out.println(b.getOperation());
    }
}