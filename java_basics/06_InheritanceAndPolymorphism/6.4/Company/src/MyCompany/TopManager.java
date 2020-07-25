package MyCompany;

public class TopManager extends Manager {
    static final int COMPANY_INCOME_BARRIER = 10000000;
    static final double SALARY_BONUS_COEFFICIENT = 1.5;

    public TopManager(int fixedSalary, Company company) {
        super(fixedSalary, company);
    }

    @Override
    public int getMonthSalary(){
        int companyIncome = getCompany().getIncome();
        if (companyIncome > COMPANY_INCOME_BARRIER){
            return (int)(getFixedSalary() * SALARY_BONUS_COEFFICIENT);//округлил в пользу компании
        }
        return getFixedSalary();
    }
}
