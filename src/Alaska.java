import java.io.*;
import java.util.ArrayList;

public class Alaska implements Airline {



    public String toString() {
        return "<html><div style='text-align: center;'>Alaska Airlines is proud to serve the strong and knowledgeable Boilermakers from Purdue University.<br>" +
                "We primarily fly westward, and often have stops in Alaska and California.<br>" +
                "We have first class amenities, even in coach class.<br>" +
                "We provide fun snacks, such as pretzels and goldfish.<br>" +
                "We also have comfortable seats, and free WiFi.<br>" +
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
            if (line.equals("ALASKA")) {
                pw.println(line);
                String[] nums = line2.split("/");
                int add = Integer.parseInt(nums[0]) + 1;
                line2 = "" + nums[0] + "/" + nums[1];
                pw.println(line2);
            } else {
                pw.println(line);
                pw.println(line2);
            }
            if (line.contains("-") && line.contains("ALASKA") && line2 == null) {
                pw.println(passenger);
                pw.println("------------------------------ALASKA");
            }
        }
        br.close();
        pw.close();
        file.delete();
        temp.renameTo(file);
    }

    @Override
    public ArrayList<String> getPassengers() throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        ArrayList<String> passengers = new ArrayList<>();
        boolean start = false;

        while (!(line = br.readLine()).equals("EOF")) {
            if (line.equals("DELTA")) {
                start = false;
            }
            if (start && !line.contains("-") && !line.equals("")) {
                passengers.add(line);
            }
            if (line.equals("Alaska passenger list")) {
                start = true;
            }
        }
        return passengers;
    }

    @Override
    public String getCapacity() throws IOException {
        File f = new File("reservations.txt");
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;

        while (!(line = br.readLine()).equals("EOF")) {
            if (line.equals("ALASKA")) {
                line = br.readLine();
                break;
            }
        }
        return line;
    }
}
