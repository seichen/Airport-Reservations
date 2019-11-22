import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public final class ReservationServer {

    public static void main(String[] args) throws IOException {
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

            connectionCount++;

            System.out.printf("<Client %d connected...>%n", connectionCount);
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
                    if (one instanceof Airline && two == null) {
                        cos.writeBoolean(((Airline) one).spaceAvailable());
                        cos.flush();
                    } else if (one instanceof Passenger && two instanceof Airline) {
                        ((Airline) two).addPassenger(one.toString());
                    } else if (one instanceof  Airline && two instanceof Airline) {
                        cos.writeObject(one.toString());
                        cos.flush();
                    } else if (one instanceof Airline && two instanceof JFrame) {
                        JLabel jl = new JLabel(((Airline) one).getCapacity());
                        JTextArea textArea = new JTextArea(6, 25);
                        textArea.setText(((Airline) one).getPassengers());
                        textArea.setEditable(false);
                        cos.writeObject(jl);
                        cos.writeObject(textArea);
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
