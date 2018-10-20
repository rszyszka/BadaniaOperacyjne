package model;

import java.awt.Point;
import java.util.*;

public class TransportProblem {

    private final double unitCost[][];
    private int numberOfSuppliers, numberOfRecipients;
    private double transportTable[][];

    private double alpha[];
    private double beta[];
    private double delta[][];

    private OptimumChecker optimumChecker;

    private boolean fictitiousSupplier, fictitiousRecipient;

    /**
     * Initializes required tables and computes first iteration of transport and delta tables
     * @param unitDemand one dimensional table of demand for each recipient
     * @param unitSupply one dimensional table of supply for each supplier
     * @param unitCost   two dimensional table of transport cost from each supplier to each recipient
     */
    public TransportProblem(double[] unitDemand, double[] unitSupply, double[][] unitCost) {
        numberOfRecipients = unitDemand.length;
        numberOfSuppliers = unitSupply.length;

        double demand = 0;
        for (int i = 0; i < numberOfRecipients; i++)
            demand += unitDemand[i];

        double supply = 0;
        for (int i = 0; i < numberOfSuppliers; i++)
            supply += unitSupply[i];

        int n = unitCost.length;
        int m = unitCost[n - 1].length;

        fictitiousSupplier = false;
        fictitiousRecipient = false;
        if (demand == supply) {
            this.unitCost = new double[n][m];
            transportTable = new double[n][m];
        } else if (demand > supply) {
            transportTable = new double[n + 1][m];
            this.unitCost = new double[n + 1][m];
            fictitiousSupplier = true;
            numberOfSuppliers++;
        } else {
            transportTable = new double[n][m + 1];
            this.unitCost = new double[n][m + 1];
            fictitiousRecipient = true;
            numberOfRecipients++;
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                this.unitCost[i][j] = unitCost[i][j];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double min = Math.min(unitDemand[j], unitSupply[i]);
                if(min == 0.0) {
                    min = Double.NaN;
                }
                else{
                    unitDemand[j] -= min;
                    unitSupply[i] -= min;
                }
                transportTable[i][j] = min;
            }
        }
        if (fictitiousRecipient) {
            for (int i = 0; i < numberOfSuppliers; i++) {
                transportTable[i][m] = unitSupply[i] == 0.0 ? Double.NaN : unitSupply[i];
            }
        }
        if (fictitiousSupplier) {
            for (int i = 0; i < numberOfRecipients; i++) {
                transportTable[n][i] = unitDemand[i] == 0.0 ? Double.NaN : unitDemand[i];
            }
        }

        alpha = new double[numberOfSuppliers];
        beta = new double[numberOfRecipients];
        delta = new double[numberOfSuppliers][numberOfRecipients];

        optimumChecker = computeDelta();
    }

    /**
     * Performs next iteration of designating optimal transport cost
     * @return <code>true</code> if solution is optimal, <code>false</code> otherwise
     * @throws CycleNotFoundException when cycle could not be found
     */
    public boolean performNextStep() throws CycleNotFoundException {

        int y_h = optimumChecker.minimumValueCoordinates.y;
        int x_h = optimumChecker.minimumValueCoordinates.x;

        Stack<Point> stack = findCycle(x_h,y_h);
        if(stack == null)
            throw new CycleNotFoundException();
        ArrayList<Point> list = new ArrayList<>(stack);
        double minValue = Double.POSITIVE_INFINITY;
        for(int i = 0 ; i < list.size(); i +=2){
            Point p = list.get(i);
            minValue = Math.min(transportTable[p.y][p.x], minValue);
        }
        double value;
        transportTable[y_h][x_h] = minValue;
        int size = list.size()-1;
        for(int i = 0; i < size ; i++){
            Point p  = list.get(i);
            if(i % 2 == 0){
                value = transportTable[p.y][p.x] - minValue;
                transportTable[p.y][p.x] = value == 0.0 ? Double.NaN : value;
            }else{
                value = transportTable[p.y][p.x] + minValue;
                transportTable[p.y][p.x] = Double.isNaN(value) ? minValue : value;
            }
        }
        optimumChecker = computeDelta();
        return isOptimal();
    }

    /**
     * Computes total cost of transport
     * @return value of total cost
     */
    public double computeTotalCost() {
        double totalCost = 0.0;
        for (int i = 0; i < numberOfSuppliers; i++)
            for (int j = 0; j < numberOfRecipients; j++) {
                if (Double.isNaN(transportTable[i][j]))
                    continue;
                totalCost += transportTable[i][j] * unitCost[i][j];
            }
        return totalCost;
    }

    /**
     * Computes alpha and beta variables and designates delta table
     * @return OptimumChecker instance which contains information whether solution
     * is optimal and coordinates of minimum value of optimality indexes
     * @see OptimumChecker
     */
    private OptimumChecker computeDelta() {

        for (int i = 1; i < alpha.length; i++)
            alpha[i] = Double.NaN;
        for (int i = 0; i < beta.length; i++)
            beta[i] = Double.NaN;

        alpha[0] = 0.0;

        for (int k = 0; k < 10; k++) {
            for (int i = 0; i < numberOfSuppliers; i++) {
                for (int j = 0; j < numberOfRecipients; j++) {
                    if (!Double.isNaN(transportTable[i][j])) {
                        if (Double.isNaN(beta[j]) && !Double.isNaN(alpha[i]))
                            beta[j] = -unitCost[i][j] - alpha[i];
                        if (Double.isNaN(alpha[i]) && !Double.isNaN(beta[j]))
                            alpha[i] = -unitCost[i][j] - beta[j];
                    }
                }
            }
        }
        OptimumChecker oc = new OptimumChecker();
        double minValue = 0.0;
        for (int i = 0; i < numberOfSuppliers; i++) {
            for (int j = 0; j < numberOfRecipients; j++) {
                delta[i][j] = unitCost[i][j] + alpha[i] + beta[j];
                if (delta[i][j] < minValue) {
                    oc.isOptimal = false;
                    oc.minimumValueCoordinates.y = i;
                    oc.minimumValueCoordinates.x = j;
                    minValue = delta[i][j];
                }
                if(!Double.isNaN(transportTable[i][j]))
                    delta[i][j] = Double.NaN;
            }
        }
        return oc;
    }

    /**
     * Finds the shortest cycle for given delta cell coordinates
     * @param x <code>x</code> coordinate of negative delta cell
     * @param y <code>y</code> coordinate of negative delta cell
     * @return <code>Stack</code> which contains coordinates of cells in cycle,
     * <code>null</code> when cycle couldn't be found
     */
    private Stack<Point> findCycle(int x, int y){
        LinkedList<Stack<Point>> queue = new LinkedList<>();
        TreeSet<Point> set = new TreeSet<>(new DistanceComparator(x,y));

        for(int i = 0; i < numberOfSuppliers; i++)
            if(Double.isNaN(delta[i][x]))
                set.add(new Point(x,i));

        for(Point p : set){
            Stack<Point> s = new Stack<>();
            s.push(p);
            queue.add(s);
        }

        int numberOfStacks = queue.size();
        for(int k = 0; !queue.isEmpty(); k++){
            if(k % 2 == 0){
                for(int i=0; i < numberOfStacks; i++) {
                    Stack<Point> stack = queue.poll();
                    Point p = stack.peek();
                    boolean newStack = false;
                    for(int j = 0; j< numberOfRecipients;j++){
                        if(p.y == y && j == x){
                            if(newStack)
                                stack.pop();
                            stack.push(new Point(x,y));
                            return stack;
                        }
                        if(p.x != j && Double.isNaN(delta[p.y][j])){
                            if(!newStack) {
                                stack.push(new Point(j, p.y));
                                queue.add(stack);
                                newStack = true;
                            }
                            else{
                                Stack<Point> stack2 = (Stack<Point>) stack.clone();
                                stack2.pop();
                                stack2.push(new Point(j,p.y));
                                queue.add(stack2);
                            }
                        }
                    }
                }
            }else{
                for(int i=0; i < numberOfStacks; i++) {
                    Stack<Point> stack = queue.poll();
                    Point p = stack.peek();
                    boolean newStack = false;
                    for(int j = 0; j< numberOfSuppliers;j++){
                        if(p.x == x && j == y){
                            if(newStack)
                                stack.pop();
                            stack.push(new Point(x,y));
                            return stack;
                        }
                        if(p.y !=j && Double.isNaN(delta[j][p.x])){
                            if(!newStack) {
                                stack.push(new Point(p.x, j));
                                queue.add(stack);
                                newStack = true;
                            }
                            else{
                                Stack<Point> stack2 = (Stack<Point>) stack.clone();
                                stack2.pop();
                                stack2.push(new Point(p.x,j));
                                queue.add(stack2);
                            }
                        }
                    }
                }
            }
            numberOfStacks = queue.size();
        }
    return null;
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

    public double[] getAlpha() {
        return alpha;
    }

    public double[] getBeta() {
        return beta;
    }

    public double[][] getDelta() {
        return delta;
    }

    public boolean isOptimal() {
        return optimumChecker.isOptimal;
    }
}

class OptimumChecker {
    boolean isOptimal;
    Point minimumValueCoordinates;

    OptimumChecker() {
        isOptimal = true;
        minimumValueCoordinates = new Point(0, 0);
    }
}