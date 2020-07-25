package BankClients;

public abstract class Client {
    private double balance;

    public Client(double balance)
    {
        setBalance(balance);
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public void getStatus(){
        System.out.println("Баланс: " + getBalance());
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
}
