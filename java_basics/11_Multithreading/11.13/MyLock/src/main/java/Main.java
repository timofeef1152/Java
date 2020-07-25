public class Main {
    static MyLock lock = new MyLock();
    static int c = 0;

    public static void main(String[] args) throws InterruptedException {
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
                Math.pow(c, 2);
                c--;
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(c);
    }
}