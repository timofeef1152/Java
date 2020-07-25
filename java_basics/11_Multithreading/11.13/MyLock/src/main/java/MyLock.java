public class MyLock {
    private Thread master = null;
    private boolean isBusy = false;

    public synchronized void lock() {
        while (isBusy && master != Thread.currentThread()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        master = Thread.currentThread();
        isBusy = true;
    }

    public synchronized void unlock() {
        this.notify();
        master = null;
        isBusy = false;
    }
}