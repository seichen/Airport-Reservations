import java.io.Serializable;
import java.util.Random;

public class Gate implements Serializable {
    private int terminal;
    private int gate;

    public Gate() {
        Random r = new Random();
        this.terminal = r.nextInt(3);
        this.gate = r.nextInt(18);
    }

    public String getTerminal() {
        String terminal = "";
        switch (this.terminal) {
            case 0:
                terminal = "A";
                break;
            case 1:
                terminal = "B";
                break;
            case 2:
                terminal = "C";
                break;
        }
        return terminal;
    }

    public int getGate() {
        return gate + 1;
    }

    public String toString() {
        return String.format("Gate[%s, %d]", getTerminal(), getGate());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof Gate) {
            Gate g = (Gate) obj;
            if (g.getTerminal().equals(getTerminal()) && g.getGate() == getGate()) {
                equals = true;
            }
        }
        return equals;
    }
}
