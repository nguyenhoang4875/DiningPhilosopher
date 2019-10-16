package diningphilosopher;

public class Fork {
    private int id;
    private Semaphore mutex;

    public Fork(int id) {
        this.id = id;
        this.mutex = new Semaphore(1);
    }

    public void grab() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
        }
    }

    public void release() {
        mutex.release();
    }

    public boolean isFree() {
        return mutex.availablePermits() == 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
