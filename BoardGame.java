//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class BoardGame {
    int[] board;
    int[] openedge;

    //int[][] number;
    int a,b;
    int count;

    WeightedQuickUnionUF uf;
    public BoardGame(int h, int w){
        this.board = new int[h*w];
        this.openedge = new int[h*w];
        //this.number = new int[h][w];
        count = 0;
        a = h;
        b = w;
        uf = new WeightedQuickUnionUF(h*w);

    } // create a board of size h*w

    // put stones of the specified type on the board according to the coordinates
    public void putStone(int[] x, int[] y, char stoneType)
    {

        //union
        for (var i = 0; i< x.length; i++){
            int s = x[i]*this.b +y[i];
            if(stoneType == 'O'){
                this.board[s] = 1;
            }
            if(stoneType == 'X'){
                this.board[s] = 2;
            }
            this.count++;
            int a, b,j ;
            a = uf.find(s);
            this.openedge[a] += 4;


            //判斷是否相同棋子，檢查find是否相同，
            //right
            /*
            if (y[i] != this.b-1){
                if(this.board[s+1]!=0) {
                    b = uf.find(s + 1);
                    if (this.board[s] == this.board[s + 1]) {

                        uf.union(s, s + 1);
                        this.openedge[uf.find(s)] = this.openedge[a] + this.openedge[b] - 2;
                        this.count--;

                    }
                    else{
                        this.openedge[a] -=1;
                        this.openedge[b] -=1;
                    }
                }

            }
            else{
                this.openedge[a] -=1;
            }*/
            //
            if (y[i] != this.b-1){
                if(this.board[s + 1]!=0) {
                    b = uf.find(s + 1);
                    if (this.board[s] == this.board[s + 1]) {

                        if (a != b) {
                            uf.union(s, (s + 1));

                            j = uf.find(s);
                            this.openedge[j] = this.openedge[a] + this.openedge[b] - 2;
                            a = j;

                            this.count--;
                        } else {
                            this.openedge[a] -= 2;
                        }

                    }
                    else{
                        this.openedge[a] -=1;
                        this.openedge[b] -=1;

                    }
                }
            }
            else{
                this.openedge[a] +=100;
            }

            //down
            if (x[i] != this.a-1){
                if(this.board[s+ this.b]!=0) {
                    b = uf.find(s + this.b);
                    if (this.board[s] == this.board[s + this.b]) {

                        if (a != b) {
                            uf.union(s, s + this.b);

                            j = uf.find(s);
                            this.openedge[j] = this.openedge[a] + this.openedge[b] - 2;
                            a = j;

                            this.count--;
                        } else {
                            this.openedge[a] -= 2;
                        }
                    }
                    else{
                        this.openedge[a] -=1;
                        this.openedge[b] -=1;

                    }
                }
            }
            else{
                this.openedge[a] +=100;
            }
            //left
            if (y[i] != 0){
                if(this.board[s-1]!=0) {
                    b = uf.find(s - 1);
                    if (this.board[s] == this.board[s - 1]) {


                        if (a != b) {
                            uf.union(s, s - 1);
                            j = uf.find(s);
                            this.openedge[j] = this.openedge[a] + this.openedge[b] - 2;
                            a = j;


                            this.count--;


                        } else {
                            this.openedge[a] -= 2;
                        }
                    }
                    else{
                        this.openedge[a] -=1;
                        this.openedge[b] -=1;

                    }
                }
            }
            else{
                this.openedge[a] +=100;
            }
            //up
            if (x[i] != 0){
                if(this.board[s-this.b]!=0) {
                    b = uf.find(s - this.b);
                    if (this.board[s] == this.board[s - this.b]) {

                        if (a != b) {
                            uf.union(s, s - this.b);

                            this.openedge[uf.find(s)] = this.openedge[a] + this.openedge[b] - 2;

                            this.count--;
                        } else {
                            this.openedge[a] -= 2;
                        }
                    }
                    else{
                        this.openedge[a] -=1;
                        this.openedge[b] -=1;

                    }

                }
            }
            else{
                this.openedge[a] +=100;
            }



        }




    }


    // Answer if the stone and its connected stones are surrounded by another type of stones
    //回傳一個boolean來得知是否被圍繞
    public boolean surrounded(int x, int y){
        int root = uf.find(x*this.b+y);

        if(this.openedge[root]==0){
            //System.out.println(this.openedge[root]);
            return true;
        }
        else{
            //System.out.println(root);
            //System.out.println(this.openedge[root]);

            return false;
        }
    }

    // Get the type of the stone at (x,y)
    //得知stone的type
    public char getStoneType(int x, int y){
        char r = 'O';
        if(this.board[x*this.b+y] == 1){
            r = 'O';
        }
        if(this.board[x*this.b+y] == 2){
            r = 'X';
        }
        return(r);
    }

    // Get the number of connected regions in the board, including both types of the stones
    public int countConnectedRegions(){


        char c=(char)(this.count);
        //System.out.println(this.count);
        return(c);

    }


/*
    public static void test(String[] args){
        BoardGame g;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONArray a = (JSONArray) CaseInList;
                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                //Board Setup
                JSONObject argsSeting = (JSONObject) a.get(0);
                a.remove(0);

                JSONArray argSettingArr = (JSONArray) argsSeting.get("args");
                g = new BoardGame(
                        Integer.parseInt(argSettingArr.get(0).toString())
                        ,Integer.parseInt(argSettingArr.get(1).toString()));

                for (Object o : a)
                {
                    JSONObject person = (JSONObject) o;

                    String func =  person.get("func").toString();
                    JSONArray arg = (JSONArray) person.get("args");

                    switch(func){
                        case "putStone":
                            int xArray[] = JSONArraytoIntArray((JSONArray) arg.get(0));
                            int yArray[] = JSONArraytoIntArray((JSONArray) arg.get(1));
                            String stonetype =  (String) arg.get(2);

                            g.putStone(xArray,yArray,stonetype.charAt(0));
                            break;
                        case "surrounded":
                            Boolean answer = (Boolean) person.get("answer");
                            testSize++;
                            System.out.print(testSize + ": " + func + " / ");
                            Boolean ans = g.surrounded(
                                    Integer.parseInt(arg.get(0).toString()),
                                    Integer.parseInt(arg.get(1).toString())
                            );
                            if(ans==answer){
                                System.out.println("AC");
                            }else{
                                waSize++;
                                System.out.println("WA");
                            }
                            break;
                        case "countConnectedRegions":
                            testSize++;
                            int ans2 = Integer.parseInt(arg.get(0).toString());
                            int ansCR = g.countConnectedRegions();
                            System.out.print(testSize + ": " + func + " / ");
                            if(ans2==ansCR){
                                System.out.println("AC");
                            }else{
                                waSize++;
                                System.out.println("WA");
                            }
                    }

                }
                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int[] JSONArraytoIntArray(JSONArray x){
        int sizeLim = x.size();
        int MyInt[] = new int[sizeLim];
        for(int i=0;i<sizeLim;i++){
            MyInt[i]= Integer.parseInt(x.get(i).toString());
        }
        return MyInt;
    }*/

    public static void main(String[] args) {//mian
        //test(args);
    }

}
