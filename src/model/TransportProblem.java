package model;

public class TransportProblem {

    private int numberOfSuppliers, numberOfRecipients;

    private final double unitCost[][];
    private double transportTable[][];

    private boolean fictitiousSupplier, fictitiousRecipient;

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
        this.unitCost = new double[n][m];
        for(int i = 0; i<n; i++)
            for(int j =0; j<m; j++)
                this.unitCost[i][j] = unitCost[i][j];

        fictitiousSupplier = false;
        fictitiousRecipient = false;
        if(demand == supply) {
            transportTable = new double[n][m];
        }
        else if (demand > supply) {
            transportTable = new double[n + 1][m];
            fictitiousSupplier = true;
            numberOfSuppliers++;
        }
        else {
            transportTable = new double[n][m + 1];
            fictitiousRecipient = true;
            numberOfRecipients++;
        }

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
}
