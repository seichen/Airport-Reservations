import java.io.Serializable;

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private int age;

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return String.format("%s.%s, %d", getFirstName().substring(0,1), getLastName(), getAge());
    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof Passenger) {
            Passenger p = (Passenger) obj;
            if (p.getAge() == getAge() && p.getFirstName().equals(getFirstName()) && p.getLastName().equals(getLastName()));
            equals = true;
        }
        return equals;
    }
}
