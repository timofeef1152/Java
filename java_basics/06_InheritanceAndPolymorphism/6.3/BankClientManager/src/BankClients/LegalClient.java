package BankClients;

public class LegalClient extends Client {
    static final double COMMISSION = 0.01;

    public LegalClient(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("Внесено: " + amount);
    }

    @Override
    public void withdraw(double amount) {
        double com = amount * COMMISSION;
        double total = amount + com;
        setBalance(getBalance() - total);
        System.out.println("Списано: " + total + " комиссия: " + com);
    }
}
