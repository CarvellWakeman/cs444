public class Philosopher implements Runnable
{
	private final Object rightFork;
	private final Object leftFork;
	private static double currentTime = 0.0d;

	static {
		currentTime = getTime();
	}

	// Constructor
	Philosopher(Object left, Object right)
	{
		this.leftFork = left;
		this.rightFork = right;
	}

	// A philosopher's action with delay
	private void doMove(String move) throws InterruptedException
	{
		System.out.println(Thread.currentThread().getName() + " " + move);
		Thread.sleep(((int)(Math.random() * 100)));
	}

	// A philosopher's action
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				doMove(getTime() + "s: Thinking");
				synchronized (leftFork)
				{
					doMove(getTime() +"s: Picked up left fork");

					synchronized(rightFork)
					{
						doMove(getTime() + "s: Picked up right fork - eating");
						doMove(getTime() + "s: Put down right fork");
					}
					doMove(getTime() + "s: Put down left fork. Back to thinking");
				}
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}

	public static double getTime(){
		double a = (System.nanoTime() / 1000000000.0) - currentTime;
		return Math.round(a * 10.0) / 10.0;
	}
}
