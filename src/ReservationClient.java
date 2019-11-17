import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        Alaska alaska = new Alaska();
        Southwest sw = new Southwest();
        Delta d = new Delta();

        JFrame f = new JFrame("Purdue University Flight Reservation System");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,600);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        String[] airlines = {"Alaska", "Southwest", "Delta"};
        JComboBox<String> airlineList = new JComboBox<>(airlines);
        airlineList.setSelectedIndex(0);
        JLabel heading = new JLabel("Welcome to the Purdue University Airline Reservation Management System!");
        heading.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        airlineList.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        top.add(heading);
        top.add(airlineList);
        airlineList.setVisible(false);

        JPanel mid = new JPanel();
        mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
        JLabel middle = new JLabel(alaska.toString());
        middle.setHorizontalAlignment(JLabel.CENTER);
        JTextField firstName = new JTextField();
        JLabel last = new JLabel("What is your last name?");
        JTextField lastname = new JTextField();
        JLabel age = new JLabel("What is your age?");
        JTextField a = new JTextField();
        mid.add(middle);
        mid.add(firstName);
        mid.add(last);
        mid.add(lastname);
        mid.add(age);
        mid.add(a);
        middle.setVisible(false);
        firstName.setVisible(false);
        last.setVisible(false);
        lastname.setVisible(false);
        age.setVisible(false);
        a.setVisible(false);

        airlineList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JComboBox cb = (JComboBox)actionEvent.getSource();
                String airline = (String)cb.getSelectedItem();
                if (airline.equals("Alaska")) {
                    middle.setText(alaska.toString());
                } else if (airline.equals("Southwest")) {
                    middle.setText(sw.toString());
                } else if (airline.equals("Delta")) {
                    middle.setText(d.toString());
                }
            }
        });

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exit = new JButton("Exit");
        JButton no = new JButton();
        JButton book = new JButton("Book a Flight");
        book.setActionCommand("toStep3");
        jp.add(exit);
        jp.add(no);
        no.setVisible(false);
        jp.add(book);

        //Start
        f.getContentPane().add(BorderLayout.NORTH, top);
        f.getContentPane().add(BorderLayout.CENTER, mid);
        f.getContentPane().add(BorderLayout.SOUTH, jp);
        f.setVisible(true);

        book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("toStep3".equals(actionEvent.getActionCommand())) {
                    heading.setText("Do you want to book a flight today?");
                    heading.updateUI();
                    book.setText("Yes, I want to book a flight");
                    book.updateUI();
                    book.setActionCommand("toStep4");
                }
                if ("toStep4".equals(actionEvent.getActionCommand())) {
                    airlineList.setVisible(true);
                    heading.setText("Choose a flight from the drop down menu.");
                    middle.setVisible(true);
                    book.setText("Choose this flight");
                    book.setActionCommand("toStep5");
                }
            }
        });


    }

    public synchronized boolean isFull(String airline) {
        return false;
    }

    public synchronized void reserveSpot(String passenger, String airline) {

    }
}
