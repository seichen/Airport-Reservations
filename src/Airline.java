import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public interface Airline extends Serializable {
    boolean spaceAvailable() throws IOException;

    void addPassenger(String passenger) throws IOException;
}
