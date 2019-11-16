import javax.naming.InvalidNameException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public final class ReservationClient implements Runnable, ActionListener {
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
        System.out.println("here");
        JFrame f = new JFrame("Purdue University Flight Reservation System");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,400);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        String[] airlines = {"Alaska", "Southwest", "Delta"};
        JComboBox airlineList = new JComboBox(airlines);
        airlineList.setSelectedIndex(0);
        airlineList.addActionListener(this);
        JLabel heading = new JLabel("Welcome to the Purdue University Airline Reservation Management System!");
        top.add(heading);
        top.add(airlineList);
        airlineList.setVisible(false);

        JLabel middle = new JLabel();

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exit = new JButton("Exit");
        JButton book = new JButton("Book a Flight");
        jp.add(exit);
        jp.add(book);

        //Start
        f.getContentPane().add(BorderLayout.NORTH, top);
        f.getContentPane().add(BorderLayout.CENTER, middle);
        f.getContentPane().add(BorderLayout.SOUTH, jp);
        f.setVisible(true);




    }

    public synchronized boolean isFull(String airline) {
        return false;
    }

    public synchronized void reserveSpot(String passenger, String airline) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComboBox cb = (JComboBox)actionEvent.getSource();
        //String airline = (String)cb.getSelectedItem();
        //updateLabel(airline);
    }
}
