import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PlantUMLRunner {
    private static String path;
    public static void setPath(String path){
        PlantUMLRunner.path = path;
    }

    public static void generate(String data, String outputPath, String outputName) throws IOException, InterruptedException {
        data = "@startuml\n" + data + "\n@enduml";
        File dir = new File(outputPath);
        if(!dir.exists()){
            if(!dir.mkdirs()){
                throw new IOException("Could not create directory");
            }
        }
        File output = new File(outputPath, outputName);
        Process planUML = new ProcessBuilder("java", "-jar", path, "-pipe")
                .redirectOutput(output)
                .start();

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(planUML.getOutputStream()))) {
            bw.write(data);
        }


        int exitCode = planUML.waitFor();
        if(exitCode != 0){
            System.err.println("PlantUMLRunner exited with code " + exitCode);
        }

    }
}
