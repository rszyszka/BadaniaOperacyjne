package main;

import model.TransportProblem;

public class ConsoleMain {

    public static void main(String[] args){

        double [] unitDemand = {20, 40, 90};
        double [] unitSupply = {50, 70, 30};

        double[][] unitCost = {
                {3, 5, 7}
                ,{12, 10, 9}
                ,{13, 3, 9}
        };

        TransportProblem transportProblem = new TransportProblem(unitDemand, unitSupply, unitCost);

        for( int i = 0 ; i < transportProblem.getNumberOfSuppliers(); i ++){
            for( int j = 0 ; j < transportProblem.getNumberOfRecipients(); j ++){
                System.out.print(transportProblem.getTransportTable()[i][j]+" ");
            }
            System.out.println();
        }
    }
}
