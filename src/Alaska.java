import java.io.*;
import java.util.ArrayList;

public class Alaska implements Airline {

    ArrayList<String> passengers = new ArrayList<>();

    public String toString() {
        return "Alaska Airlines is proud to serve the strong and knowledgeable Boilermakers from Purdue University.\n" +
                "We primarily fly westward, and often have stops in Alaska and California.\n" +
                "We have first class amenities, even in coach class.\n" +
                "We provide fun snacks, such as pretzels and goldfish.\n" +
                "We also have comfortable seats, and free WiFi.\n" +
                "We hope you choose Alaska Airlines for your next itinerary!";
    }

    @Override
    public boolean spaceAvailable() throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while (!(line = br.readLine()).equals("EOF")) {
            if (line.equals("ALASKA")) {
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
            if (line.contains("-") && line.contains("ALASKA") && line2 == null) {
                pw.println(line);
                pw.println((String) null);
                pw.println(passenger);
                pw.println("------------------------------ALASKA");
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
        passengers.add(passenger);
    }
}
