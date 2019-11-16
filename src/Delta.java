import java.io.*;
import java.util.ArrayList;

public class Delta implements Airline {

    ArrayList<String> passengers = new ArrayList<>();

    public String toString() {
        return "Delta Airlines is proud too be one of the five premier Airlines at Purdue University\n" +
                "We offer exceptional services, with free limited WiFi for all customers.\n" +
                "Passengers who use T-Mobile as a cell phone carrier get additional benefits.\n" +
                "We are also happy to offer power outlets in each seat for passenger use.\n" +
                "We hope you choose to fly Delta as your next Airline.";
    }

    @Override
    public boolean spaceAvailable() throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while (!(line = br.readLine()).equals("EOF")) {
            if (line.equals("DELTA")) {
                line = br.readLine();
                break;
            }
        }

        String[] numbers = line.split("/");
        if (Integer.parseInt(numbers[0]) >= Integer.parseInt(numbers[1])) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addPassenger(String passenger) throws IOException {
        File file = new File("reservations.txt");
        File temp = File.createTempFile("temp-file-name", ".txt");
        BufferedReader br = new BufferedReader(new FileReader( file ));
        PrintWriter pw =  new PrintWriter(new FileWriter( temp ));
        String line;
        String line2;
        while (!(line = br.readLine()).equals("EOF") && !(line2 = br.readLine()).equals("EOF")) {
            if (line.contains("-") && line.contains("DELTA") && line2 == null) {
                pw.println(line);
                pw.println((String) null);
                pw.println(passenger);
                pw.println("------------------------------DELTA");
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
        passengers.add(passenger);
    }
}
