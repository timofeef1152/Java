import lombok.Data;

import java.util.HashMap;
import java.util.Random;

@Data
public class Bank {
    private HashMap<String, Account> accounts;

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Заглушка
     */
    private synchronized boolean isFraud(String fromAccNum, String toAccNum, long amount) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextBoolean();
    }

    public void transfer(String fromAccNum, String toAccNum, long amount) {
        if (fromAccNum.equals(toAccNum)) throw new RuntimeException("В операции отказано: счета совпадают!");
        //Getting the accounts.
        Account fromAcc = accounts.get(fromAccNum);
        if (fromAcc == null)
            throw new RuntimeException("В операции отказано: счет " + fromAccNum + " не существует!");
        Account toAcc = accounts.get(toAccNum);
        if (toAcc == null)
            throw new RuntimeException("В операции отказано: счет " + toAccNum + " не существует!");

        boolean isFraud = isFraud(fromAccNum, toAccNum, amount);

        //Lock accounts.
        Account lock1;
        Account lock2;

        if (fromAcc.compareTo(toAcc) < 0) {
            lock1 = fromAcc;
            lock2 = toAcc;
        } else {
            lock1 = toAcc;
            lock2 = fromAcc;
        }

        synchronized (lock1) {
            synchronized (lock2) {
                //Manipulations.
                if (fromAcc.isBlocked())
                    throw new RuntimeException("В операции отказано: счет " + fromAcc.getNumber() + " заблокирован!");
                if (toAcc.isBlocked())
                    throw new RuntimeException("В операции отказано: счет " + toAcc.getNumber() + " заблокирован!");
                if (fromAcc.getBalance() < amount)
                    throw new RuntimeException("В операции отказано: не достаточно средств на счете!");
                fromAcc.setBalance(fromAcc.getBalance() - amount);
                fromAcc.setBlocked(isFraud);
                //Например, здесь могут быть ваши длительные вычисления...
                toAcc.setBalance(toAcc.getBalance() + amount);
                toAcc.setBlocked(isFraud);
            }
        }
    }

    public long getBalance(String accNum) {
        Account account = accounts.get(accNum);
        if (account == null) throw new RuntimeException("Запрашиваемый счет не найден в системе банка!");
        synchronized (account) {
            return account.getBalance();
        }
    }
}