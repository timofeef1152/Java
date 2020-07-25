package MyCompany;

public class Manager extends EmployeeBase {
    static final double COMPANY_SALARY_BONUS_COEFFICIENT = 0.05;
    private Company company;

    public Manager(int fixedSalary, Company company) {
        super(fixedSalary);
        setCompany(company);
    }

    protected Company getCompany() {
        return company;
    }

    protected void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int getMonthSalary() {
        //Внёс правку в формулу З/П, потому что 5% от всего дохода компании - слишком много.
        return getFixedSalary() + (int)(getCompany().getIncome() * COMPANY_SALARY_BONUS_COEFFICIENT / company.getEmployees().size());//округлил в пользу компании
    }
}
