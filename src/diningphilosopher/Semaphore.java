package diningphilosopher;


public class Semaphore {
    private final int permit;
    private int counter;

    public Semaphore(int permit) {
        this.permit = permit;
        this.counter = permit;
    }

    public static void main(String[] args) {
    }

    public int availablePermits() {
        return counter;
    }

    public synchronized void acquire() throws InterruptedException {
        while (counter == 0) {
            wait();
        }
        System.out.println("count before desc : " + counter);
        counter = 0;
    }

    public synchronized void release() {
        if (counter < permit) {
            counter = 1;
            notify();
        }
    }
}
