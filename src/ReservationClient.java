import javax.naming.InvalidNameException;
import javax.swing.*;
import java.io.*;
import java.net.Socket;

public final class ReservationClient implements Runnable {
    public static void main(String[] args) throws IOException {
        String hostname;
        String portString;
        boolean portCor = false;
        int port = 0;
        Socket socket;


        hostname = JOptionPane.showInputDialog(null, "Enter the server's hostname: ");

        if (hostname != null) {

            while (!portCor) {
                portString = JOptionPane.showInputDialog(null, "Enter the server's port: ");
                try {
                    port = Integer.parseInt(portString);
                    portCor = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Incorrect port format");
                }
            }

            socket = new Socket(hostname, port);
        }
    }


    @Override
    public void run() {

    }

    public boolean isFull(String airline) {
        return false;
    }

    public void reserveSpot(String passenger, String airline) {

    }
}
