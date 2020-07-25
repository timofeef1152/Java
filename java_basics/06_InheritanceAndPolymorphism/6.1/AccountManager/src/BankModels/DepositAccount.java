package BankModels;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DepositAccount extends Account {
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Calendar cal;

    public DepositAccount(String number, double balance, String currency) {
        super(number, balance, currency);
        cal = Calendar.getInstance();
        cal.setTime(new Date(1,1,1));
    }

    public void depositMoney(double amount){
        setBalance(getBalance() + amount);
        cal.setTime(new Date());
        System.out.println("Пополнение " + amount + " " + getCurrency());
    }

    public void withdrawMoney(double amount){
        Calendar curCal = Calendar.getInstance();
        curCal.setTime(new Date());
        if (
                (curCal.get(Calendar.YEAR) - cal.get(Calendar.YEAR) > 1)//Более 2 лет
                        ||
                (curCal.get(Calendar.MONTH) - cal.get(Calendar.MONTH) > 1)//Более 2 месяцев
                        ||
                (curCal.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                curCal.get(Calendar.MONTH) - cal.get(Calendar.MONTH) >= 1 &&
                curCal.get(Calendar.DAY_OF_MONTH) >= cal.get(Calendar.DAY_OF_MONTH))//Текущий год, более 1 месяца
                        ||
                (curCal.get(Calendar.YEAR) - cal.get(Calendar.YEAR) == 1 &&
                curCal.get(Calendar.DAY_OF_YEAR) >= cal.get(Calendar.DAY_OF_MONTH))//Прошедший год, более 1 месяца
        ){
            setBalance(getBalance() - amount);
            System.out.println("Списание " + amount + " " + getCurrency());
        }
        else {
            Calendar nextCal = Calendar.getInstance();
            nextCal.add(Calendar.MONTH,1);
            System.out.println("Следующее списание средств возможно " + sdf.format(nextCal.getTime()));
        }
    }
}
