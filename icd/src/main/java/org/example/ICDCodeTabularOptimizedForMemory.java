package org.example;

import java.io.*;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {

    @Override
    public String getDescription(String code) {
        File file = new File("icd10.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            for(int i = 0; i < 88; i++){
                br.readLine();
            }

            while((line = br.readLine()) != null){
                String[] split = line.split(" ", 2);
                if(split[0].equals(code)){
                    return split[1];
                }

            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new IndexOutOfBoundsException();
    }
}
