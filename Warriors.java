import java.util.Arrays;
class Warriors {
    Node first;
    Node str;
    public class Node{
        private Integer index;
        private Node next;}
    public boolean isEmpty(){
        return first.next == null;}
    public void push(int i){
        Node oldfirst = first;
        first = new Node();
        first.index = i;
        first.next = oldfirst;}
    public int pop(){
        int i = first.index;
        first = first.next;
        return i;}
    public int[] warriors(int[] strength, int[] range) {
        int n = strength.length;
        int[] answer = new int[n * 2];
        int j, a, b, w, r1, r2, q;
        push(0);
        first.next = null;
        answer[0] = 0;
        for (var i = 1; i < n; i++) {
            b = strength[i]; //外圍index
            r2 = range[i];//外圍index 的range
            while (true) {
                j = first.index;
                a = strength[j];//stack的第一個index
                r1 = range[j];//stack的第一個index 的range
                if (b < a) { //決定i的左邊界
                    push(i);
                    if (r2 < (i-(j+1))) {
                        answer[i*2] = i-r2;}
                    else{
                        answer[i*2] = j+1;}
                    break;
                }
                if(a==b){
                    w = pop();
                    push(i);
                    if(r1<i-1-w){
                        answer[w*2+1] = w+r1;} //右邊界
                    else{
                        answer[w*2+1] = i-1;}
                    if(r2<(i-(j+1))){
                        answer[i*2] = i-r2;}
                    else{
                        answer[i*2] = j+1;}
                    break;
                }
                if (isEmpty()) {
                    //決定stack僅存index的右邊界
                    if(r1 < ((i-1)-j)) {
                        answer[j*2+1] = j+r1;}
                    else{
                        answer[j*2+1]= i-1;}
                    //把原本的pop出去
                    first.index = i;
                    first.next = null;
                    //決定新放進來的左邊界
                    if (r2 < i) {
                        answer[i * 2] = i - r2;}
                    else {
                        answer[i * 2] = 0;}
                    break;
                }
                //stack的第一個值要被pop掉，並決定其右邊界
                w = pop();
                if(r1<i-1-w){
                    answer[w*2+1] = w+r1;}
                else{
                    answer[w*2+1] = i-1;}
            }
        }

        //檢查stack還有誰
        while (true) {
            j = first.index;
            r1 = range[j];
            if (isEmpty()) {
                if(r1<n-1-j){
                    answer[j*2+1] = j+r1;}
                else{
                    answer[j*2+1] = n-1;}
                break;
            }
            if(r1<n-1-j){
                answer[j*2+1] = j+r1;}
            else{
                answer[j*2+1] = n-1;}
                first = first.next;
            }
            return answer;}

        public static void main(String[] args)  {
    }
}