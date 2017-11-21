public abstract class ThreadType extends Thread implements Runnable
{
    protected String name;
	protected int id;
	private static double initialTime = 0.0d;

	static {
        initialTime = getTime();
    }

	// Constructor
	ThreadType(String name, int id)
	{
        super(name+String.valueOf(id));

        this.id = id;
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
                //else {
                //    System.out.println(String.valueOf(ThreadType.getTime()) + " " + name +
                //            " ID:" + id + " Cannot take action");
                //}
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}

	public static double getTime()
	{
		double a = (System.nanoTime() / 1000000000.0) - initialTime;
		return Math.round(a * 10.0) / 10.0;
	}
}
