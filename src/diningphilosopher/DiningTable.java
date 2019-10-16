package diningphilosopher;

public class DiningTable {
    private static final int NUM_PHILOSOPHERS = 5;
    private Fork[] forks = new Fork[NUM_PHILOSOPHERS];
    private Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
    private OnPhilosopherActionListenter listenter;

    public DiningTable() {
    }

    public void initTable(OnPhilosopherActionListenter listenner) {
        this.listenter = listenner;
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Fork(i);
        }
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % NUM_PHILOSOPHERS], listenner);
            philosophers[i].setFinishedEating(false);
            philosophers[i].start();
        }
    }

    public void suspend() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i].setSuspended(true);
        }
    }

    public void resume() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i].setSuspended(false);
            synchronized (philosophers[i]) {
                philosophers[i].notify();
            }
        }
    }

    public void reset() {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            synchronized (philosophers[i]) {
                if (philosophers[i].isAlive()) {
                    philosophers[i].stop();
                }
            }
        }
    }

    public void play() {
        initTable(listenter);
    }
}
