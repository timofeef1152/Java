package BankClients;

public class IndividualClient extends Client {
    static final double LIMIT = 1000;
    static final double COMMISSION_IN_LIMIT = 0.01;
    static final double COMMISSION_OUT_LIMIT = 0.005;

    public IndividualClient(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        double com;
        double total;
        if (amount < LIMIT){
            com = amount * COMMISSION_IN_LIMIT;
        }
        else {
            com = amount * COMMISSION_OUT_LIMIT;
        }
        total = amount - com;
        setBalance(getBalance() + total);
        System.out.println("Внесено: " + total + " + комиссия: " + com);
    }

    @Override
    public void withdraw(double amount) {
        setBalance(getBalance() - amount);
        System.out.println("Списано: " + amount);
    }
}
