package BankClients;

public class PhysicClient extends Client {
    public PhysicClient(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("Внесено: " + amount);
    }

    @Override
    public void withdraw(double amount) {
        setBalance(getBalance() - amount);
        System.out.println("Списано: " + amount);
    }
}
