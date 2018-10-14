package model;

import java.awt.*;

public class TransportProblem {

    private int numberOfSuppliers, numberOfRecipients;

    private final double unitCost[][];
    private double transportTable[][];

    private Double alpha[];
    private Double beta[];
    private double delta[][];

    private OptimumChecker optimumChecker;

    private boolean fictitiousSupplier, fictitiousRecipient;

    /**
     *  Initializes required tables and computes first iteration of transport and delta tables
     * @param unitDemand one dimensional table of demand for each recipient
     * @param unitSupply one dimensional table of supply for each supplier
     * @param unitCost two dimensional table of transport cost from each supplier to each recipient
     */
    public TransportProblem(double []unitDemand, double []unitSupply, double [][]unitCost) {
        numberOfRecipients = unitDemand.length;
        numberOfSuppliers = unitSupply.length;

        double demand = 0;
        for(int i = 0; i < numberOfRecipients; i ++)
            demand += unitDemand[i];

        double supply = 0;
        for(int i = 0; i < numberOfSuppliers; i ++)
            supply += unitSupply[i];

        int n = unitCost.length;
        int m = unitCost[n-1].length;

        fictitiousSupplier = false;
        fictitiousRecipient = false;
        if(demand == supply) {
            this.unitCost = new double[n][m];
            transportTable = new double[n][m];
        }
        else if (demand > supply) {
            transportTable = new double[n + 1][m];
            this.unitCost = new double[n + 1][m];
            fictitiousSupplier = true;
            numberOfSuppliers++;
        }
        else {
            transportTable = new double[n][m + 1];
            this.unitCost = new double[n][m + 1];
            fictitiousRecipient = true;
            numberOfRecipients++;
        }

        for(int i = 0; i<n; i++)
            for(int j =0; j<m; j++)
                this.unitCost[i][j] = unitCost[i][j];

        for(int i = 0 ; i < n ; i ++){
            for(int j = 0 ; j < m; j ++){
                transportTable[i][j] = Math.min(unitDemand[j],unitSupply[i]);
                unitDemand[j] -= transportTable[i][j];
                unitSupply[i] -= transportTable[i][j];
            }
        }
        if(fictitiousRecipient){
            for( int i =0 ; i < numberOfSuppliers; i++){
                transportTable[i][m] = unitSupply[i];
            }
        }
        if(fictitiousSupplier){
            for( int i =0 ; i < numberOfRecipients; i++){
                transportTable[n][i] = unitDemand[i];
            }
        }

        alpha = new Double[numberOfSuppliers];
        beta = new Double[numberOfRecipients];
        delta = new double[numberOfSuppliers][numberOfRecipients];

        optimumChecker = computeDelta();
    }

    /**
     * Performs next iteration of designating optimal transport cost
     * @return <code>true</code> if solution is optimal, <code>false</code> otherwise
     */
    public boolean performNextStep(){

        int y_h = optimumChecker.minimumValueCoordinates.y;
        int x_h = optimumChecker.minimumValueCoordinates.x;
        int x = x_h;
        int y = y_h;

        boolean finding = true;
        for(int i = 0 ; i < numberOfRecipients && finding; i ++){
            if(delta[y_h][i] == 0.0){
                for(int j =0; j < numberOfSuppliers && finding; j++){
                    if(delta[j][i] == 0.0 && delta[j][x_h] == 0.0){
                        x = i;
                        y = j;
                        finding = false;
                    }
                }
            }
        }

        System.out.println("y:"+y+" x:"+x);

        return false;
    }

    /**
     * Computes alpha and beta variables and designates delta table
     * @return OptimumChecker instance which contains information whether solution
     * is optimal and coordinates of minimum value of optimality indexes
     * @see OptimumChecker
     */
    private OptimumChecker computeDelta(){

        alpha[0] = 0.0;
        for(int i = 0 ; i < numberOfSuppliers; i ++) {
            for (int j = 0; j < numberOfRecipients; j++) {
                if(transportTable[i][j] != 0 ){
                    if(beta[j] == null && alpha[i] != null)
                        beta[j] = -unitCost[i][j] - alpha[i];
                    if(alpha[i] == null && beta[j] != null)
                        alpha[i] = -unitCost[i][j] - beta[j];
                }
            }
        }
        OptimumChecker oc = new OptimumChecker();
        double minValue = 0.0;
        for(int i = 0 ; i < numberOfSuppliers; i ++) {
            for (int j = 0; j < numberOfRecipients; j++) {
                delta[i][j] = unitCost[i][j] + alpha[i] + beta[j];
                if(delta[i][j] < minValue){
                    oc.isOptimal = false;
                    oc.minimumValueCoordinates.y = i;
                    oc.minimumValueCoordinates.x = j;
                    minValue = delta[i][j];
                }
            }
        }
        return oc;
    }

    public boolean isFictitiousSupplier() {
        return fictitiousSupplier;
    }

    public boolean isFictitiousRecipient() {
        return fictitiousRecipient;
    }

    public int getNumberOfSuppliers() {
        return numberOfSuppliers;
    }

    public int getNumberOfRecipients() {
        return numberOfRecipients;
    }

    public double[][] getUnitCost() {
        return unitCost;
    }

    public double[][] getTransportTable() {
        return transportTable;
    }

    public Double[] getAlpha() {
        return alpha;
    }

    public Double[] getBeta() {
        return beta;
    }

    public double[][] getDelta() {
        return delta;
    }

    public boolean isOptimal(){
        return optimumChecker.isOptimal;
    }
}

class OptimumChecker{
    boolean isOptimal;
    Point minimumValueCoordinates;

    OptimumChecker(){
        isOptimal = true;
        minimumValueCoordinates = new Point(0,0);
    }
}
