import java.io.IOException;
import java.io.Serializable;

/** Airline.java
 *
 * An interface class that implements the Serializable class.
 * It has four methods, with each implemented in the Airline classes.
 *
 * Sources used: None
 *
 * @author Yash Bansal and Sabrina Eichenberger, CS 180 BLK, Lab Section LC1
 * @version 2019-12-01
 */

public interface Airline extends Serializable {
    boolean spaceAvailable();

    void addPassenger(String passenger);

    String getPassengers();

    String getCapacity();
}
