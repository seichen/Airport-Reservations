import java.io.*;
import java.util.ArrayList;

public class Southwest implements Airline {

    public String toString() {
        return "<html><div style='text-align: center;'>Southwest Airlines is proud to offer flights to Purdue University.<br>" +
                "We are happy to offer free in flight wifi, as well as our amazing snacks.<br>" +
                "In addition, we offer flights for much cheaper than other airlines, and offer two free checked bags.<br>" +
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
            if (line.equals("SOUTHWEST")) {
                pw.println(line);
                String[] nums = line2.split("/");
                int add = Integer.parseInt(nums[0]) + 1;
                line2 = "" + nums[0] + "/" + nums[1];
                pw.println(line2);
            } else {
                pw.println(line);
                pw.println(line2);
            }
            if (line.contains("-") && line.contains("SOUTHWEST") && line2 == null) {
                pw.println(passenger);
                pw.println("------------------------------SOUTHWEST");
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
            if (start && !line.contains("-") && !line.equals("")) {
                passengers.add(line);
            }
            if (line.equals("Southwest passenger list")) {
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
            if (line.equals("SOUTHWEST")) {
                line = br.readLine();
                break;
            }
        }
        return line;
    }
}
