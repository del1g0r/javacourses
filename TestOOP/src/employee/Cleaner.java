package employee;

public class Cleaner extends Employee {

    double rate;
    int workedDays;

    public String toString() {
        return super.toString() + " rate: " + rate + " workedDays: " + workedDays;
    }

}
