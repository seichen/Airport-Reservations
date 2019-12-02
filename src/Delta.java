import java.io.*;
import java.util.ArrayList;

/** Delta.java
 *
 * A class that implements the airline interface. This is one of three airlines used in the project.
 *
 * Sources used: None
 *
 * @author Yash Bansal and Sabrina Eichenberger, CS 180 BLK, Lab Section LC1
 * @version 2019-12-01
 */

public class Delta implements Airline {

    public synchronized String toString() {
        return "<html><div style='text-align: center;'>Delta Airlines is proud too be one of the five premier " +
                "Airlines at Purdue University<br>" +
                "We offer exceptional services, with free limited WiFi for all customers.<br>" +
                "Passengers who use T-Mobile as a cell phone carrier get additional benefits.<br>" +
                "We are also happy to offer power outlets in each seat for passenger use.<br>" +
                "We hope you choose to fly Delta as your next Airline.";
    }

    @Override
    public synchronized boolean spaceAvailable(){
        try {
            File f = new File("src\\res\\reservations.txt");
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
        } catch (IOException e) {

        }
        return false;
    }

    @Override
    public synchronized void addPassenger(String passenger){
        try {
            ArrayList<String> fileText = new ArrayList<>();

            File file = new File("src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("DELTA")) {
                    fileText.add(line);
                    line = br.readLine();
                    String[] nums = line.split("/");
                    int add = Integer.parseInt(nums[0]) + 1;
                    line = "" + add + "/" + nums[1];
                    fileText.add(line);
                } else {
                    fileText.add(line);
                }
                if (line.equals("Delta passenger list")) {
                    line = br.readLine();
                    fileText.add(line);
                    fileText.add(passenger);
                    fileText.add("---------------------DELTA");
                }
            }
            fileText.add("EOF");
            PrintWriter pw = new PrintWriter(new FileWriter(file, false));
            for (int i = 0; i < fileText.size(); i++) {
                pw.println(fileText.get(i));
                pw.flush();
            }
            br.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized String getPassengers(){
        try {
            File f = new File("src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            ArrayList<String> passengers = new ArrayList<>();
            boolean start = false;

            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("SOUTHWEST")) {
                    start = false;
                }
                if (start && !line.contains("-") && !line.equals("")) {
                    passengers.add(line);
                }
                if (line.equals("Delta passenger list")) {
                    start = true;
                }
            }
            String passengersText = "";
            for (int i = 0; i < passengers.size(); i++) {
                passengersText += passengers.get(i) + "\n";
            }
            return passengersText;
        } catch (IOException e) {

        }
        return null;
    }

    @Override
    public synchronized String getCapacity(){
        try {
            File f = new File("src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("DELTA")) {
                    line = br.readLine();
                    break;
                }
            }
            return "DELTA " + line;
        } catch (IOException e) {

        }
        return null;
    }
}
