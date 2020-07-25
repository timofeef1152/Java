package MyCompany;

public class Operator extends EmployeeBase {

    public Operator(int fixedSalary) {
        super(fixedSalary);
    }

    @Override
    public int getMonthSalary() {
        return getFixedSalary();
    }
}
