import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import edu.princeton.cs.algs4.GrahamScan;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Stack;

class Airport {
    // Output smallest average distance with optimal selection of airport location.
    //public double airport(List<int[]> houses)
    public double airport(List<int[]> houses) {
        /*double avgDistance;
        // TODO

        return avgDistance;*/
        double Avgx = 0;
        double Avgy = 0;
        int i1,j1;
        int n = houses.size();

        Point2D[] P = new Point2D[houses.size()];
        for (var i = 0; i < houses.size(); i++){
            i1 = houses.get(i)[0];
            j1 = houses.get(i)[1];
            P[i] = new Point2D(i1,j1);
            Avgx+=i1;
            Avgy+=j1;
        }
        Avgx = Avgx/houses.size();
        Avgy = Avgy/houses.size();

        ArrayList<Point2D> d = new ArrayList<Point2D>();
        GrahamScan G = new GrahamScan(P);
        Iterable<Point2D> t = new Stack<Point2D>();
        for (Point2D p : G.hull())d.add(p);
        double a,b,c;
        Point2D c1,c2;
        double dis;
        double min=0;

        for (var i = 0; i < d.size(); i++){
            if (i==d.size()-1){
                c1 = d.get(i);
                c2 = d.get(0);
            }
            else {
                c1 = d.get(i);
                c2 = d.get(i + 1);
            }
            a = c1.y()-c2.y();
            b = c2.x()-c1.x();
            c = c1.x()*c2.y()-c1.y()*c2.x();
            //count distance
            dis = Math.abs(a*Avgx+b*Avgy+c)/Math.sqrt(a*a+b*b);
            if (i==0){
                min = dis;
            }
            else{
                if(dis<min) {
                    min = dis;
                }
            }

        }
        return min;
    }


    public static void main(String[] args) {

    }
}
