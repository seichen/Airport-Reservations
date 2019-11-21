import java.io.*;
import java.util.ArrayList;

public class Delta implements Airline {

    public String toString() {
        return "<html><div style='text-align: center;'>Delta Airlines is proud too be one of the five premier Airlines at Purdue University<br>" +
                "We offer exceptional services, with free limited WiFi for all customers.<br>" +
                "Passengers who use T-Mobile as a cell phone carrier get additional benefits.<br>" +
                "We are also happy to offer power outlets in each seat for passenger use.<br>" +
                "We hope you choose to fly Delta as your next Airline.";
    }

    @Override
    public boolean spaceAvailable(){
        try {
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
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
    public void addPassenger(String passenger){
        try {
            File file = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
            File temp = File.createTempFile("temp-file-name", ".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(temp));
            String line;
            String line2;
            while (!(line = br.readLine()).equals("EOF") && !(line2 = br.readLine()).equals("EOF")) {
                if (line.equals("DELTA")) {
                    pw.println(line);
                    String[] nums = line2.split("/");
                    int add = Integer.parseInt(nums[0]) + 1;
                    line2 = "" + add + "/" + nums[1];
                    pw.println(line2);
                } else {
                    pw.println(line);
                    pw.println(line2);
                }
                if (line.equals("Delta passenger list")) {
                    pw.println(passenger);
                    pw.println("------------------------------DELTA");
                }
            }
            br.close();
            pw.close();
            file.delete();
            temp.renameTo(file);
        } catch (IOException e) {

        }
    }

    @Override
    public String getPassengers(){
        try {
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
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
    public String getCapacity(){
        try {
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("DELTA")) {
                    line = br.readLine();
                    break;
                }
            }
            return line;
        } catch (IOException e) {

        }
        return null;
    }
}
