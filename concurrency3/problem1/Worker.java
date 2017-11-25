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
        return Main.IsResourceOpen();
    }

	public void work() throws InterruptedException
	{
		Main.AddWorker();

		// Work
		System.out.println(Thread.currentThread().getName() + " - Resource new value:" + String.valueOf(Main.GetResource()));
		System.out.flush();

        Main.IncResource();

        Thread.sleep(1000 + id*100);

		Main.RemWorker();
	}

	@Override
	public void run()
	{
		try
		{
			while(true)
			{
                if (canWork())
                {
                    work();
                }
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}
