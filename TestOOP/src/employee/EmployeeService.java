package employee;

public class EmployeeService {

    Employee[] employees;

    EmployeeService(Employee[] employees) {
        this.employees = employees;
    }

    Employee[] getEmployees() {
        return employees;
    }

    void setEmployees(Employee[] employees) {
        this.employees = employees;
    }

    void print() {
        print(employees);
    }

    static void print(Employee[] employees) {
        for (int i = 0; i < employees.length; i++) {
            System.out.println(employees[i]);
        }
    }

    double calculateSalary() {
        double r = 0;
        for (int i = 0; i < employees.length; i++)
            r += employees[i].salary;
        return r;
    }

    void removeEmployee(int id) {
        for (int i = 0; i < employees.length; i++)
            if (employees[i].id == id) {
                Employee[] newEmployees = new Employee[employees.length - 1];
                for (int j = 0; j < newEmployees.length; j++)
                    newEmployees[j] = employees[j + (j >= i ? 1 : 0)];
                employees = newEmployees;
            }
    }

    void addEmployee(Employee employee) {
        Employee[] newEmployees = new Employee[employees.length + 1];
        for (int i = 0; i < employees.length; i++)
            newEmployees[i] = employees[i];
        newEmployees[newEmployees.length - 1] = employee;
        employees = newEmployees;
    }

    Employee[] sortByLastName() {
        Employee[] r = new Employee[employees.length];
        for (int i = 0; i < r.length; i++)
            r[i] = employees[i];
        for (int i = 0; i < r.length / 2; i++) {
            boolean b = false;
            for (int j = i + 1; j < r.length - i - 1; j++) {
                int k = r.length - j - 1;
                if (Employee.compByLastName(r[j], r[i]) < 0) {
                    Employee buf = r[j];
                    r[j] = r[i];
                    r[i] = buf;
                    b = true;
                }
                if (Employee.compByLastName(r[k], r[r.length - i - 1]) > 0) {
                    Employee buf = r[k];
                    r[k] = r[r.length - i - 1];
                    r[r.length - i - 1] = buf;
                    b = true;
                }
            }
            if (!b)
                break;
        }
        return r;
    }

    Employee[] sortByLastFirstName() {
        Employee[] r = new Employee[employees.length];
        for (int i = 0; i < r.length; i++) r[i] = employees[i];
        for (int i = 0; i < r.length / 2; i++) {
            boolean b = false;
            for (int j = i + 1; j < r.length - i - 1; j++) {
                int k = r.length - j - 1;
                if (Employee.compByLastFirstName(r[j], r[i]) < 0) {
                    Employee buf = r[j];
                    r[j] = r[i];
                    r[i] = buf;
                    b = true;
                }
                if (Employee.compByLastFirstName(r[k], r[r.length - i - 1]) > 0) {
                    Employee buf = r[k];
                    r[k] = r[r.length - i - 1];
                    r[r.length - i - 1] = buf;
                    b = true;
                }
            }
            if (!b) break;
        }
        return r;
    }

}
