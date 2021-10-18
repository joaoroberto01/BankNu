package com.jrgc.banknu;

import com.jrgc.banknu.models.BankUser;
import com.jrgc.banknu.utils.SceneManager;
import com.jrgc.banknu.utils.UserPersistUtils;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BankApplication extends javafx.application.Application {
    public static List<BankUser> bankUsers = new ArrayList<>();
    public static BankUser currentUser;

    @Override
    public void start(Stage stage) {
        SceneManager.goToLogin(stage);
        UserPersistUtils.fetchUsers();
    }

    public static void main(String[] args) {
        launch();
    }
}