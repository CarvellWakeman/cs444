import java.util.concurrent.ThreadLocalRandom;

public class Philosopher implements Runnable
{
	private final String name;
	private final Object rightFork;
	private final Object leftFork;
	private static double currentTime = 0.0d;

	static
	{
		currentTime = getTime();
	}

	// Constructor
	Philosopher(String name, Object left, Object right)
	{
		this.name = name;
		this.leftFork = left;
		this.rightFork = right;
	}

	// A philosopher's action with delay
	private void doMove(int eatThinkFork, String move) throws InterruptedException
	{
		int num = 0;
		switch(eatThinkFork)
		{	
			case 0: // eat
				num = ThreadLocalRandom.current().nextInt(2, 9+1);
				break;
			case 1: // think
				num = ThreadLocalRandom.current().nextInt(1, 20+1);
				break;
			case 2: // fork
				num = 0;
				break;
		}

		if (num > 0){
			Thread.sleep(num);
		}

		// print out
		System.out.println(Thread.currentThread().getName()
			+ " " + move + " for " + String.valueOf(num) + " seconds");
	}

	// A philosopher's action
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				doMove(1, getTime() + "s: Thinking");
				synchronized (leftFork)
				{
					doMove(2, getTime() +"s: Picked up left fork");

					synchronized(rightFork)
					{
						doMove(0, getTime() + "s: Picked up right fork - eating");
						doMove(2, getTime() + "s: Put down right fork");
					}
					doMove(1, getTime() + "s: Put down left fork. Back to thinking");
				}
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}

	public static double getTime()
	{
		double a = (System.nanoTime() / 1000000000.0) - currentTime;
		return Math.round(a * 10.0) / 10.0;
	}
}
