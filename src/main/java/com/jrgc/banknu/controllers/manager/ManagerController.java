package com.jrgc.banknu.controllers.manager;

import com.jrgc.banknu.controllers.manager.tabs.MyClientsController;
import javafx.fxml.FXML;

public class ManagerController {

    @FXML
    public MyClientsController myClientsController;

    @FXML
    public void onTabRefresh(){
        myClientsController.onRefresh();
    }
}