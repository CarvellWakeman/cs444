public class Worker extends Thread implements Runnable
{
    protected String name;
	protected int id;

	// Constructor
    Worker(String name, int id)
	{
        super(name+String.valueOf(id));

        this.id = id;
        this.name = name;
	}

    public boolean canWork()
    {
        return true;
    }

	public void work() throws InterruptedException
	{
		// Work
		System.out.println(Thread.currentThread().getName());
		System.out.flush();
		Thread.sleep(500);
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
                work();
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}