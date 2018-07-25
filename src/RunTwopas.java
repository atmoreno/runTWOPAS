import java.io.*;
import java.util.ArrayList;

public class RunTwopas {

    public static void main(String[] args) throws IOException, InterruptedException {

        BufferedReader inputList = new BufferedReader(new FileReader(args[0]));
        String list = null;
        ArrayList<String> listFiles = new ArrayList<>();
        while ((list = inputList.readLine())!= null) {
            listFiles.add(list);
        }
        System.out.println("Number of scenarios to execute: " + listFiles.size());
        for (String arg : listFiles){
            Process process = new ProcessBuilder("TwoPas.exe ", arg).start();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;

            System.out.printf("Output of running %s is:", arg);
            System.out.println();

            int p = 0;
            while ((line = br.readLine()) != null & p < 22) {
                System.out.println(line);
                p++;
            }
            br.close();
            process.destroy();

            System.out.println();
        }

    }

}
