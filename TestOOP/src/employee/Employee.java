package employee;

public abstract class Employee {

    int id;
    String firstName;
    String lastName;
    double salary;
    int age;
    char gender;

    static int idGen = 0;

    Employee() {
        super();
        this.id = ++idGen;
    }

    Employee(String firstName, String lastName, double salary) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    Employee(String firstName, String lastName, double salary, int age, char gender) {
        this(firstName, lastName, salary);
        this.age = age;
        this.gender = gender;
    }

    static int compByLastName(Employee a, Employee b) {
        return a.lastName.compareTo(b.lastName);
    }

    static int compByLastFirstName(Employee a, Employee b) {
        int r = compByLastName(a, b);
        if (r != 0)
            return r;
        else
            return a.firstName.compareTo(b.firstName);
    }

    public String toString() {
        return this.getClass().getName() + "{" + id + "]: " + firstName + " " + lastName + " gender: " + gender + " age: " + age + " salary: " + salary;
    }

}
