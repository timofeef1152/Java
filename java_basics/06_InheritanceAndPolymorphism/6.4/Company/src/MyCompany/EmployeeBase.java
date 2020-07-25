package MyCompany;

public abstract class EmployeeBase implements Employee {
    private int fixedSalary;

    public EmployeeBase(int fixedSalary){
        setFixedSalary(fixedSalary);
    }

    public int getFixedSalary() {
        return fixedSalary;
    }

    protected void setFixedSalary(int fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    @Override
    public int compareTo(Employee employee) {
        return new Integer(this.getMonthSalary()).compareTo(employee.getMonthSalary());
    }
}
