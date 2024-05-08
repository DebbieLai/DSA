

import edu.princeton.cs.algs4.MaxPQ;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;


class Event{
    int index;
    int city1;
    int city2;
    int date1;
    int date2;
    int num;
    Event(int Index, int Num, int City1, int City2, int Date1, int Date2){
        index = Index; //3 virus attack //2 traveller //1 leave
        num = Num;
        city1 = City1;
        city2 = City2;
        date1 = Date1;
        date2 = Date2;
    }


}

class City{
    // optional, for reference only
    int People;

    int cityinfecteddate;
    int recoverydate;
    int Index;
    int infected;


    City(int index,int people){
        Index = index;
        People = people;
        cityinfecteddate = 0;
        recoverydate = 0;
        infected = 0;


    }
}

class CovidSimulation {

    private int Date = 1;
    int countdate = 0;

    int n;

    City[] cc;
    Event[] ee = new Event[100000];

    MinPQ<Event> epq = new MinPQ<>(new epq());

    public class epq implements Comparator<Event>{
        public int compare(Event a,Event b){
            if (a.date1 > b.date1){
                return 1; //a排後面
            }
            else if (a.date1 == b.date1){
                if (a.index < b.index){
                    return 1;
                }
//                else if (a.index==b.index && a.date2 <b.date2){  //a排後面 a是0 b不是0
//                    return 1;
//                }
                else{
                    return -1;
                }
            }
            return -1;
        }
    }

    int eventnum = 0;




    public CovidSimulation(int[] Num_Of_Citizen) {
        n = Num_Of_Citizen.length;
        cc = new City[n];
        for(int i = 0 ; i < n; i++){
            cc[i] = new City( i,Num_Of_Citizen[i]);
        }
    }
    public void virusAttackPlan(int city, int date){

        ee[eventnum] = new Event(3,0,city,0,date,0);
        epq.insert(ee[eventnum]);
        eventnum++;



    }
    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        ee[eventnum] = new Event(1,NumberOfTraveller,FromCity,ToCity,DateOfDeparture,DateOfArrival);
        epq.insert(ee[eventnum]);
        eventnum++;
    }
    public int CityWithTheMostPatient(int date){

        if (!epq.isEmpty()){
            int d = epq.min().date1;
            while (d<=date){

                //判斷是甚麼狀況
                int event = epq.min().index;


                switch (event){
                    case 1:
                        int d1,d2,N,c1,c2;
                        N = epq.min().num;
                        d1 = epq.min().date1; //recoveryday
                        d2 = epq.min().date2;
                        c1 = epq.min().city1;
                        c2 = epq.min().city2;


                        if (d2>=cc[c1].recoverydate){
                            d1 = 0;
                        }
                        else{
                            d1 = cc[c1].recoverydate;
                        }

                        cc[c1].People -= N;
                        //刪掉最小值
                        epq.delMin();
                        //加入新值


                        ee[eventnum] = new Event(2,N,0,c2,d2,d1);
                        epq.insert(ee[eventnum]);
                        eventnum++;

                        break;


                    case 2:

                        //把Number of traveller加到city people
                        int x1 = epq.min().city2;
                        cc[x1].People += epq.min().num;

                        int recoverday = epq.min().date2;


                        if (recoverday!=0){
                            //city無病菌
                            if (cc[x1].recoverydate <= d){
                                cc[x1].cityinfecteddate = d;
                                cc[x1].recoverydate = d+4;
                            }
                            //city本來就有病菌
                            else{
                                //是否超過8天期限
                                //是否比現在的recovery date還晚
                                int rd = cc[x1].recoverydate;
                                int id = cc[x1].cityinfecteddate;

                                if (recoverday>rd){
                                    //至多8天
                                    if (recoverday-id>=7){
                                        cc[x1].recoverydate = id+7;

                                    }
                                    else{
                                        cc[x1].recoverydate = recoverday;
                                    }
                                }


                            }
                        }
                        epq.delMin();
                        break;


                    case 3: //virus attack


                        int x2 = epq.min().city1;

                        if (d >= cc[x2].recoverydate){
                            cc[x2].cityinfecteddate = d;
                            cc[x2].recoverydate= d+4;
                        }
                        epq.delMin();



                }

//                System.out.print("day ");
//                System.out.print(d);
//                System.out.print(": ");


//                for(int i = 0;i<n;i++){
//                    if(cc[i].recoverydate <= d){
////                        System.out.print(0);
//                        System.out.print(cc[i].People);
//                    }
//                    else{
//                        System.out.print(cc[i].People);
//                    }
//                    System.out.print(" ");
//                }
//                System.out.println("");


                if (epq.isEmpty()){
                    break;
                }
                d = epq.min().date1;



            }
        }
        countdate = date;

        int answer  = 0 ;
        class cp implements Comparator<City> {
            public int compare(City a,City b){
                if (a.infected>b.infected){
                    return 1;
                }
                else if (a.infected==b.infected){
                    if (a.Index >b.Index){
                        return 1;
                    }
                    else{
                        return -1;
                    }
                }
                return -1;
            }
        }
        MaxPQ<City> pq = new MaxPQ<>(new cp());



        for(int i = 0;i<n;i++){
            if(cc[i].recoverydate > date){

                cc[i].infected = cc[i].People;
                pq.insert(cc[i]);


            }



        }

//
//        System.out.print("day(recover) ");
//        System.out.print(date);
//        System.out.print(": ");
//
//        for(int i = 0;i<n;i++){
//            System.out.print(cc[i].recoverydate);
//            System.out.print(" ");
//
//        }
//        System.out.println(" ");
//
//
//        System.out.print("day(infecte) ");
//        System.out.print(date);
//        System.out.print(": ");
//
//        for(int i = 0;i<n;i++){
//            System.out.print(cc[i].infected);
//            System.out.print(" ");
//
//        }
//        System.out.println(" ");




        if(pq.isEmpty()){
            return -1;
        }
        else{
            answer = pq.max().Index;
            return answer;
        }

    }



    public static void main(String[] args) {

        //infected city: in coming with more recent virus
//        CovidSimulation sol = new CovidSimulation(new int[] {500, 100, 50, 250});
//        sol.virusAttackPlan(0, 3);
//        sol.virusAttackPlan(1, 1);
//        sol.virusAttackPlan(3, 3);
//
//        sol.TravelPlan(200, 0, 3, 1, 5);
//        sol.TravelPlan(50, 1, 0, 1, 4);
//        sol.TravelPlan(150, 0, 2, 1, 3);
//        sol.TravelPlan(50, 3, 2, 1, 2);//ok
//        sol.TravelPlan(10, 1, 3, 1, 2);//ok
//        sol.TravelPlan(70, 3, 0, 2, 3);
//
//        System.out.println(sol.CityWithTheMostPatient(1));
//        System.out.println(sol.CityWithTheMostPatient(2));
//        System.out.println(sol.CityWithTheMostPatient(3));
//        System.out.println(sol.CityWithTheMostPatient(4));
//        System.out.println(sol.CityWithTheMostPatient(5));
//        System.out.println(sol.CityWithTheMostPatient(6));
//        System.out.println(sol.CityWithTheMostPatient(8));



    }
}