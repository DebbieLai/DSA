import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DijkstraSP;

class Flood {
    public Flood() {};
    //return which village is the latest one flooded
    public int village(int villages, List<int[]> road) {

        EdgeWeightedDigraph EE = new EdgeWeightedDigraph(villages);
        for(int[] i: road){
            DirectedEdge j = new DirectedEdge(i[0],i[1],i[2]);
            EE.addEdge(j);
        }
        DijkstraSP uu = new DijkstraSP(EE,0);
        double longest = 0;
        int answer =0;
        double o;

        for(int i = 1;i<villages;i++){
            if (uu.hasPathTo(i)){
                o = uu.distTo(i);

                if (o>longest){
                    answer = i;
                    longest = o;
                }
            }

        }
        return answer;


    }

    public static void main(String[] args) {
//        Flood solution = new Flood();
//        System.out.println(solution.village(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,3});
//            add(new int[]{0,2,6});
//            add(new int[]{1,2,2});
//            add(new int[]{2,1,3});
//            add(new int[]{2,3,3});
//            add(new int[]{3,1,1});
//        }}));
//        System.out.println(solution.village(6, new ArrayList<int[]>(){{
//            add(new int[]{0,1,5});
//            add(new int[]{0,4,3});
//            add(new int[]{1,2,1});
//            add(new int[]{1,3,3});
//            add(new int[]{1,5,2});
//            add(new int[]{2,3,4});
//            add(new int[]{3,2,1});
//            add(new int[]{4,0,2});
//            add(new int[]{4,1,4});
//            add(new int[]{5,0,3});
//        }}));
    }
}