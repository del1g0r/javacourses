package employee;

public class Test {

    public static void main(String[] args) {
        Employee[] employees = MockEmployeesGenerator.generate(20);
        EmployeeService employeeService = new EmployeeService(employees);
//        employeeService.addEmployee(new Employee("Bill", "Gates", 10000));
        employeeService.removeEmployee(3);
        Employee[] sortedEmployees = employeeService.sortByLastFirstName();

        System.out.println("Random:");
        employeeService.print();

        System.out.println("Ordered:");
        employeeService.print(sortedEmployees);

        System.out.println(employeeService.calculateSalary());
    }
}