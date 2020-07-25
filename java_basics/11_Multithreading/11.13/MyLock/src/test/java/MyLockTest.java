import junit.framework.Assert;
import junit.framework.TestCase;

public class MyLockTest extends TestCase {
    private MyLock lock;
    private int c;

    @Override
    protected void setUp() throws Exception {
        lock = new MyLock();
        c = 0;
    }

    public void testLockUnlock() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                lock.lock();
                Math.pow(c, 2);
                c++;
                lock.unlock();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100_000; i++) {
                lock.lock();
                lock.lock();
                Math.pow(c, 4);
                c--;
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        Assert.assertEquals(0, c);
    }
}
