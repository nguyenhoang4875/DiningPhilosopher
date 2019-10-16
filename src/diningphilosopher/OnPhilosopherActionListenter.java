package diningphilosopher;

public interface OnPhilosopherActionListenter {
	void onThinking(Philosopher philosopher);
	
	void onEating(Philosopher philosopher);
	
	void onHungry(Philosopher philosopher);
}
