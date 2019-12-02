import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

/** ReservationServer.java
 *
 * A class that houses the server of the program.
 *
 * Sources used: None
 *
 * @author Yash Bansal and Sabrina Eichenberger, CS 180 BLK, Lab Section LC1
 * @version 2019-12-01
 */

public final class ReservationServer {

    private static Gate gate = new Gate();

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(0);
        Socket clientSocket;
        ArrayList<Thread> t = new ArrayList<>();
        int connectionCount = 0;

        System.out.printf("<Now serving clients on port %d...>%n", serverSocket.getLocalPort());

        while(true) {
            try {
                clientSocket = serverSocket.accept();

            }catch (IOException e) {
                e.printStackTrace();

                break;
            }

            t.add(new Thread(new ClientHandler(clientSocket)));

            t.get(connectionCount).start();

            System.out.printf("<Client %d connected...>%n", connectionCount + 1);

            t.get(connectionCount).join();

            connectionCount++;
        }

    }

    private static class ClientHandler implements Runnable {



        private Socket clientSocket;

        ClientHandler(Socket clientSocket) {
            Objects.requireNonNull(clientSocket, "the specified client socket is null");

            this.clientSocket = clientSocket;
        }


        @Override
        public void run() {
            try {
                ObjectOutputStream cos = new
                        ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream cis = new
                        ObjectInputStream(clientSocket.getInputStream());

                Object one;
                Object two;

                while (clientSocket != null) {
                    one = cis.readObject();
                    two = cis.readObject();
                    if (one instanceof Airline && two == null) { // ifSpaceAvailable
                        cos.writeBoolean(((Airline) one).spaceAvailable());
                        cos.flush();
                    } else if (one instanceof Passenger && two instanceof Airline) { // addPassengerToList (VOID)
                        ((Airline) two).addPassenger(one.toString());
                    } else if (one instanceof Airline && two instanceof JFrame) { // getLabels
                        JLabel jl = new JLabel(((Airline) one).getCapacity());
                        JTextArea textArea = new JTextArea(6, 25);
                        textArea.setText(((Airline) one).getPassengers());
                        textArea.setEditable(false);
                        JScrollPane js = new JScrollPane(textArea);
                        cos.writeObject(jl);
                        cos.writeObject(js);
                    } else if (one == null && two == null) {
                        cos.writeObject(ReservationServer.gate);
                        cos.flush();
                    }
                }
                cos.close();
                cis.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
