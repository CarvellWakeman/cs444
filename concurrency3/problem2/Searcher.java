import java.util.concurrent.ThreadLocalRandom;

public class Searcher extends ThreadType
{
	// Constructor
	Searcher(String name)
	{
		super(name);
	}

	// Can Search
	public boolean canAct()
	{
        return true;
	}

	// Search
	public void action() throws InterruptedException
	{
		// print out
		System.out.println(Thread.currentThread().getName() + " Search");
		Thread.sleep(5000);
	}
}
