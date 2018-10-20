package model;

import java.awt.Point;
import java.util.Comparator;

class DistanceComparator implements Comparator<Point> {

    private int x,y;

    DistanceComparator(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compare(Point o1, Point o2) {

        double distance1 = Math.sqrt(Math.pow(o1.x - x, 2) + Math.pow(o1.y - y, 2));
        double distance2 = Math.sqrt(Math.pow(o2.x - x, 2) + Math.pow(o2.y - y, 2));

        return Double.compare(distance1, distance2);
    }
}
