public class DiningPhil
{
	public static void main(String[] args)throws Exception
	{
		// Create philosophers and their forks
		Philosopher[] philosophers = new Philosopher[5];
		Object[] forks = new Object[philosophers.length];
		String[] names = new String[]{"Aristotle", "Augustine", "Roland Barthes", "Albert Camus", "Noam Chomsky"};

		// Initialize forks
		for(int i = 0; i < forks.length; i++)
		{
			forks[i] = new Object();
		}

		// Begin dining
		for(int i = 0; i < philosophers.length; i ++){
			Object leftFork = forks[i];
			Object rightFork = forks[(i+1) % forks.length];
			String name = names[i];

			// Last philosopher picks up right fork first
			if( i == philosophers.length - 1)
			{
				philosophers[i] = new Philosopher(name, rightFork, leftFork);
			}
			else
			{
				philosophers[i] = new Philosopher(name, leftFork, rightFork);
			}

			// Create philosopher thread
			Thread b = new Thread(philosophers[i], name);
			b.start();
		}
	}
}
