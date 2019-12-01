import javax.naming.InvalidNameException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

public final class ReservationClient {

    private static Boolean spaceAvailable;
    private static JLabel capacity;
    private static JScrollPane passengerList;
    private static JLabel capa;
    private static JScrollPane passengers;
    private static Gate gate;

    public static void setGate(Gate gate) {
        ReservationClient.gate = gate;
    }

    public static void setCapa(JLabel capa) {
        ReservationClient.capa = capa;
    }

    public static void setPassengers(JScrollPane passengers) {
        ReservationClient.passengers = passengers;
    }

    public static void setSpaceAvailable(Boolean spaceAvailable) {
        ReservationClient.spaceAvailable = spaceAvailable;
    }

    public static void setCapacity(JLabel capacity) {
        ReservationClient.capacity = capacity;
    }

    public static void setPassengerList(JScrollPane passengerList) {
        ReservationClient.passengerList = passengerList;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
            ResponseListener rl = new ResponseListener(socket);

            rl.getGate(null, null);


                Alaska alaska = new Alaska();
                Southwest sw = new Southwest();
                Delta d = new Delta();
                String airline;

                Passenger p;

                JFrame f = new JFrame("Purdue University Flight Reservation System");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setSize(600,400);

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
                ImageIcon ii = new ImageIcon("src\\purdue-aviation-logo-sm.png");
                JLabel image = new JLabel(ii);
                mid.add(image);
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


                JLabel newCapacity = new JLabel();
                newCapacity.setAlignmentX(JPanel.CENTER_ALIGNMENT);

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




            AbstractAction abstractAction = new AbstractAction() {

                @Override

                public void actionPerformed(ActionEvent e) {

                    String airline = (String) airlineList.getSelectedItem();
                    JFrame jf = new JFrame("Passengers");
                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    jf.setSize(300,300);


                    if (airline.equals("Southwest")) {
                        rl.sendAndReceive(sw, jf, "passengerList");
                    } else if (airline.equals("Delta")) {
                        rl.sendAndReceive(d, jf, "passengerList");

                    } else if (airline.equals("Alaska")){
                        rl.sendAndReceive(alaska, jf, "passengerList");
                    }

                    jf.add(BorderLayout.NORTH, capacity);
                    jf.add(BorderLayout.CENTER, passengerList);
                    jf.setVisible(true);

                    JPanel cp = (JPanel) jf.getContentPane();
                    ActionMap aMap = cp.getActionMap();
                    InputMap inMap = cp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
                    inMap.put(escape, "Exit");
                    AbstractAction aa = new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jf.remove(capacity);
                            jf.remove(passengerList);
                            jf.setVisible(false);
                        }
                    };
                    aMap.put("Exit", aa);


                }


            };

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
                            image.setVisible(false);
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
                            KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, 0, true);
                            inMap.put(slashKey, "OpenAirlineList");
                            aMap.put("OpenAirlineList", abstractAction); // end of keybind

                            airlineList.setVisible(true);
                            heading.setText("Choose a flight from the drop down menu.");
                            middle.setVisible(true);
                            book.setText("Choose this flight");
                            book.setActionCommand("Step5");
                        }
                        if ("Step5".equals(actionEvent.getActionCommand())) {

                            String airline = (String) airlineList.getSelectedItem();
                            if (airline.equals("Alaska")) {
                                rl.sendAndReceive(alaska, null, "spaceAvailable");
                                if (spaceAvailable) {
                                    airlineList.setVisible(false);
                                    middle.setVisible(false);
                                    heading.setText("Are you sure that you want to book a flight on Alaska Airlines?");
                                    book.setText("Yes, I want this flight");
                                    book.setActionCommand("Step6");
                                    no.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Flight is full");
                                }
                            } else if (airline.equals("Southwest")) {
                                rl.sendAndReceive(sw, null, "spaceAvailable");
                                if (spaceAvailable) {
                                    airlineList.setVisible(false);
                                    middle.setVisible(false);
                                    heading.setText("Are you sure that you want to book a flight on Southwest Airlines?");
                                    book.setText("Yes, I want this flight");
                                    book.setActionCommand("Step6");
                                    no.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Flight is full");
                                }
                            } else {
                                rl.sendAndReceive(d, null, "spaceAvailable");
                                if (spaceAvailable) {
                                    airlineList.setVisible(false);
                                    middle.setVisible(false);
                                    heading.setText("Are you sure that you want to book a flight on Delta Airlines?");
                                    book.setText("Yes, I want this flight");
                                    book.setActionCommand("Step6");
                                    no.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Flight is full");
                                }
                            }
                        }

                        if ("Step6".equals(actionEvent.getActionCommand())) {

                            String airline = (String) airlineList.getSelectedItem();
                            if (airline.equals("Southwest")) {
                                    rl.sendAndReceive(sw, null, "spaceAvailable");

                                    if (spaceAvailable) {
                                        airlineList.setVisible(false);
                                        heading.setText("Please input your information below");
                                        mid.setVisible(true);
                                        middle.setVisible(true);
                                        middle.setText("What is your first name?");
                                        firstName.setVisible(true);
                                        last.setVisible(true);
                                        lastname.setVisible(true);
                                        age.setVisible(true);
                                        a.setVisible(true);
                                        no.setVisible(false);
                                        book.setText("Next");
                                        book.setActionCommand("Step7");
                                    }
                            } else if (airline.equals("Delta")) {
                                    rl.sendAndReceive(d, null, "spaceAvailable");

                                    if (spaceAvailable) {
                                        airlineList.setVisible(false);
                                        heading.setText("Please input your information below");
                                        mid.setVisible(true);
                                        middle.setVisible(true);
                                        middle.setText("What is your first name?");
                                        firstName.setVisible(true);
                                        last.setVisible(true);
                                        lastname.setVisible(true);
                                        age.setVisible(true);
                                        a.setVisible(true);
                                        no.setVisible(false);
                                        book.setText("Next");
                                        book.setActionCommand("Step7");
                                    }
                            } else if (airline.equals("Alaska")) {
                                    rl.sendAndReceive(alaska, null, "spaceAvailable");

                                    if (spaceAvailable) {
                                        airlineList.setVisible(false);
                                        heading.setText("Please input your information below");
                                        mid.setVisible(true);
                                        middle.setVisible(true);
                                        middle.setText("What is your first name?");
                                        firstName.setVisible(true);
                                        last.setVisible(true);
                                        lastname.setVisible(true);
                                        age.setVisible(true);
                                        a.setVisible(true);
                                        no.setVisible(false);
                                        book.setText("Next");
                                        book.setActionCommand("Step7");
                                    }
                            }

                        }

                        if ("Step7".equals(actionEvent.getActionCommand())) {

                                String f = firstName.getText();
                                boolean correctFirstName;

                                try {
                                    verifyString(f);
                                    correctFirstName = true;
                                } catch (InvalidNameException e) {
                                    correctFirstName = false;
                                    e.printStackTrace();
                                }

                                String l = lastname.getText();
                                boolean correctLastName;

                                try {
                                    verifyString(l);
                                    correctLastName = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    correctLastName = false;
                                }

                                String ageString = a.getText();
                                boolean correctAge;

                                try {
                                    int ageInteger = Integer.parseInt(ageString);
                                    correctAge = true;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    correctAge = false;
                                }

                                Boolean step8 = false;

                                if (correctFirstName && correctLastName && correctAge) {
                                    int confirm = JOptionPane.showConfirmDialog(null, "Are all the details you entered correct? \n The passenger's name is " + f + " " + l + " and their age is " + ageString + ". \n If all the information shown is correct, select the Yes button down below, otherwise, select the No button.", "Confirm Info", JOptionPane.YES_NO_OPTION);
                                    if (confirm == 1) {
                                        book.setActionCommand("Step7");
                                    } else {
                                        book.setActionCommand("Step8");
                                        step8 = true;
                                    }
                                } else {
                                    book.setActionCommand("Step7");
                                }

                                if (step8) {


                                }
                        }

                        if ("Step8".equals(book.getActionCommand())) {

                            Passenger p = new Passenger(firstName.getText().toUpperCase(), lastname.getText().toUpperCase(), Integer.parseInt(a.getText()));

                            JPanel cp = (JPanel) f.getContentPane();
                            ActionMap aMap = cp.getActionMap();
                            InputMap inMap = cp.getInputMap();
                            KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true);
                            inMap.put(slashKey, null);
                            aMap.put(slashKey, null);

                            String airline = (String) airlineList.getSelectedItem();
                            middle.setVisible(false);
                            firstName.setVisible(false);
                            last.setVisible(false);
                            lastname.setVisible(false);
                            age.setVisible(false);
                            a.setVisible(false);
                            JFrame jf = new JFrame();

                            if (airline.equals("Alaska")) {
                                rl.sendAndReceive(alaska, null, "spaceAvailable");

                                if (spaceAvailable) {
                                    rl.sendAndReceive(p, alaska, "addPassenger");
                                    heading.setText("<html><div style='text-align: center;'>Flight data displaying for Alaska Airlines <br>Enjoy your flight! <br>Flight is now boarding at Gate " + gate.getTerminal() + gate.getGate());
                                    heading.setAlignmentX(JPanel.CENTER_ALIGNMENT);
                                    rl.sendAndReceive(alaska, jf, "passenger");
                                }
                            } else if (airline.equals("Southwest")) {
                                rl.sendAndReceive(sw, null, "spaceAvailable");

                                if (spaceAvailable) {
                                    rl.sendAndReceive(p, sw, "addPassenger");
                                    heading.setText("<html><div style='text-align: center;'>Flight data displaying for Southwest Airlines <br>Enjoy your flight! <br>Flight is now boarding at Gate " + gate.getTerminal() + gate.getGate());
                                    heading.setAlignmentX(JPanel.CENTER_ALIGNMENT);
                                    rl.sendAndReceive(sw, jf, "passenger");
                                }
                            } else if (airline.equals("Delta")) {
                                rl.sendAndReceive(d, null, "spaceAvailable");

                                if (spaceAvailable) {
                                    rl.sendAndReceive(p, d, "addPassenger");
                                    heading.setText("<html><div style='text-align: center;'>Flight data displaying for Delta Airlines <br>Enjoy your flight! <br>Flight is now boarding at Gate " + gate.getTerminal() + gate.getGate());
                                    heading.setAlignmentX(JPanel.CENTER_ALIGNMENT);
                                    rl.sendAndReceive(d, jf, "passenger");
                                }
                            }
                            BoardingPass bp = new BoardingPass(gate, p);
                            JLabel pass = new JLabel(bp.toString((String) airlineList.getSelectedItem()));
                            String cap = capa.getText();
                            String[] word = cap.split(" ");
                            newCapacity.setText(word[1]);
                            mid.add(newCapacity);
                            mid.add(passengers);
                            mid.add(pass);

                            book.setActionCommand("Refresh");
                            book.setText("Refresh");

                        }

                        if ("Refresh".equals(actionEvent.getActionCommand())) {
                            JFrame jf = new JFrame();
                            String airline = (String) airlineList.getSelectedItem();
                            if (airline.equals("Alaska")) {
                                    rl.sendAndReceive(alaska, jf, "passenger");
                            } else if (airline.equals("Southwest")) {
                                    rl.sendAndReceive(sw, jf, "passenger");
                            } else if (airline.equals("Delta")) {
                                    rl.sendAndReceive(d, jf, "passenger");
                            }

                            String cap = capa.getText();
                            String[] word = cap.split(" ");
                            newCapacity.setText(word[1]);
                        }
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
                            book.setActionCommand("Step5");
                            no.setVisible(false);
                        }
                    }
                });
        }
    }

    public static void verifyString(String string) throws InvalidNameException {
        char[] stringAsChars = string.toCharArray();
        for (int i = 0; i < stringAsChars.length; i++) {
            if (Character.isDigit(stringAsChars[i])) {
                throw new InvalidNameException("Please enter a valid name.");
            }
        }
    }

    private static class ResponseListener {

        Socket socket;
        ObjectOutputStream cos;
        ObjectInputStream cis;


        ResponseListener (Socket clientSocket) throws IOException, ClassNotFoundException {
            Objects.requireNonNull(clientSocket, "the specified client socket is null");
            socket = clientSocket;

            cos = new ObjectOutputStream(socket.getOutputStream());
            cis = new ObjectInputStream(socket.getInputStream());
        }

        synchronized void getGate(Object one, Object two) {
            try {
                cos.writeObject(one);
                cos.flush();
                cos.writeObject(two);
                cos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Gate gate = (Gate) cis.readObject();
                ReservationClient.setGate(gate);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        synchronized void sendAndReceive(Object one, Object two, String todo) {

            try {
                cos.writeObject(one);
                cos.flush();
                cos.writeObject(two);
                cos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if ("spaceAvailable".equals(todo)) {
                try {
                    Boolean temp = cis.readBoolean();
                    ReservationClient.setSpaceAvailable(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if ("addPassenger".equals(todo)) {
                // nothing
            } else if ("passengerList".equals(todo)) {
                try {
                    JLabel jl = (JLabel) cis.readObject();
                    JScrollPane textArea = (JScrollPane) cis.readObject();
                    ReservationClient.setCapacity(jl);
                    ReservationClient.setPassengerList(textArea);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else if ("passenger".equals(todo)) {
                try {
                    JLabel jl = (JLabel) cis.readObject();
                    JScrollPane textArea = (JScrollPane) cis.readObject();
                    ReservationClient.setCapa(jl);
                    ReservationClient.setPassengers(textArea);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
