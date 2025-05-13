package org.example;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        DeathCauseStatistic deathCauseStatistic = DeathCauseStatistic.fromCsvLine("A02.1          ,5,-,-,-,-,-,-,-,-,-,-,-,-,1,2,-,1,1,-,-,-");
//        System.out.println(deathCauseStatistic);
//
        DeathCauseStatisticList deathCauseStatisticList = new DeathCauseStatisticList();
        deathCauseStatisticList.repopulate("zgony.csv");

        List<DeathCauseStatistic> list = deathCauseStatisticList.mostDeadlyDiseases(42,22);
        System.out.println(list);
        ICDCodeTabularOptimizedForMemory i = new ICDCodeTabularOptimizedForMemory();
        System.out.println(i.getDescription("A04"));
        ICDCodeTabularOptimizedForTime it = new ICDCodeTabularOptimizedForTime();
        it.load();
        System.out.println(it.getDescription("A03"));






//        HashMap<String, String> map = new HashMap<>();
//        map = it.getMap();
//        System.out.println(map);

//        String c = " A04";
//        if(Character.isLetter(c.charAt(0))){
//            System.out.println("tak");
//        }
    }
}