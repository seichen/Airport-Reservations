import java.io.Serializable;

/** BoardingPass.java
 *
 * A class that creates a boarding pass for a passenger using the gate, terminal, and the passengers personal info.
 *
 * Sources used: None
 *
 * @author Yash Bansal and Sabrina Eichenberger, CS 180 BLK, Lab Section LC1
 * @version 2019-12-01
 */

public class BoardingPass implements Serializable {
    private Gate gate;
    private Passenger passenger;

    public BoardingPass(Gate gate, Passenger passenger) {
        this.passenger = passenger;
        this.gate = gate;
    }

    public Gate getGate() {
        return gate;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public String toString(String airline) {
        return String.format("<html><div style='text-align: center;'>BOARDING PASS FOR FLIGHT 18000 WITH %s<br>"
                + "PASSENGER FIRST NAME: %s <br>" + "PASSENGER LAST NAME: %s <br>" + "PASSENGER AGE: %d <br>"
                + "You can begin boarding at gate %s <br>", airline, passenger.getFirstName(),
                passenger.getLastName(), passenger.getAge(), gate.toString());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof BoardingPass) {
            BoardingPass b = (BoardingPass) obj;
            if (b.getGate() == getGate() && b.getPassenger() == getPassenger()) {
                equals = true;
            }
        }
        return equals;
    }
}
