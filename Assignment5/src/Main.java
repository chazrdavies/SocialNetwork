import java.lang.constant.MethodTypeDesc;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws PersonNotFoundException {

        SocialConnections instagram = new MySocialConnection();

        instagram.addPerson("alex");
        instagram.addPerson("ben");
        instagram.addPerson("tom");
        instagram.addPerson("nadine");
        instagram.addPerson("chaz");
        instagram.addPerson("corey");
        instagram.addPerson("hugh");


        instagram.connectPeople("chaz", "alex");
        instagram.connectPeople("chaz", "tom");
        instagram.connectPeople("chaz", "ben");
        instagram.connectPeople("ben", "corey");
        instagram.connectPeople("alex", "hugh");
        instagram.connectPeople("corey", "nadine");
        instagram.connectPeople("alex", "corey");


        LinkedList<String> list = (LinkedList<String>) instagram.getConnections("chaz");


        for(String n : list){
            System.out.print(n + " ");
        }

        if(instagram.areWeAllConnected()){
            System.out.println("Instagram is all connected");
        }else{
            System.out.println("Instagram is NOT all connected");
        }


        SocialConnections bereal = new MySocialConnection();

        bereal.addPerson("alex");
        bereal.addPerson("ben");
        bereal.addPerson("tom");
        bereal.addPerson("nadine");
        bereal.addPerson("chaz");
        bereal.addPerson("corey");
        bereal.addPerson("hugh");
        bereal.addPerson("matt");


        bereal.connectPeople("chaz", "alex");
        bereal.connectPeople("chaz", "tom");
        bereal.connectPeople("chaz", "ben");
        bereal.connectPeople("ben", "corey");
        bereal.connectPeople("alex", "hugh");
        bereal.connectPeople("corey", "nadine");
        bereal.connectPeople("alex", "corey");
        bereal.connectPeople("tom", "matt");
        bereal.connectPeople("matt", "corey");

        if(bereal.areWeAllConnected()){
            System.out.println("Bereal is all connected");
        }else{
            System.out.println("Bereal is NOT all connected");
        }

        int minDegree = bereal.getMinimumDegreeOfSeparation("chaz", "corey");
        System.out.println("Min num of degrees is " + minDegree);


        List<String> firstDegree = bereal.getConnectionsToDegree("chaz", 1);
        List<String> secondDegree = bereal.getConnectionsToDegree("chaz", 2);
        List<String> thirdDegree = bereal.getConnectionsToDegree("chaz", 3);

        System.out.println("First degree from chaz:");
        for(String p1 : firstDegree){
            System.out.print(p1 + " ");
            System.out.println();
        }
        System.out.println("second degree from chaz:");
        for(String p2 : secondDegree){
            System.out.print(p2 + " ");
            System.out.println();
        }
        System.out.println("third degree from chaz:");
        for(String p3 : thirdDegree){
            System.out.print(p3 + " ");
            System.out.println();
        }



        bereal.addPerson("Steve");

        int num = bereal.getMinimumDegreeOfSeparation("chaz", "Steve");

        System.out.println("Num " + num);

        for(String p1 : firstDegree){
            System.out.print(p1 + " ");
            System.out.println();
        }
        System.out.println("second degree from chaz:");
        for(String p2 : secondDegree){
            System.out.print(p2 + " ");
            System.out.println();
        }
        System.out.println("third degree from chaz:");
        for(String p3 : thirdDegree){
            System.out.print(p3 + " ");
            System.out.println();
        }

    }
}