import java.util.concurrent.ThreadLocalRandom;

public class Inserter extends ThreadType
{
	// Constructor
	Inserter(String name)
	{
		super(name);
	}

	// Can Insert
	public boolean canAct()
	{
		return true;
	}

	// Insert
	public void action() throws InterruptedException
	{
		// print out
		System.out.println(Thread.currentThread().getName() + " Insert");
		Thread.sleep(5000);
	}
}
