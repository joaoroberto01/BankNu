package com.jrgc.banknu.controllers.manager.tabs;

import com.jrgc.banknu.controllers.HomeController;
import com.jrgc.banknu.utils.Utils;
import javafx.fxml.FXML;

public class ManagerHomeController extends HomeController {

    @FXML
    public void initialize(){
        welcomeText.setText(Utils.getGreeting() + ", gerente");
    }

}