import java.util.concurrent.ThreadLocalRandom;

public class Deleter extends ThreadType
{
	// Constructor
	Deleter(String name)
	{
		super(name);
	}

	// Can Delete
	public boolean canAct()
	{
		return true;
	}

	// Delete
	public void action() throws InterruptedException
	{
		// print out
		System.out.println(Thread.currentThread().getName() + " Delete");
		Thread.sleep(5000);
	}
}
