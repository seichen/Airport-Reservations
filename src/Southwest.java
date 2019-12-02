import java.io.*;
import java.util.ArrayList;

/** Southwest.java
 *
 * A class that implements the airline interface. This is one of three airlines used in the project.
 *
 * Sources used: None
 *
 * @author Yash Bansal and Sabrina Eichenberger, CS 180 BLK, Lab Section LC1
 * @version 2019-12-01
 */

public class Southwest implements Airline {

    public synchronized String toString() {
        return "<html><div style='text-align: center;'>Southwest Airlines is proud to offer flights to " +
                "Purdue University.<br>" +
                "We are happy to offer free in flight wifi, as well as our amazing snacks.<br>" +
                "In addition, we offer flights for much cheaper than other airlines, and offer two free checked bags." +
                "<br>" +
                "We hope you choose Southwest for your next flight.";
    }

    @Override
    public synchronized boolean spaceAvailable(){
        try {
            File f = new File("src\\res\\reservations.txt");
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
                if (line.equals("SOUTHWEST")) {
                    fileText.add(line);
                    line = br.readLine();
                    String[] nums = line.split("/");
                    int add = Integer.parseInt(nums[0]) + 1;
                    line = "" + add + "/" + nums[1];
                    fileText.add(line);
                } else {
                    fileText.add(line);
                }
                if (line.equals("Southwest passenger list")) {
                    line = br.readLine();
                    fileText.add(line);
                    fileText.add(passenger);
                    fileText.add("---------------------SOUTHWEST");
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
                if (start && !line.contains("-") && !line.equals("") && !line.contains("SOUTHWEST")) {
                    passengers.add(line);
                }
                if (line.equals("Southwest passenger list")) {
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
                if (line.equals("SOUTHWEST")) {
                    line = br.readLine();
                    break;
                }
            }
            return "SOUTHWEST " + line;
        } catch (IOException e) {

        }
        return null;
    }
}
