package org.example;

import java.util.ArrayList;
import java.util.List;

public class DeathCauseStatistic {
    private String code;
    private List<Integer> listaZgonow;


    public DeathCauseStatistic(String code, List<Integer> listaZgonow) {
        this.code = code;
        this.listaZgonow = listaZgonow;
    }



    public static DeathCauseStatistic fromCsvLine(String line){
        String[] parts = line.split(",");
        List<Integer> list = new ArrayList<>();
        String c = parts[0].trim();
        //zaczecie od 2 bo skipujemy kolumne total
        for(int i = 2; i < parts.length; i++){
            if(parts[i].equals("-")){
                list.add(0);
            }else{
                list.add(Integer.parseInt(parts[i]));
            }
        }

        return new DeathCauseStatistic(c, list);

    }

    @Override
    public String toString() {
        return "DeathCauseStatistic{" +
                "code='" + code + '\'' +
                ", listaZgonow=" + listaZgonow +
                '}';
    }


    public class Bracket{
        int young;
        int old;

        public Bracket(int young, int old) {
            this.young = young;
            this.old = old;
        }
    }

    public Bracket parseBracket(String line){
        String[] parts = line.split(" ");

//        for(int i = 0; i < parts.length; i++) {
//            System.out.printf("%s\n", parts[i]);
//        }
        int young = Integer.parseInt(parts[0]);

        if(young == 95){
            int old = Integer.MAX_VALUE;
            return new Bracket(young, old);

        }

        int old = Integer.parseInt(parts[2]);





        return new Bracket(young, old);
    }

    public AgeBracketDeaths getAgeBracketDeaths(int wiek) {
        String s = "0 – 4,5 - 9,10 - 14,15 - 19,20 - 24,25 - 29,30 - 34,35 - 39,40 - 44,45 - 49,50 - 54,55 - 59,60 - 64,65 - 69,70 - 74,75 - 79,80 - 84,85 - 89,90 - 94,95";
        List<Bracket> brackets = new ArrayList<>();
        String[] parts = s.split(",");

        for(int i = 0; i < parts.length; i++){
            brackets.add(parseBracket(parts[i]));
        }

        for(Bracket bracket : brackets){
            if(bracket.young >= wiek && wiek <= bracket.old){
                int index = wiek / 5;
                return new AgeBracketDeaths(bracket.young, bracket.old, listaZgonow.get(index));
            }
        }


        return null;
    }


    public class AgeBracketDeaths{
        public final int young;
        public final int old;
        public final int deathCount;


        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }


}
