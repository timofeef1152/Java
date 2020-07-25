package BankModels;

public class CardAccount extends Account {
    private double commission;

    public CardAccount(String number, double balance, String currency, double commission) {
        super(number, balance, currency);
        this.commission = commission;
    }

    public void withdrawMoney(double amount){
        setBalance(getBalance() - (amount * commission));
        System.out.println("Списание " + amount + " " + getCurrency());
    }
}
