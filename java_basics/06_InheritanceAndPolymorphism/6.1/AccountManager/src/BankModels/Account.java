package BankModels;

public class Account {
    private String number;
    private double balance;
    private String currency;

    public Account(String number, double balance, String currency){
        this.number = number;
        this.balance = balance;
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void withdrawMoney(double amount){
        balance -= amount;
        System.out.println("Списание " + amount + " " + currency);
    }

    public void depositMoney(double amount){
        balance += amount;
        System.out.println("Пополнение " + amount + " " + currency);
    }

    public String getStatus(){
        return  "Баланс счета " + number + " составляет: " + balance + " " + currency;
    }
}
