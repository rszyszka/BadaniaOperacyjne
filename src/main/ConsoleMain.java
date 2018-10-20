package main;

import model.CycleNotFoundException;
import model.TransportProblem;

public class ConsoleMain {

    public static void main(String[] args) {

        double[] unitDemand = {20, 40, 80,50};
        double[] unitSupply = {30, 40, 60,60};

        double[][] unitCost = {
                {1, 4, 3 ,8}
                , {2, 5, 1 ,2}
                , {2, 6, 5, 9}
                ,{5,8,6,1}
    };

        int stepCounter = 1;
        TransportProblem transportProblem = new TransportProblem(unitDemand, unitSupply, unitCost);
        boolean isOptimal = transportProblem.isOptimal();
        System.out.println("Step"+stepCounter+": ");
        printSolution(transportProblem);

        while(!isOptimal) {
            stepCounter++;
            try {
                isOptimal = transportProblem.performNextStep();
            }catch (CycleNotFoundException ex){
                System.out.println(ex.getMessage());
                break;
            }
            System.out.println("Step"+stepCounter+": ");
            printSolution(transportProblem);
            System.out.println("Total cost: " + transportProblem.computeTotalCost());
        }
    }

    private static void printSolution(TransportProblem transportProblem) {
        System.out.println("Alpha:");
        for (int i = 0; i < transportProblem.getAlpha().length; i++) {
            System.out.print(transportProblem.getAlpha()[i] + " ");
        }
        System.out.println("\nBeta:");
        for (int i = 0; i < transportProblem.getBeta().length; i++) {
            System.out.print(transportProblem.getBeta()[i] + " ");
        }
        System.out.println("\nTransport:");
        for (int i = 0; i < transportProblem.getNumberOfSuppliers(); i++) {
            for (int j = 0; j < transportProblem.getNumberOfRecipients(); j++) {
                System.out.print(transportProblem.getTransportTable()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Delta:");
        for (int i = 0; i < transportProblem.getNumberOfSuppliers(); i++) {
            for (int j = 0; j < transportProblem.getNumberOfRecipients(); j++) {
                System.out.print(transportProblem.getDelta()[i][j] + " ");
            }
            System.out.println();
        }
    }
}