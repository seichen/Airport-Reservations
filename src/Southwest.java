import java.io.*;
import java.util.ArrayList;

public class Southwest implements Airline {

    ArrayList<String> passengers = new ArrayList<>();

    public String toString() {
        return "Southwest Airlines is proud to offer flights to Purdue University.\n" +
                "We are happy to offer free in flight wifi, as well as our amazing snacks.\n" +
                "In addition, we offer flights for much cheaper than other airlines, and offer two free checked bags.\n" +
                "We hope you choose Southwest for your next flight.";
    }

    @Override
    public boolean spaceAvailable() throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while (!(line = br.readLine()).equals("EOF")) {
            if (line.equals("SOUTHWEST")) {
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
            if (line.contains("-") && line.contains("SOUTHWEST") && line2 == null) {
                pw.println(line);
                pw.println((String) null);
                pw.println(passenger);
                pw.println("------------------------------SOUTHWEST");
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
        passengers.add(passenger);
    }
}
