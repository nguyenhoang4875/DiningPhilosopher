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
            philosophers[i] = new Philosopher(i, forks[min(i,(i+1)% NUM_PHILOSOPHERS)], forks[max(i,(i + 1) % NUM_PHILOSOPHERS)], listenner);
            philosophers[i].setFinishedEating(false);
            philosophers[i].start();
        }
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            System.out.println("--------------------------------");
            System.out.println("philosopher:"+i+ " left fork "+philosophers[i].getLeftFork().getId()+ " right fork "+philosophers[i].getRightFork().getId() );

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

    public int min(int a,int b){
        return a<b?a:b;
    }
    public int max(int a,int b){
        return a>b?a:b;
    }
}
