package employee;

import java.util.Random;

public class MockEmployeesGenerator {

    static String[] firstWNames = {"Julia", "Mary", "Emma", "Isabella"};
    static String[] firstMNames = {"Bill", "Bob", "James", "John"};
    static String[] lastNames = {"Lee", "Smith", "Martin", "Lines"};
    static char[] genderNames = {'M', 'W'};

    static double minSalary = 100;
    static double maxSalary = 340;

    static int minAge = 20;
    static int maxAge = 80;

    static String[] classNames = {Developer.class.getName(), Manager.class.getName(), Cleaner.class.getName()};

    static Random random = new Random();

    static Employee[] generate(int n) {
        Employee[] employee = new Employee[n];
        for (int i = 0; i < n; i++) {
            Employee e = null;
            String className = classNames[random.nextInt(classNames.length)];
            if (className == Developer.class.getName()) {
                Developer developer = new Developer();
                developer.fixedBugs = random.nextInt(100);
                e = developer;
            } else if (className == Manager.class.getName()) {
                Manager manager = new Manager();
                e = manager;
            } else if (className == Cleaner.class.getName()) {
                Cleaner cleaner = new Cleaner();
                cleaner.rate = (double) Math.round((random.nextDouble() * (maxSalary - minSalary) + minSalary) * 100) / 100;
                cleaner.workedDays = random.nextInt(100);
                e = cleaner;
            }
            if (e != null) {
                e.gender = genderNames[random.nextInt(genderNames.length)];
                e.firstName = e.gender == 'M' ? firstMNames[random.nextInt(firstMNames.length)] : firstWNames[random.nextInt(firstWNames.length)];
                e.lastName = lastNames[random.nextInt(lastNames.length)];
                e.age = random.nextInt(maxAge - minAge) + minAge;
                e.salary = (double) Math.round((random.nextDouble() * (maxSalary - minSalary) + minSalary) * 100) / 100;
                employee[i] = e;
            }
        }
        return employee;
    }

}
