import javax.naming.InvalidNameException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public final class ReservationClient {

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

            try {
                ObjectOutputStream cos = new
                        ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream cis = new
                        ObjectInputStream(socket.getInputStream());


                Alaska alaska = new Alaska();
                Southwest sw = new Southwest();
                Delta d = new Delta();
                Gate g = new Gate();
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
                JLabel first = new JLabel("What is your first name?");
                JTextField firstName = new JTextField();
                JLabel last = new JLabel("What is your last name?");
                JTextField lastname = new JTextField();
                JLabel age = new JLabel("What is your age?");
                JTextField a = new JTextField();
                mid.add(middle);
                mid.add(first);
                mid.add(firstName);
                mid.add(last);
                mid.add(lastname);
                mid.add(age);
                mid.add(a);
                middle.setVisible(false);
                first.setVisible(false);
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
                            try {
                                cos.writeObject(alaska);
                                cos.flush();
                                cos.writeObject(alaska);
                                cos.flush();
                                String line = (String) cis.readObject();

                                middle.setText(line);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else if (airline.equals("Southwest")) {
                            try {
                                cos.writeObject(sw);
                                cos.flush();
                                cos.writeObject(sw);
                                cos.flush();
                                String line = (String) cis.readObject();

                                middle.setText(line);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else if (airline.equals("Delta")) {
                            try {
                                cos.writeObject(d);
                                cos.flush();
                                cos.writeObject(d);
                                cos.flush();
                                String line = (String) cis.readObject();

                                middle.setText(line);
                            } catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
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
                                    JFrame jf = new JFrame("Passengers");
                                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    jf.setSize(300,300);


                                    if (airline.equals("Southwest")) {
                                        try {
                                            cos.writeObject(sw);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else if (airline.equals("Delta")) {
                                        try {
                                            cos.writeObject(d);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }

                                    } else if (airline.equals("Alaska")){
                                        try {
                                            cos.writeObject(alaska);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    JPanel cp = (JPanel) jf.getContentPane(); //START OF KEY BIND
                                    ActionMap aMap = cp.getActionMap();
                                    InputMap inMap = cp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                                    KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
                                    inMap.put(escape, "Exit");
                                    AbstractAction aa = new AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jf.setVisible(false);
                                        }
                                    };
                                    aMap.put("Exit", aa);


                                }


                            };
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
                                try {
                                    cos.writeObject(alaska);
                                    cos.writeObject(null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Boolean test = cis.readBoolean();
                                    if (test) {
                                        airlineList.setVisible(false);
                                        middle.setVisible(false);
                                        heading.setText("Are you sure that you want to book a flight on Alaska Airlines?");
                                        book.setText("Yes, I want this flight");
                                        book.setActionCommand("Step6");
                                        no.setVisible(true);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Flight is full");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (airline.equals("Southwest")) {
                                try {
                                    cos.writeObject(sw);
                                    cos.writeObject(null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Boolean test = cis.readBoolean();
                                    if (test) {
                                    airlineList.setVisible(false);
                                    middle.setVisible(false);
                                    heading.setText("Are you sure that you want to book a flight on Southwest Airlines?");
                                    book.setText("Yes, I want this flight");
                                    book.setActionCommand("Step6");
                                    no.setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Flight is full");
                                }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    cos.writeObject(d);
                                    cos.writeObject(null);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Boolean test = cis.readBoolean();
                                    if (test) {
                                        airlineList.setVisible(false);
                                        middle.setVisible(false);
                                        heading.setText("Are you sure that you want to book a flight on Delta Airlines?");
                                        book.setText("Yes, I want this flight");
                                        book.setActionCommand("Step6");
                                        no.setVisible(true);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Flight is full");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if("Step6".equals(actionEvent.getActionCommand())) {

                            JPanel cp = (JPanel) f.getContentPane(); //START OF KEY BIND
                            ActionMap aMap = cp.getActionMap();
                            InputMap inMap = cp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                            KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true);
                            inMap.put(slashKey, "OpenAirlineList");
                            AbstractAction abstractAction = new AbstractAction() {

                                @Override

                                public void actionPerformed(ActionEvent e) {

                                    String airline = (String) airlineList.getSelectedItem();
                                    JFrame jf = new JFrame("Passengers");
                                    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    jf.setSize(300,300);


                                    if (airline.equals("Southwest")) {
                                        try {
                                            cos.writeObject(sw);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else if (airline.equals("Delta")) {
                                        try {
                                            cos.writeObject(d);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }

                                    } else if (airline.equals("Alaska")){
                                        try {
                                            cos.writeObject(alaska);
                                            cos.writeObject(jf);
                                            JLabel jl = (JLabel) cis.readObject();
                                            JTextArea textArea = (JTextArea) cis.readObject();
                                            JScrollPane scrollPane = new JScrollPane(textArea);
                                            jf.add(BorderLayout.NORTH, jl);
                                            jf.add(BorderLayout.CENTER, scrollPane);
                                            jf.setVisible(true);
                                        } catch (IOException | ClassNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    JPanel cp = (JPanel) jf.getContentPane(); //START OF KEY BIND
                                    ActionMap aMap = cp.getActionMap();
                                    InputMap inMap = cp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                                    KeyStroke escape = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true);
                                    inMap.put(escape, "Exit");
                                    AbstractAction aa = new AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            jf.setVisible(false);
                                        }
                                    };
                                    aMap.put("Exit", aa);


                                }


                            };
                            aMap.put("OpenAirlineList", abstractAction); // end of keybind

                            String airline = (String) airlineList.getSelectedItem();
                           if (airline.equals("Alaska")) {
                               try {
                                    cos.writeObject(alaska);
                                    cos.writeObject(null);
                                    boolean av = cis.readBoolean();

                                    if (av) {
                                        heading.setText("Please input your information below.");
                                        mid.setVisible(true);
                                        middle.setVisible(false);
                                        first.setVisible(true);
                                        firstName.setVisible(true);
                                        last.setVisible(true);
                                        lastname.setVisible(true);
                                        age.setVisible(true);
                                        a.setVisible(true);
                                        no.setVisible(false);
                                        book.setText("Next");
                                        book.setActionCommand("Step7");
                                    }

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           } else if (airline.equals("Southwest")) {
                               try {
                                   cos.writeObject(sw);
                                   cos.writeObject(null);
                                   boolean av = cis.readBoolean();

                                   if (av) {
                                       heading.setText("Please input your information below.");
                                       mid.setVisible(true);
                                       middle.setVisible(false);
                                       first.setVisible(true);
                                       firstName.setVisible(true);
                                       last.setVisible(true);
                                       lastname.setVisible(true);
                                       age.setVisible(true);
                                       a.setVisible(true);
                                       no.setVisible(false);
                                       book.setText("Next");
                                       book.setActionCommand("Step7");
                                   }

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           } else if (airline.equals("Delta")) {
                               try {
                                   cos.writeObject(d);
                                   cos.writeObject(null);
                                   boolean av = cis.readBoolean();

                                   if (av) {
                                       heading.setText("Please input your information below.");
                                       mid.setVisible(true);
                                       middle.setVisible(false);
                                       first.setVisible(true);
                                       firstName.setVisible(true);
                                       last.setVisible(true);
                                       lastname.setVisible(true);
                                       age.setVisible(true);
                                       a.setVisible(true);
                                       no.setVisible(false);
                                       book.setText("Next");
                                       book.setActionCommand("Step7");
                                   }

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }
                        }

                        if ("Step7".equals(actionEvent.getActionCommand())) {
                            JPanel cp = (JPanel) f.getContentPane();
                            ActionMap aMap = cp.getActionMap();
                            InputMap inMap = cp.getInputMap();
                            KeyStroke slashKey = KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0, true);
                            inMap.put(slashKey, null);
                            aMap.put(slashKey, null);

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

                            if (correctFirstName && correctLastName && correctAge) {
                                int confirm = JOptionPane.showConfirmDialog(null, "Are all the details you entered correct? \n The passenger's name is " + f + " " + l + " and their age is " + ageString + ". \n If all the information shown is correct, select the Yes button down below, otherwise, select the No button.", "Confirm Info", JOptionPane.YES_NO_OPTION);
                                if (confirm == 1) {
                                    book.setActionCommand("Step6");
                                } else {
                                    book.setActionCommand("Step8");
                                }
                            } else {
                                book.setActionCommand("Step6");
                            }

                        }

                        if ("Step8".equals(actionEvent.getActionCommand())) {
                            String airline = (String) airlineList.getSelectedItem();
                            first.setVisible(false);
                            firstName.setVisible(false);
                            last.setVisible(false);
                            lastname.setVisible(false);
                            age.setVisible(false);
                            a.setVisible(false);

                            if (airline.equals("Alaska")) {
                                try {
                                    cos.writeObject(alaska);
                                    cos.writeObject(null);

                                    boolean av = (boolean) cis.readBoolean();
                                    if (av) {
                                        heading.setText("Flight data displaying for Alaska Airlines \n Enjoy your flight! \n Flight is now boarding at Gate " + g.getTerminal() + g.getGate());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (airline.equals("Southwest")) {
                                try {
                                    cos.writeObject(sw);
                                    cos.writeObject(null);

                                    boolean av = (boolean) cis.readBoolean();
                                    if (av) {
                                        heading.setText("Flight data displaying for Southwest Airlines \n Enjoy your flight! \n Flight is now boarding at Gate " + g.getTerminal() + g.getGate());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (airline.equals("Delta")) {
                                try {
                                    cos.writeObject(d);
                                    cos.writeObject(null);

                                    boolean av = (boolean) cis.readBoolean();
                                    if (av) {
                                        heading.setText("Flight data displaying for Delta Airlines \n Enjoy your flight! \n Flight is now boarding at Gate " + g.getTerminal() + g.getGate());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        // STEP 6
                        // check available
                        // cos.write(airline)
                        // cos.write(null)
                        // cis.readBoolean()
                        // write passenger to file by using the server not the airline class
                        // cos.write(passenger)
                        // cos.write(airline)

                        // also copy paste key bind into here bc step 7 has the option to go back

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
                            book.setActionCommand("Step5");
                            no.setVisible(false);
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
