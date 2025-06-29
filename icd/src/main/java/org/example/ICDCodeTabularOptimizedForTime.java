package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {

    HashMap<String, String> map;

    public ICDCodeTabularOptimizedForTime() {
        this.map = new HashMap<>();
    }





    public void load() {
        HashMap<String, String> m = new HashMap<>();
        File file = new File("icd10.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            for(int i = 0; i < 88; i++){
                br.readLine();
            }

            while((line = br.readLine()) != null){
                String[] split = line.split(" ", 2);
                if (split.length < 2) {
                    continue;
                }
                String c = split[0];
                if(!c.isEmpty()){
                    if (Character.isLetter(c.charAt(0))) {
                        if(!split[1].isEmpty()){
                            m.put(c, split[1]);
                        }

//                    System.out.println("tak");
                    }
                }

            }

            this.map = m;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getDescription(String code) {
        if(map.isEmpty()){
            throw new IndexOutOfBoundsException();
        }
        return map.get(code);
    }


    public HashMap<String, String> getMap() {
        return map;
    }
}
