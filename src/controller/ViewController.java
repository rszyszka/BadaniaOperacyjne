package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewController {

    @FXML
    TextField recipent1;

    @FXML
    TextField recipent2;

    @FXML
    TextField recipent3;

    @FXML
    TextField supplier1;

    @FXML
    TextField supplier2;

    @FXML
    TextField supplier3;

    @FXML
    TextField unitCost1;

    @FXML
    TextField unitCost2;

    @FXML
    TextField unitCost3;

    @FXML
    TextField unitCost4;

    @FXML
    TextField unitCost5;

    @FXML
    TextField unitCost6;

    @FXML
    TextField unitCost7;

    @FXML
    TextField unitCost8;

    @FXML
    TextField unitCost9;

    @FXML
    void initialize() {
        recipent1.setText("20");
        recipent2.setText("40");
        recipent3.setText("40");

        supplier1.setText("32");
        supplier2.setText("19");
        supplier3.setText("27");

        unitCost1.setText("1");
        unitCost2.setText("4");
        unitCost3.setText("3");
        unitCost4.setText("2");
        unitCost5.setText("5");
        unitCost6.setText("1");
        unitCost7.setText("2");
        unitCost8.setText("6");
        unitCost9.setText("5");

    }
}
