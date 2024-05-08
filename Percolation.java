/*import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/

import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {
    //int[] board;//tell whether it is open
    int[] root;//tell whether the site is open or full * connect to up-->1, connect to down-->2, connect both-->3
    int[] list_num;//tell the link list number
    int[] list_length;
    int n;
    int p;
    int count;


    WeightedQuickUnionUF uf;
    Node[] first ;
    Node[] last;
    Point2D[] P;
    int step =0;



    private static class Node {
        private Point2D site;
        private Node next;
    }
    // create N-by-N grid, with all sites blocked
    public Percolation(int N){
        //this.board = new int[N*N];//0 or 10
        this.root = new int[N*N];//0 or 1 or 2 or 3 if no root??
        this.list_num = new int[N*N];//put the list num of a root

        if(N>10){
            this.first = new Node[N*N/4];
            this.last = new Node[N*N/4];
            this.list_length = new int[N*N/4];// put the list num length
        }
        else{
            this.first = new Node[N*N];
            this.last = new Node[N*N];
            this.list_length = new int[N*N];// put the list num length
        }


        this.n = N;
        this.p = 0;
        this.count = 0;

        uf = new WeightedQuickUnionUF(N*N);

    }
    public void storelist(int k){
        if (this.step==0){

            //System.out.println("hiii");

            Node current = first[k];
            int c = 0;
            /*
            while (true) {
                if(current!=null){
                    c++;
                    current = current.next;}

                else {
                    break;
                }

            }*/

            this.P = new Point2D[this.list_length[k]];
            current = first[k];
            c = 0;
            while (true) {
                if(current!=null){

                    this.P[c] = current.site;

                    c++;
                    current = current.next;}

                else {
                    break;
                }

            }
            Merge.sort(this.P);
            this.step =1;

        }

    }

    public boolean isEmpty(int k){
        return first[k] == null;
    }
    public void connectlist(int x, int y){
        this.last[x].next = this.first[y];
        this.last[x] = this.last[y];
        this.list_length[x]= this.list_length[y]+this.list_length[x];
    }
    public void enqueue(int i, int j, int k){
        this.list_length[k]++;
        Node oldlast = this.last[k];
        this.last[k] = new Node();
        this.last[k].site = new Point2D(i,j);
        this.last[k].next = null;
        if(isEmpty(k)) this.first[k] = this.last[k];
        else                    oldlast.next = this.last[k];
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {

        int s = i * this.n + j;
        this.root[s] = 10;
        int a,b;
        a = uf.find(s);
        int w;
        int lone = 0;
        int has_list = 0;
        this.list_num[a] = this.count;
        int rb=0;
        int ra =0;
        int la = 0;
        int lb = 0;
        int rw = 0;

        if(this.n==1){
            this.P = new Point2D[1];
            this.P[0] = new Point2D(0,0);
            this.step=1;
        }


        //right
        if (j != this.n-1){ //if there is right site
            if(this.root[s + 1]>=10) { //if right site is open
                lone = 1;
                b = uf.find(s + 1);
                if (a != b) {
                    uf.union(s, (s + 1));
                    w = uf.find(s);
                    enqueue(i, j, this.list_num[w]);
                    if (w != a){

                        has_list=1;
                        a = w;}
                    else{
                        this.list_num[a] =this.list_num[b];
                        has_list=1;
                        rb = this.root[b]-10;
                        ra = this.root[a]-10;

                        if(rb==1){
                            if(ra==0){
                                this.root[a]=11;
                            }
                            if(ra==2){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==2){
                            if(ra==0){
                                this.root[a]=12;
                            }
                            if(ra==1){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==3){
                            this.root[a]=13;
                        }
                    }
                }


            }
        }
        //left

        if (j != 0){ //if there is left site

            if(this.root[s - 1]>=10) { //if left site is open
                lone = 1;
                b = uf.find(s - 1);

                if (a != b) {//if they are not connect

                    uf.union(s, (s - 1));
                    w = uf.find(s);
                    if (w != a){
                        if(has_list==0){
                            enqueue(i, j, this.list_num[w]);
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[w],this.list_num[a] );
                        }
                        ra = this.root[a]-10;
                        rw = this.root[w]-10;

                        if(ra==1){
                            if(rw==0){
                                this.root[w]=11;
                            }
                            if(rw==2){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==2){
                            if(rw==0){
                                this.root[w]=12;
                            }
                            if(rw==1){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==3){
                            this.root[w]=13;
                        }

                        a = w;}
                    else{
                        if(has_list==0){
                            enqueue(i, j, this.list_num[b]);
                            this.list_num[a] =this.list_num[b];
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[a],this.list_num[b] );
                        }
                        rb = this.root[b]-10;
                        ra = this.root[a]-10;

                        if(rb==1){
                            if(ra==0){
                                this.root[a]=11;
                            }
                            if(ra==2){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==2){
                            if(ra==0){
                                this.root[a]=12;
                            }
                            if(ra==1){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==3){
                            this.root[a]=13;
                        }
                    }
                }


            }
        }
        //up
        if (i != 0){ //if there is up site
            if(this.root[s - this.n]>=10) { //if left site is open
                lone = 1;
                b = uf.find(s - this.n);
                if (a != b) {
                    uf.union(s, (s - this.n));
                    w = uf.find(s);
                    if (w != a){
                        if(has_list==0){
                            enqueue(i, j, this.list_num[w]);
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[b],this.list_num[a] );
                        }
                        ra = this.root[a]-10;
                        rw = this.root[w]-10;
                        if(ra==1){
                            if(rw==0){
                                this.root[w]=11;
                            }
                            if(rw==2){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==2){
                            if(rw==0){
                                this.root[w]=12;
                            }
                            if(rw==1){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==3){
                            this.root[w]=13;
                        }

                        a = w;

                    }
                    else{
                        if(has_list==0){
                            enqueue(i, j, this.list_num[b]);
                            this.list_num[a] =this.list_num[b];
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[a],this.list_num[b] );
                        }
                        rb = this.root[b]-10;
                        ra = this.root[a]-10;
                        if(rb==1){
                            if(ra==0){
                                this.root[a]=11;
                            }
                            if(ra==2){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==2){
                            if(ra==0){
                                this.root[a]=12;
                            }
                            if(ra==1){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==3){
                            this.root[a]=13;
                        }
                    }


                }

            }
        }
        else{

            if(this.root[a]==12)
            {
                this.root[a] = 13;
                this.p = 1;
                storelist(this.list_num[a]);
            }
            if(this.root[a]==10)
            {
                this.root[a] = 11;
            }
        }
        //down
        if (i != this.n-1){ //if there is down site
            if(this.root[s + this.n]>=10) { //if down site is open
                lone = 1;
                b = uf.find(s + this.n);

                if (a != b) { //if they are not connect
                    uf.union(s, (s + this.n));
                    w = uf.find(s);
                    if (w != a){ //if root has changed, update the information of root(open, full, list_num)
                        if(has_list==0){
                            enqueue(i, j, this.list_num[w]);
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[b],this.list_num[a] );
                        }
                        ra = this.root[a]-10;
                        rw = this.root[w]-10;
                        if(ra==1){
                            if(rw==0){
                                this.root[w]=11;
                            }
                            if(rw==2){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==2){
                            if(rw==0){
                                this.root[w]=12;
                            }
                            if(rw==1){
                                this.root[w]=13;
                                this.p = 1;
                                storelist(this.list_num[w]);
                            }
                        }
                        if(ra==3){
                            this.root[w]=13;
                        }

                        a = w;

                    }
                    else{//if root has not changed
                        if(has_list==0){
                            enqueue(i, j, this.list_num[b]);
                            this.list_num[a] =this.list_num[b];
                            has_list=1;
                        }
                        else{ //if has_list==1
                            connectlist(this.list_num[a],this.list_num[b] );
                        }
                        rb = this.root[b]-10;
                        ra = this.root[a]-10;
                        if(rb==1){
                            if(ra==0){
                                this.root[a]=11;
                            }
                            if(ra==2){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==2){
                            if(ra==0){
                                this.root[a]=12;
                            }
                            if(ra==1){
                                this.root[a]=13;
                                this.p = 1;
                                storelist(this.list_num[a]);
                            }
                        }
                        if(rb==3){
                            this.root[a]=13;
                        }
                    }

                }

            }
        }
        else{

            if(this.root[a]==11)
            {
                this.root[a] = 13;
                this.p = 1;
                storelist(this.list_num[a]);
            }
            if(this.root[a]==10)
            {
                this.root[a] = 12;
            }

        }
        if(lone==0){
            enqueue(i, j, this.count);
            this.list_num[a] = this.count;
            this.count++;
        }



    }
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j){
        if(this.root[i*this.n+j]>=10){
            return true;
        }
        else{
            return false;
        }

    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j){
        int s = this.root[uf.find(i*this.n+j)];
        if(s==11 || s==13){
            return true;
        }
        else{
            return false;
        }

    }
    // does the system percolate?
    public boolean percolates(){
        if(this.p == 1){
            return true;
        }
        return false;

    }


    public Point2D[] PercolatedRegion() {
        /*this.P = new Point2D[1];
        this.P[0] = new Point2D(0,0);*/
        return this.P;
    }




    public static void main(String args[]){



    }
}
