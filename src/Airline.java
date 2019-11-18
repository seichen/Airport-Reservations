import java.io.IOException;
import java.io.Serializable;

public interface Airline extends Serializable {
    boolean spaceAvailable();

    void addPassenger(String passenger);

    String getPassengers();

    String getCapacity();
}
