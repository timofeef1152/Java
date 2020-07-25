import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BankTest extends TestCase {

    private int logicalCoreCount;
    private ExecutorService service;

    private HashMap<String, Account> accounts;
    private Bank bank;

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

    @Override
    protected void setUp() throws Exception {
        accounts = generateAccounts(20);
        bank = new Bank(accounts);

        logicalCoreCount = Runtime.getRuntime().availableProcessors();
        service = Executors.newFixedThreadPool(logicalCoreCount * 10);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testTransferConcurrent(){
        AtomicBoolean anyException = new AtomicBoolean(false);
        //Simulate work.
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                Account from = accounts.get(new Random().nextInt(accounts.size()));
                Account to = from;
                try {
                    while (from.equals(to)) to = accounts.get(new Random().nextInt(accounts.size()));

                    bank.transfer(from.getNumber(), to.getNumber(), Math.round(Math.random() * 100_000));
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
                catch (Exception e){
                    anyException.set(true);
                    return;
                }
                System.out.println("Баланс:\n\t" + from.getNumber() + ":\t" + bank.getBalance(from.getNumber()) + "\n\t" + to.getNumber() + ":\t" + bank.getBalance(to.getNumber()));
            });
            if (anyException.get()) break;
            service.execute(thread);
        }
        service.shutdown();
        while (!service.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(anyException.get());
    }
}
