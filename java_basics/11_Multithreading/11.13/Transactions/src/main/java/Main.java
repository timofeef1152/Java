import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int logicalCoreCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(logicalCoreCount * 10);

        HashMap<String, Account> accounts = generateAccounts(20);

        Bank bank = new Bank(accounts);

        //Simulate work.
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                Account from = (Account) accounts.values().toArray()[new Random().nextInt(accounts.size())];
                Account to = from;
                try {
                    while (from.equals(to))
                        to = (Account) accounts.values().toArray()[new Random().nextInt(accounts.size())];

                    bank.transfer(from.getNumber(), to.getNumber(), Math.round(Math.random() * 100_000));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Баланс:\n\t" + from.getNumber() + ":\t" + bank.getBalance(from.getNumber()) + "\n\t" + to.getNumber() + ":\t" + bank.getBalance(to.getNumber()));
            });
            service.execute(thread);
        }
        service.shutdown();
    }

    static HashMap<String, Account> generateAccounts(int count) {
        HashMap<String, Account> accounts = new HashMap<>();
        for (int i = 0; i < count; i++) {
            long balance = Math.round(Math.random() * 10_000);
            String accNum = Long.toString(Math.round(Math.random() * Math.pow(10, 20)));
            while (accNum.length() < 20) accNum += Math.round(Math.random() * 10);
            String finalAccNum = accNum;
            if (!accounts.containsKey(finalAccNum)) {
                accounts.put(finalAccNum, new Account(accNum, balance, false));
            } else {
                i--;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }
}
