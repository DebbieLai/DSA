import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


//
//
//class test{
//    public test(String[] args){
//        Kings sol;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            for(Object CaseInList : all){
//                JSONArray a = (JSONArray) CaseInList;
//                int q_cnt = 0, wa = 0,ac = 0;
//                for (Object o : a) {
//                    q_cnt++;
//                    JSONObject person = (JSONObject) o;
//                    JSONArray arg_str = (JSONArray) person.get("strength");
//                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
//                    Long arg_k = (Long) person.get("k");
//                    JSONArray arg_ans = (JSONArray) person.get("answer");
//                    int STH[] = new int[arg_str.size()];
//                    int RNG[] = new int[arg_str.size()];
//                    int k = Integer.parseInt(arg_k.toString());
//
//                    int Answer[] = new int[arg_ans.size()];
//                    int Answer_W[] = new int[arg_ans.size()];
//                    for(int i=0;i<arg_ans.size();i++){
//                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
//                    }
//                    for(int i=0;i<arg_str.size();i++){
//                        STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
//                        RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
//                    }
//                    sol = new Kings(STH,RNG);
//                    Answer_W = sol.topKKings(k);
//                    for(int i=0;i<arg_ans.size();i++){
//                        if(Answer_W[i]==Answer[i]){
//                            if(i==arg_ans.size()-1){
//                                System.out.println(q_cnt+": AC");
//                            }
//                        }else {
//                            wa++;
//                            System.out.println(q_cnt+": WA");
//                            break;
//                        }
//                    }
//
//                }
//                System.out.println("Score: "+(q_cnt-wa)+"/"+q_cnt);
//
//            }
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}
class King{
    // optional, for reference only
    int Strength;
    int Index;
    King(int str, int i){
        Strength=str;
        Index=i;
    }
}


class Kings {

    Node first;

    int step = 0;
    int[] knock;
    int num ;

    int n;


    int[] answer;

    int[] ss;
    King[] kk ;


    public class Node{
        private Integer index;
        private Node next;
    }




    public void push(int i){
        Node oldfirst = first;
        first = new Node();
        first.index = i;
        first.next = oldfirst;
    }




    public int pop(){
        int i = first.index;
        first = first.next;
        return i;
    }
    public boolean isEmpty(){
        return first.next == null;
    }



    public Kings(int[] strength, int[] range) {

        n = strength.length;
        num = n;
        knock = new int[n];



        int j, a, b, w, r1, r2, q;
        push(0);
        first.next = null;
        knock[0]+=1;


        for (var i = 1; i < n; i++) {



            b = strength[i]; //外圍index
            r2 = range[i];//外圍index 的range


            while (true) {


                j = first.index;
                a = strength[j];//stack的第一個index
                r1 = range[j];//stack的第一個index 的range

                if (b < a) { //決定i的左邊界
                    push(i);

                    if (r2 <= (i-(j+1))) { //範圍小-->King
                        knock[i-r2] += i+1;

                    }
                    else{
                        knock[j+1] += i+1;
                    }
                    break;
                }

                if(a==b){
                    w = first.index;
                    if(r1<=(i-1)-w){ //範圍小-->King
                        knock[w+r1] -= w+1;
                    }
                    else{
                        knock[i-1] -= w+1;
                    }
                    w = pop();

                    push(i);
                    if(r2<=(i-(j+1))){ //範圍小-->King
                        knock[i-r2] += i+1;
                    }
                    else{
                        knock[j+1] += i+1;
                    }
                    break;
                }

                if (isEmpty()) {
                    //決定stack僅存index的右邊界
                    if(r1 <= ((i-1)-j)) {
                        knock[j+r1] -= j+1;
                    }
                    else{
                        knock[i-1] -= j+1;
                    }

                    //把原本的pop出去
                    first.index = i;
                    first.next = null;

                    //決定新放進來的左邊界
                    if (r2 <= i) {
                        knock[i-r2] += i+1;
                    }
                    else{
                        knock[0] += i+1;
                    }
                    break;
                }
                w = first.index;
                //stack的第一個值要被pop掉，並決定其右邊界
                if(r1<=(i-1)-w){
                    knock[w+r1] -= w+1;
                }
                else{
                    knock[i-1] -= w+1;
                }
                w = pop();
            }


        }
        //檢查stack還有誰

        while (true){
            j = first.index;
            r1 = range[j];
            if (isEmpty()) {

                if(r1<=(n-1)-j){
                    knock[j+r1] -= j+1;
                }
                else{
                    knock[n-1] -= j+1;
                }

                break;
            }


            if(r1<=n-1-j){
                knock[j+r1] -= j+1;
            }
            else{
                knock[n-1] -= j+1;
            }

            first = first.next;

        }


        num = 0;
        int u = 0;
        ArrayList index = new ArrayList();




        for (int h = 0; h <n ; h++){
            u += knock[h];

            if(u==0 ){
                if(h==n-1){
                    if( knock[h]==0){
                        num+=1;
                        index.add(h);
                    }
                }
                else{
                    num+=1;
                    index.add(h);
                }

            }
            else if(u-(h+1)==0){
                num+=1;
                index.add(h);
            }

        }

        int c;
        kk = new King[num];

        for (int p = 0; p <num ; p++){
            c = (int)index.get(p);
            kk[p] = new King(strength[c],c);
//            System.out.println(c);




        }






    }




    public int[] topKKings(int k) {
        if (k==0){
            int[] pp = new int[0];
            return pp;
        }
        if (k>num){
            k = num;
        }
        class cp implements Comparator<King>{
            public int compare(King a,King b){
                if (a.Strength>b.Strength){
                    return 1;
                }
                else if (a.Strength==b.Strength){
                    if (a.Index <b.Index){
                        return 1;
                    }
                    else{
                        return -1;
                    }
                }
                return -1;
            }
        }
        int[] hand = new int[k];

        MinPQ<King> pq = new MinPQ<>(new cp());
        for (int e = 0;e < num; e++)
        {

            pq.insert(kk[e]);

            if (pq.size() > k)
            {
                pq.delMin();}
        }
        int w = pq.size()-1;

        while(!pq.isEmpty() ){
            hand[w] = pq.delMin().Index;
            w--;
        }




        return hand;
    }

    public static void main(String[] args) {
//        test t = new test(args);
//        Kings sol = new Kings(new int[] {0,1,2,3,4,3,2,1,0}
//                , new int[] { 8,7,6,5,4,3,2,1,0});
//        System.out.println(Arrays.toString(sol.topKKings(5)));
    }
}

