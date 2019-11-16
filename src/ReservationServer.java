import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

            t.add(new Thread());

            t.get(connectionCount).start();

            connectionCount++;

            System.out.printf("<Client %d connected...>%n", connectionCount);
        }

    }


}
