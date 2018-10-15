package employee;

public class Developer extends Employee {

    int fixedBugs;

    Developer() {
        super();
    }

    public String toString() {
        return super.toString() + " fixedBugs: " + fixedBugs;
    }

}
