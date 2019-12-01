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
            File f = new File("src\\res\\reservations.txt");
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
            ArrayList<String> fileText = new ArrayList<>();

            File file = new File("src\\res\\reservations.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while (!(line = br.readLine()).equals("EOF")) {
                if (line.equals("ALASKA")) {
                    fileText.add(line);
                    line = br.readLine();
                    String[] nums = line.split("/");
                    int add = Integer.parseInt(nums[0]) + 1;
                    line = "" + add + "/" + nums[1];
                    fileText.add(line);
                } else {
                    fileText.add(line);
                }
                if (line.equals("Alaska passenger list")) {
                    line = br.readLine();
                    fileText.add(line);
                    fileText.add(passenger);
                    fileText.add("---------------------ALASKA");
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
    public synchronized String getPassengers() {
        try {
            File f = new File("src\\res\\reservations.txt");
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
            File f = new File("src\\res\\reservations.txt");
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
