import java.io.Serializable;

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
        return String.format("BOARDING PASS FOR FLIGHT 18000 WITH %s \n PASSENGER FIRST NAME: %s \n PASSENGER LAST NAME: %s \n PASSENGER AGE: %d \n You can begin boarding at gate %s", airline, passenger.getFirstName(), passenger.getLastName(), passenger.getAge(), gate.toString());
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
