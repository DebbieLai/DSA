import java.util.List;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.PrimMST;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

class Budget {
    public Budget() {};
    //return the total costs of the bridges
    public int plan(int island, List<int[]> bridge) {

        EdgeWeightedGraph EE = new EdgeWeightedGraph(island);
        for(int[] i: bridge){
            Edge j = new Edge(i[0],i[1],i[2]);
            EE.addEdge(j);}
        PrimMST uu = new PrimMST(EE);
        int answer = (int)uu.weight();

        return answer;

    }





    public static void main(String[] args) {
//        Budget solution = new Budget();
//        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,2});
//            add(new int[]{0,2,4});
//            add(new int[]{1,3,5});
//            add(new int[]{2,1,1});
//        }}));
//        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,0});
//            add(new int[]{0,2,4});
//            add(new int[]{0,3,4});
//            add(new int[]{1,2,1});
//            add(new int[]{1,3,4});
//            add(new int[]{2,3,2});
//        }}));
    }
}