package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DeathCauseStatisticList {
    List<DeathCauseStatistic> deathCauseStatisticList;

    public DeathCauseStatisticList() {
        deathCauseStatisticList = new ArrayList<>();
    }


    public void repopulate(String path){
        deathCauseStatisticList.clear();
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            br.readLine();
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                deathCauseStatisticList.add(DeathCauseStatistic.fromCsvLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public List<DeathCauseStatistic> mostDeadlyDiseases(int wiek, int n){
        HashMap<DeathCauseStatistic, Integer> mapa = new HashMap<>();
        int i = 0;
        for(DeathCauseStatistic d : deathCauseStatisticList){
            if(i == n){
                break;
            }

            DeathCauseStatistic.AgeBracketDeaths bracket = d.getAgeBracketDeaths(wiek);
            if(bracket != null){
                mapa.put(d,bracket.deathCount);
            }

            i++;

        }

        List<DeathCauseStatistic> sorted = mapa.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).map(Map.Entry::getKey).collect(Collectors.toList());
        return sorted;


    }
}
