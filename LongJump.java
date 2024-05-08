//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//
//class test{
//    public test(String[] args) {
//        LongJump g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])) {
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int count = 0;
//            for (Object CaseInList : all) {
//                count++;
//                JSONArray a = (JSONArray) CaseInList;
//                int testSize = 0;
//                int waSize = 0;
//                System.out.print("Case ");
//                System.out.println(count);
//                //Board Setup
//                JSONObject argsSetting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSetting.get("args");
//
//                int[] arr=new int[argSettingArr.size()];
//                for(int k=0;k<argSettingArr.size();k++) {
//                    arr[k] = (int)(long) argSettingArr.get(k);
//                }
//                g = new LongJump(arr);
//
//                for (Object o : a) {
//                    JSONObject person = (JSONObject) o;
//
//                    String func = person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch (func) {
//                        case "addPlayer" -> g.addPlayer(Integer.parseInt(arg.get(0).toString()));
//                        case "winnerDistances" -> {
//                            testSize++;
//                            Integer t_ans = (int)(long)person.get("answer");
//                            Integer r_ans = g.winnerDistances(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            if (t_ans.equals(r_ans)) {
//                                System.out.println("winnerDistances : AC");
//                            } else {
//                                waSize++;
//                                System.out.println("winnerDistances : WA");
//                                System.out.println("Your answer : "+r_ans);
//                                System.out.println("True answer : "+t_ans);
//                            }
//                        }
//                    }
//
//                }
//                System.out.println("Score: " + (testSize - waSize) + " / " + testSize + " ");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}
class LongJump {
    private Node root;
    private class Node{
        private int key;
        private Node left;
        private Node right;
        private int sum;

        Node(int k, int s){
            key = k;
            sum = s;
        }
    }
    public void put(int key){
        root = put(root, key);
    }
    private Node put(Node x, int key){
        if(x==null) return new Node(key, key);

        //if key>x.key
        if (key<x.key) x.left = put(x.left, key);
        else if (key>x.key) x.right = put(x.right, key);
        x.sum = x.key+ size(x.left) + size(x.right);
        return x;
    }

//    public int size(){
//        return size(root);
//    }
    private int size(Node x){
        if(x==null) return 0;
        return x.sum;
    }


    public int floor(int key){
        Node x = floor(root, key);
        if(x==null) return 0;
        return x.key;
    }

    public int ceiling(int key){
        Node x = ceiling(root, key);
        if(x==null) return 0;
        return x.key;
    }




    private Node floor(Node x, int key){
        if (x==null) return null;
        if (key==x.key) return x;
        if (key<x.key) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t!=null) return t;
        else return x;
    }

    private Node ceiling(Node x, int key){
        if (x==null) return null;
        if (key==x.key) return x;
        if (key>x.key) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t!=null) return t;
        else return x;
    }
    // Add new player in the competition with different distance
    public void addPlayer(int distance) {
        put(distance);
    }
    public int rank(int key){
        return rank(key, root);
    }
    private int rank(int key, Node x){
        if (x==null)return 0;
        if (key<x.key) return rank(key, x.left);
        else if (key>x.key) return x.key+size(x.left)+rank(key, x.right);
        else return size(x.left);
    }
    public LongJump(int[] playerList){
        int num = playerList.length;
        for (int i =0;i<num;i++){
            put(playerList[i]);
        }

//
//        System.out.println(rank(floor(8)));
//        System.out.println(rank(ceiling(7)));
//        System.out.println(floor(8));
//        System.out.println(rank(floor(8))-rank(ceiling(7))+floor(8));

    }
    public int m(){
        Node g = root;
        while(g.right!=null){
            g = g.right;
        }
        return g.key;

    }

    // return the winners total distances in range[from, to]
    public int winnerDistances(int from, int to) {
        int w = m();
        if (from>w && to>w){
            return 0;
        }


        int p=floor(to);
        return rank(p)-rank(ceiling(from))+p;

    }
    public static void main(String[] args) {
//        test t = new test(args);

//        LongJump solution = new LongJump(new int[]{2,5,6});
////        range = 5-8 rank(8)+rank(5)+8 rank(floor(8))+rank(floor(5))+8
//        System.out.println(solution.winnerDistances(6,6));
//        solution.addPlayer(10);
//        solution.addPlayer(8);
//        System.out.println(solution.winnerDistances(3,10));
    }
}