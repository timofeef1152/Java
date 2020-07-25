import BankModels.Account;
import BankModels.CardAccount;
import BankModels.DepositAccount;

public class Loader {
    public static void main(String[] args) {
        //Базовый счет
        //==============================================================================================================
        System.out.println("\nБазовый счет");
        Account defAcc = new Account("42344123334879452348", 567312d,"RUR");
        System.out.println(defAcc.getStatus());
        defAcc.withdrawMoney(1400);
        System.out.println(defAcc.getStatus());
        defAcc.depositMoney(2500);
        System.out.println(defAcc.getStatus());
        //==============================================================================================================
        //Депозитный счет
        //==============================================================================================================
        System.out.println("\nДепозитный счет");
        DepositAccount depAcc = new DepositAccount("4236712338347856789", 567312d,"RUR");
        System.out.println(depAcc.getStatus());
        depAcc.withdrawMoney(1400);
        System.out.println(depAcc.getStatus());
        depAcc.withdrawMoney(250);
        System.out.println(depAcc.getStatus());
        depAcc.depositMoney(3700);
        System.out.println(depAcc.getStatus());
        depAcc.withdrawMoney(500);
        System.out.println(depAcc.getStatus());
        //==============================================================================================================
        //Карточный счет
        //==============================================================================================================
        System.out.println("\nКарточный счет");
        CardAccount cardAcc = new CardAccount("40817123346789456789", 567312d,"RUR",0.01d);
        System.out.println(cardAcc.getStatus());
        cardAcc.withdrawMoney(1500);
        System.out.println(cardAcc.getStatus());
        //==============================================================================================================
    }
}
