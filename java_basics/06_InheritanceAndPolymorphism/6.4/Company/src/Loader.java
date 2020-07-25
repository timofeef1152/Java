import MyCompany.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loader {
    public static void main(String[] args) {
        //Инициализация
        //==============================================================================================================
        Random random = new Random();
        Company myCompany = new Company(11000000);
        for (int i = 0; i <= 180; i++){
            int fixedSalary = 30000 + random.nextInt(100000);
            Employee operator = new Operator(fixedSalary);
            myCompany.hire(operator);
        }
        for (int i = 0; i <= 80; i++){
            int fixedSalary = 60000 + random.nextInt(100000);
            Employee manager = new Manager(fixedSalary, myCompany);
            myCompany.hire(manager);
        }
        List<Employee> topManagers = new ArrayList<>();
        for (int i = 0; i <= 10; i++){
            int fixedSalary = 120000 + random.nextInt(100000);
            topManagers.add(new TopManager(fixedSalary, myCompany));
        }
        myCompany.hireAll(topManagers);
        //==============================================================================================================

        //==============================================================================================================
        System.out.println("Список из 15 самых высоких зарплат");
        printEmployees(myCompany.getTopSalaryStaff(15));
        //==============================================================================================================

        //==============================================================================================================
        System.out.println("Список из 30 самых низких зарплат");
        printEmployees(myCompany.getLowestSalaryStaff(30));
        //==============================================================================================================

        //==============================================================================================================
        System.out.println("Увольнение 50% сотрудников компании");
        int employeesCount = myCompany.getEmployees().size();
        while (myCompany.getEmployees().size() > employeesCount / 2){
            int index = random.nextInt(myCompany.getEmployees().size());
            ArrayList<Employee> employees = new ArrayList<>(myCompany.getEmployees());
            myCompany.fire(employees.get(index));
        }
        //==============================================================================================================

        //==============================================================================================================
        System.out.println("Список из 15 самых высоких зарплат");
        printEmployees(myCompany.getTopSalaryStaff(15));
        //==============================================================================================================

        //==============================================================================================================
        System.out.println("Список из 30 самых низких зарплат");
        printEmployees(myCompany.getLowestSalaryStaff(30));
        //==============================================================================================================
    }

    static void printEmployees( List<Employee> employees){
        for (int i = 0; i < employees.size(); i++){
            System.out.println(i + 1 + ". " + employees.get(i).getMonthSalary());
        }
    }
}
