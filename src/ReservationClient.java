import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        String airline;

        JFrame f = new JFrame("Purdue University Flight Reservation System");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,600);

        JPanel top = new JPanel(); //top panel
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

        JPanel mid = new JPanel(); // middle panel
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

        airlineList.addActionListener(new ActionListener() { //JComboBox for airline selection
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

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER)); // bottom panel
        JButton exit = new JButton("Exit");
        JButton no = new JButton("No, I want a different flight.");
        JButton book = new JButton("Book a Flight");
        book.setActionCommand("Step3");
        jp.add(exit);
        exit.setActionCommand("Exit");
        jp.add(no);
        no.setActionCommand("no");
        no.setVisible(false);
        jp.add(book);

        //Start
        f.getContentPane().add(BorderLayout.NORTH, top);
        f.getContentPane().add(BorderLayout.CENTER, mid);
        f.getContentPane().add(BorderLayout.SOUTH, jp);
        f.setVisible(true);

        exit.addActionListener(new ActionListener() { // exit button
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("Exit".equals(actionEvent.getActionCommand())) {
                    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
                    f.setVisible(false);
                    f.dispose();
                }
            }
        });

        book.addActionListener(new ActionListener() { // MAIN BUTTON -- DO EVERYTHING HERE
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if ("Step3".equals(actionEvent.getActionCommand())) {
                    heading.setText("Do you want to book a flight today?");
                    heading.updateUI();
                    book.setText("Yes, I want to book a flight");
                    book.updateUI();
                    book.setActionCommand("Step4");
                }
                if ("Step4".equals(actionEvent.getActionCommand())) {

                    JPanel cp = (JPanel) f.getContentPane(); //START OF KEY BIND
                    ActionMap aMap = cp.getActionMap();
                    InputMap inMap = cp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true);
                    inMap.put(slashKey, "OpenAirlineList");
                    AbstractAction abstractAction = new AbstractAction() {

                        @Override

                        public void actionPerformed(ActionEvent e) {

                            String airline = (String) airlineList.getSelectedItem();

                            if (airline.equals("Southwest")) {
                                JTextArea textArea = new JTextArea(6, 25);
                                textArea.setText("Southwest " + sw.getCapacity() + "\n" + sw.getPassengers());
                                textArea.setEditable(false);
                                JScrollPane scrollPane = new JScrollPane(textArea);
                                JOptionPane.showMessageDialog(null, scrollPane, "Passengers",
                                        JOptionPane.PLAIN_MESSAGE);
                            } else if (airline.equals("Delta")) {
                                JTextArea textArea = new JTextArea(6, 25);
                                textArea.setText("Delta " + d.getCapacity() + "\n" + d.getPassengers());
                                textArea.setEditable(false);
                                JScrollPane scrollPane = new JScrollPane(textArea);
                                JOptionPane.showMessageDialog(null, scrollPane, "Passengers",
                                        JOptionPane.PLAIN_MESSAGE);

                            } else if (airline.equals("Alaska")){
                                JTextArea textArea = new JTextArea(6, 25);
                                textArea.setText("Alaska " + alaska.getCapacity() + "\n" + alaska.getPassengers());
                                textArea.setEditable(false);
                                JScrollPane scrollPane = new JScrollPane(textArea);
                                JOptionPane.showMessageDialog(null, scrollPane, "Passengers",
                                        JOptionPane.PLAIN_MESSAGE);

                            }

                        }


                    };
                    aMap.put("OpenAirlineList", abstractAction);

                    airlineList.setVisible(true);
                    heading.setText("Choose a flight from the drop down menu.");
                    middle.setVisible(true);
                    book.setText("Choose this flight");
                    book.setActionCommand("Step5");
                }
                if ("Step5".equals(actionEvent.getActionCommand())) {

                    String airline = (String) airlineList.getSelectedItem();
                    if (airline.equals("Alaska")) {
                        if (alaska.spaceAvailable()) {
                            airlineList.setVisible(false);
                            middle.setVisible(false);
                            heading.setText("Are you sure that you want to book a flight on Alaska Airlines?");
                            book.setText("Yes, I want this flight");
                            book.setActionCommand("toStep6");
                            no.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Flight is full");
                        }
                    } else if (airline.equals("Southwest")) {
                            if (sw.spaceAvailable()) {
                                airlineList.setVisible(false);
                                middle.setVisible(false);
                                heading.setText("Are you sure that you want to book a flight on Alaska Airlines?");
                                book.setText("Yes, I want this flight");
                                book.setActionCommand("toStep6");
                                no.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Flight is full");
                            }
                    } else {
                            if (d.spaceAvailable()) {
                                airlineList.setVisible(false);
                                middle.setVisible(false);
                                heading.setText("Are you sure that you want to book a flight on Alaska Airlines?");
                                book.setText("Yes, I want this flight");
                                book.setActionCommand("toStep6");
                                no.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Flight is full");
                            }
                    }
                }
                // STEP 6

                // STEP 7
                /* JPanel cp = (JPanel) f.getContentPane();
                ActionMap aMap = cp.getActionMap();
                InputMap inMap = cp.getInputMap();
                KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true);
                inMap.put(slashKey, null);
                aMap.put(slashKey, null);
                ^^^^ input this into step 7 ^^^^
                */

                // STEP 8
            }
        });

        no.addActionListener(new ActionListener() { // no button
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if ("no".equals(actionEvent.getActionCommand())) {
                    airlineList.setVisible(true);
                    heading.setText("Choose a flight from the drop down menu.");
                    middle.setVisible(true);
                    book.setText("Choose this flight");
                    book.setActionCommand("toStep5");
                }
            }
        });

    }
}
