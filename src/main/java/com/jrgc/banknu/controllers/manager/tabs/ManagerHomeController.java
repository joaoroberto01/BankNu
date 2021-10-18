package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.BankApplication;
import com.jrgc.banknu.controllers.HomeController;
import com.jrgc.banknu.utils.Utils;
import javafx.fxml.FXML;

public class ManagerHomeController extends HomeController {

    @FXML
    public void initialize(){
        welcomeText.setText(String.format("%s, gerente %s!", Utils.getGreeting(), BankApplication.currentUser.getUsername()));
    }

}