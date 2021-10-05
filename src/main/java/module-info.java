module com.jrgc.banknu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.jrgc.banknu.models to com.google.gson;
    exports com.jrgc.banknu.models;

    opens com.jrgc.banknu to javafx.fxml;
    exports com.jrgc.banknu;
    exports com.jrgc.banknu.utils;
    opens com.jrgc.banknu.utils to javafx.fxml;
    exports com.jrgc.banknu.controllers;
    opens com.jrgc.banknu.controllers to javafx.fxml;
    exports com.jrgc.banknu.controllers.client;
    opens com.jrgc.banknu.controllers.client to javafx.fxml;
    exports com.jrgc.banknu.controllers.manager;
    opens com.jrgc.banknu.controllers.manager to javafx.fxml;
    exports com.jrgc.banknu.controllers.client.tabs;
    opens com.jrgc.banknu.controllers.client.tabs to javafx.fxml;
    exports com.jrgc.banknu.uicomponents;
    opens com.jrgc.banknu.uicomponents to javafx.fxml;
}