package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.models.*;
import com.jrgc.banknu.uicomponents.CurrencyField;
import com.jrgc.banknu.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AccountDetailsController {

    @FXML
    public Label accountText;

    @FXML
    public CurrencyField limitTextField;

    @FXML
    public TextField taxTextField;

    @FXML
    public Label valueLabel;

    protected BankAccount selectedAccount;


    public void setupAccount(BankAccount selectedAccount){
        this.selectedAccount = selectedAccount;
        accountText.setText(String.format("Detalhes da %s\nConta %d", selectedAccount.getAccountType(), selectedAccount.getNumber()));

        switch (selectedAccount.getAccountType()){
            case SPECIAL -> setupSpecial();
            case SAVINGS -> setupSavings();
        }
    }

    private void setupSpecial() {
        valueLabel.setText("Limite Negativo (R$):");
        limitTextField.setAmount(((SpecialBankAccount) selectedAccount).getLimit());
        limitTextField.setManaged(true);
        taxTextField.setManaged(false);
    }

    private void setupSavings() {
        valueLabel.setText("Taxa da Poupança (%):");
        taxTextField.setText(String.valueOf(((SavingsBankAccount) selectedAccount).getTax()).replace(".", ","));
        taxTextField.setManaged(true);
        limitTextField.setManaged(false);
    }

    @FXML
    public void onDetailClick() {
        switch (selectedAccount.getAccountType()){
            case SPECIAL -> onLimitClick();
            case SAVINGS -> onTaxClick();
        }
    }

    private void onLimitClick() {
        float amount = limitTextField.getAmount().floatValue();

        if (amount == 0) {
            AlertUtils.showWarning("Insira um valor maior que R$0,00");
            return;
        }

        ((SpecialBankAccount) selectedAccount).setLimit(amount);

        AlertUtils.showInformation("Limite atualizado com sucesso!");
    }

    private void onTaxClick() {
        try {
            String taxString = taxTextField.getText().replace(",", ".");
            float tax = Float.parseFloat(taxString);
            if (tax == 0) {
                AlertUtils.showWarning("Insira um valor maior que R$0,00");
                return;
            }

            ((SavingsBankAccount) selectedAccount).setTax(tax);

            AlertUtils.showInformation("Taxa de poupança com sucesso!");
        }catch (NumberFormatException numberFormatException){
            AlertUtils.showWarning("Insira um valor válido");
        }
    }
}