package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.UnsolvableException;
import model.TransportProblem;

public class ViewController {

    // ChoiceBox
    @FXML
    ChoiceBox<String> recipientsNumber;

    @FXML
    ChoiceBox<String> suppliersNumber;

    // Recipients
    @FXML
    TextField recipient1;

    @FXML
    TextField recipient2;

    @FXML
    TextField recipient3;

    @FXML
    TextField recipient4;


    // Suppliers
    @FXML
    TextField supplier1;

    @FXML
    TextField supplier2;

    @FXML
    TextField supplier3;

    @FXML
    TextField supplier4;


    // Unit costs
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
    TextField unitCost10;

    @FXML
    TextField unitCost11;

    @FXML
    TextField unitCost12;

    @FXML
    TextField unitCost13;

    @FXML
    TextField unitCost14;

    @FXML
    TextField unitCost15;

    @FXML
    TextField unitCost16;

    @FXML
    TextArea solutionTextArea;

    @FXML
    void initialize() {

        recipientsNumber.getItems().addAll("2", "3", "4");
        suppliersNumber.getItems().addAll("2", "3", "4");

        recipientsNumber.setValue("4");
        suppliersNumber.setValue("4");

        recipient1.setText("20");
        recipient2.setText("40");
        recipient3.setText("80");
        recipient4.setText("50");

        supplier1.setText("30");
        supplier2.setText("40");
        supplier3.setText("60");
        supplier4.setText("60");

        unitCost1.setText("1");
        unitCost2.setText("4");
        unitCost3.setText("3");
        unitCost4.setText("8");

        unitCost5.setText("2");
        unitCost6.setText("5");
        unitCost7.setText("1");
        unitCost8.setText("2");

        unitCost9.setText("2");
        unitCost10.setText("6");
        unitCost11.setText("5");
        unitCost12.setText("9");

        unitCost9.setText("2");
        unitCost10.setText("6");
        unitCost11.setText("5");
        unitCost12.setText("9");

        unitCost13.setText("5");
        unitCost14.setText("8");
        unitCost15.setText("6");
        unitCost16.setText("1");

        recipientsNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case "2":
                    recipient3.setVisible(false);
                    recipient4.setVisible(false);
                    unitCost3.setVisible(false);
                    unitCost4.setVisible(false);
                    unitCost7.setVisible(false);
                    unitCost8.setVisible(false);
                    unitCost11.setVisible(false);
                    unitCost12.setVisible(false);
                    unitCost15.setVisible(false);
                    unitCost16.setVisible(false);
                    break;
                case "3":
                    recipient3.setVisible(true);
                    recipient4.setVisible(false);
                    unitCost3.setVisible(true);
                    unitCost4.setVisible(false);
                    unitCost7.setVisible(true);
                    unitCost8.setVisible(false);
                    if(suppliersNumber.getValue().equals("3"))
                        unitCost11.setVisible(true);
                    unitCost12.setVisible(false);
                    if(suppliersNumber.getValue().equals("4")) {
                        unitCost11.setVisible(true);
                        unitCost15.setVisible(true);
                    }
                    unitCost16.setVisible(false);
                    break;
                case "4":
                    recipient3.setVisible(true);
                    recipient4.setVisible(true);
                    unitCost3.setVisible(true);
                    unitCost4.setVisible(true);
                    unitCost7.setVisible(true);
                    unitCost8.setVisible(true);
                    if(suppliersNumber.getValue().equals("3")) {
                        unitCost11.setVisible(true);
                        unitCost15.setVisible(true);
                    }
                    else if(suppliersNumber.getValue().equals("4")) {
                        unitCost11.setVisible(true);
                        unitCost12.setVisible(true);
                        unitCost15.setVisible(true);
                        unitCost16.setVisible(true);
                    }
                    break;
            }
        });

        suppliersNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue){
                case "2":
                    supplier3.setVisible(false);
                    supplier4.setVisible(false);
                    unitCost9.setVisible(false);
                    unitCost10.setVisible(false);
                    unitCost11.setVisible(false);
                    unitCost12.setVisible(false);
                    unitCost13.setVisible(false);
                    unitCost14.setVisible(false);
                    unitCost15.setVisible(false);
                    unitCost16.setVisible(false);
                    break;
                case "3":
                    supplier3.setVisible(true);
                    supplier4.setVisible(false);
                    unitCost9.setVisible(true);
                    unitCost10.setVisible(true);
                    if(recipientsNumber.getValue().equals("3")) {
                        unitCost11.setVisible(true);
                    }
                    if(recipientsNumber.getValue().equals("4")) {
                        unitCost11.setVisible(true);
                        unitCost12.setVisible(true);
                    }
                    unitCost13.setVisible(false);
                    unitCost14.setVisible(false);
                    unitCost15.setVisible(false);
                    unitCost16.setVisible(false);
                    break;
                case "4":
                    supplier3.setVisible(true);
                    supplier4.setVisible(true);
                    unitCost9.setVisible(true);
                    unitCost10.setVisible(true);
                    unitCost13.setVisible(true);
                    unitCost14.setVisible(true);
                    if(recipientsNumber.getValue().equals("3")) {
                        unitCost11.setVisible(true);
                        unitCost15.setVisible(true);
                    }
                    else if(recipientsNumber.getValue().equals("4")) {
                        unitCost11.setVisible(true);
                        unitCost12.setVisible(true);
                        unitCost15.setVisible(true);
                        unitCost16.setVisible(true);
                    }
                    break;
            }
        });

    }

    public void findSolutionAction() {

        solutionTextArea.clear();

        int recipientsNum = Integer.parseInt(recipientsNumber.getValue());
        int suppliersNum = Integer.parseInt(suppliersNumber.getValue());

        int stepCounter = 1;

        double[] recipients = {
                Integer.parseInt(recipient1.getText()),
                Integer.parseInt(recipient2.getText()),
                Integer.parseInt(recipient3.getText()),
                Integer.parseInt(recipient4.getText())
        };

        double[] suppliers = {
                Integer.parseInt(supplier1.getText()),
                Integer.parseInt(supplier2.getText()),
                Integer.parseInt(supplier3.getText()),
                Integer.parseInt(supplier4.getText())
        };

        double[][] unit = {
                {
                        Integer.parseInt(unitCost1.getText()),
                        Integer.parseInt(unitCost2.getText()),
                        Integer.parseInt(unitCost3.getText()),
                        Integer.parseInt(unitCost4.getText())
                },
                {
                        Integer.parseInt(unitCost5.getText()),
                        Integer.parseInt(unitCost6.getText()),
                        Integer.parseInt(unitCost7.getText()),
                        Integer.parseInt(unitCost8.getText())
                },
                {
                        Integer.parseInt(unitCost9.getText()),
                        Integer.parseInt(unitCost10.getText()),
                        Integer.parseInt(unitCost11.getText()),
                        Integer.parseInt(unitCost12.getText())
                },
                {
                        Integer.parseInt(unitCost13.getText()),
                        Integer.parseInt(unitCost14.getText()),
                        Integer.parseInt(unitCost15.getText()),
                        Integer.parseInt(unitCost16.getText()),
                }
        };


        double[] unitDemand = new double[recipientsNum];
        for (int i = 0; i < recipientsNum; i++) {
            unitDemand[i] = recipients[i];
        }

        double[] unitSupply = new double[suppliersNum];
        for (int i = 0; i < suppliersNum; i++) {
            unitSupply[i] = suppliers[i];
        }

        double[][] unitCost = new double[suppliersNum][recipientsNum];
        for (int i = 0; i < suppliersNum; i++) {
            for (int j = 0; j < recipientsNum; j++) {
                unitCost[i][j] = unit[i][j];
            }
        }

        try {
            TransportProblem transportProblem = new TransportProblem(unitDemand, unitSupply, unitCost);
            printSolution(transportProblem, stepCounter++);
            boolean isOptimal = transportProblem.isOptimal();
            while (!isOptimal) {
                isOptimal = transportProblem.performNextStep();
                printSolution(transportProblem, stepCounter++);
            }
        }catch (UnsolvableException ex) {
            solutionTextArea.appendText(ex.getMessage());
        }
    }

    private void printSolution(TransportProblem transportProblem, int stepCounter) {

        solutionTextArea.appendText("\n\t\t\t\t\t\tStep " + stepCounter + "\n");

        solutionTextArea.appendText("\n");
        solutionTextArea.appendText("Alpha:" + "\n");
        for (int i = 0; i < transportProblem.getAlpha().length; i++) {
            solutionTextArea.appendText(transportProblem.getAlpha()[i] + "\t\t");
        }

        solutionTextArea.appendText("\n");
        solutionTextArea.appendText("\nBeta:" + "\n");
        for (int i = 0; i < transportProblem.getBeta().length; i++) {
            solutionTextArea.appendText(transportProblem.getBeta()[i] + "\t\t");
        }

        solutionTextArea.appendText("\n");
        solutionTextArea.appendText("\nTransport:" + "\n");
        for (int i = 0; i < transportProblem.getNumberOfSuppliers(); i++) {
            for (int j = 0; j < transportProblem.getNumberOfRecipients(); j++) {
                if (Double.isNaN(transportProblem.getTransportTable()[i][j]))
                    solutionTextArea.appendText("X\t\t");
                else
                    solutionTextArea.appendText(transportProblem.getTransportTable()[i][j] + "\t\t");
            }
            solutionTextArea.appendText("\n");
        }

        solutionTextArea.appendText("\n");
        solutionTextArea.appendText("Delta:" + "\n");
        for (int i = 0; i < transportProblem.getNumberOfSuppliers(); i++) {
            for (int j = 0; j < transportProblem.getNumberOfRecipients(); j++) {
                if (Double.isNaN(transportProblem.getDelta()[i][j]))
                    solutionTextArea.appendText("X\t\t");
                else
                    solutionTextArea.appendText(transportProblem.getDelta()[i][j] + "\t\t");
            }
            solutionTextArea.appendText("\n");
        }

        solutionTextArea.appendText("\n");
        solutionTextArea.appendText("TOTAL COSTS: " + transportProblem.computeTotalCost() + "\n");

    }

}