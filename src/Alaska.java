import java.io.*;
import java.util.ArrayList;

public class Alaska implements Airline {



    public synchronized String toString() {
        return "<html><div style='text-align: center;'>Alaska Airlines is proud to serve the strong and knowledgeable Boilermakers from Purdue University.<br>" +
                "We primarily fly westward, and often have stops in Alaska and California.<br>" +
                "We have first class amenities, even in coach class.<br>" +
                "We provide fun snacks, such as pretzels and goldfish.<br>" +
                "We also have comfortable seats, and free WiFi.<br>" +
                "We hope you choose Alaska Airlines for your next itinerary!";
    }

    @Override
    public synchronized boolean spaceAvailable() {
        try {
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
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
        }catch (IOException e) {
        }

        return false;
    }

    @Override
    public synchronized void addPassenger(String passenger){
        try {
            File file = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
            File temp = File.createTempFile("temp-file-name", ".txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(temp));
            String line;
            String line2;
            while (!(line = br.readLine()).equals("EOF") && !(line2 = br.readLine()).equals("EOF")) {
                if (line.equals("ALASKA")) {
                    pw.println(line);
                    String[] nums = line2.split("/");
                    int add = Integer.parseInt(nums[0]) + 1;
                    line2 = "" + add + "/" + nums[1];
                    pw.println(line2);
                } else {
                    pw.println(line);
                    pw.println(line2);
                }
                if (line.equals("Alaska passenger list")) {
                    pw.println(passenger);
                    pw.println("------------------------------ALASKA");
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
    public synchronized String getPassengers() {
        try {
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
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
            File f = new File("C:\\Users\\Sabrina\\OneDrive - purdue.edu\\CS 180-BLK\\Projects\\Airport\\src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("ALASKA")) {
                    line = br.readLine();
                    break;
                }
            }
            return "ALASKA " + line;
        }catch (IOException e) {

        }
        return null;
    }
}
