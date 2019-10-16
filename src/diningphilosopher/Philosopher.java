package diningphilosopher;

import java.util.Random;

public class Philosopher extends Thread {
    private Random numGenerator = new Random();
    private int id;
    private Fork leftFork;
    private Fork rightFork;
    private OnPhilosopherActionListenter listenter;
    private boolean finishedEating;
    private boolean suspended;

    public Philosopher(int id, Fork leftFork, Fork rightFork, OnPhilosopherActionListenter listenter) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.listenter = listenter;
    }

    public void run() {
        try {
            while (true) {
                think();
                pickUpLeftFork();
                pickUpRightFork();
                eat();
                putDownFork();
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + id + " was interrupted.\n");
        }
    }

    private void think() throws InterruptedException {
        synchronized (this) {
            while (suspended) {
                wait();
            }

        }
        listenter.onThinking(this);
        Thread.sleep(numGenerator.nextInt(3000) + 5000);
    }

    private void pickUpLeftFork() {
        synchronized (this) {
            while (suspended) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        leftFork.grab();
        listenter.onHungry(this);
        System.out.flush();
    }

    private void pickUpRightFork() {
        synchronized (this) {
            while (suspended) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
        }
        rightFork.grab();
        System.out.flush();

    }

    private void eat() throws InterruptedException {
        synchronized (this) {
            while (suspended) {
                wait();
            }
        }
        setFinishedEating(false);
        listenter.onEating(this);
        System.out.flush();
        Thread.sleep(numGenerator.nextInt(3000) + 5000);
    }

    private void putDownFork() {
        leftFork.release();
        rightFork.release();
    }

    public int getPhilId() {
        return id;
    }

    public boolean isFinishedEating() {
        return finishedEating;
    }

    public void setFinishedEating(boolean finishedEating) {
        this.finishedEating = finishedEating;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public boolean getSuspended() {
        return suspended;
    }

}
