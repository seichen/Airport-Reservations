import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public interface Airline extends Serializable {
    boolean spaceAvailable() throws IOException;

    void addPassenger(String passenger) throws IOException;

    ArrayList<String> getPassengers() throws IOException;

    String getCapacity() throws IOException;
}
