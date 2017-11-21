import java.util.concurrent.ThreadLocalRandom;

public abstract class ThreadType extends Thread implements Runnable
{
	private final String name;
	private static double currentTime = 0.0d;

	// Constructor
	ThreadType(String name)
	{
		super(name);

		this.currentTime = getTime();
		this.name = name;
	}

	public abstract boolean canAct();
	public abstract void action() throws InterruptedException;

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				if (canAct())
				{
					action();
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
