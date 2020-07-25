package MyCompany;

import java.util.*;

public class Company {
    private int income;
    private TreeSet<Employee> employees;

    public Company(int income) {
        setIncome(income);
        setEmployees(new TreeSet<>());
    }

    public TreeSet<Employee> getEmployees() {
        return employees;
    }

    private void setEmployees(TreeSet<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getTopSalaryStaff(int count){
        if (employees.size() <= count){
            return new ArrayList<>();
        }
        var descEmployees = new ArrayList<>(employees);
        Collections.reverse(descEmployees);
        return  descEmployees.subList(0, count);
    }

    public List<Employee> getLowestSalaryStaff(int count){
        if (employees.size() <= count){
            return new ArrayList<>();
        }
        var ascEmployees = new ArrayList<>(employees);
        return  ascEmployees.subList(0, count);
    }

    public void hire(Employee employee){
        employees.add(employee);
    }

    public void  hireAll(List<Employee> employeeSet){
        employees.addAll(employeeSet);
    }

    public void fire(Employee employee){
        employees.remove(employee);
    }

    private void setIncome(int income){
        this.income = income;
    }

    public int getIncome(){
        return income;
    }
}
