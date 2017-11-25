import java.util.concurrent.ThreadLocalRandom;

public class Main
{
    // Shared Resource
    private static Resource sharedResource;

    public static synchronized void IncResource()
    {
        sharedResource.IncValue();
    }
    public static synchronized int GetResource()
    {
        return sharedResource.GetValue();
    }

    // Locks
    private static int numWorkers = 0;
    private static boolean canWork = true;

    public static synchronized boolean IsResourceOpen(){ return canWork; }

    public static synchronized void AddWorker()
    {
        if (numWorkers>=3){
            canWork = false;
        } else {
            numWorkers++;
        }
    }
    public static synchronized void RemWorker()
    {
        if (numWorkers<=0){
            canWork = true;
        } else {
            numWorkers--;
        }
    }

    // Threads
    public static Worker[] workers;

	public static void main(String[] args)throws Exception
	{
	    // Resource
        sharedResource = new Resource();

		// Threads
        workers = new Worker[3];

		// Create threads
		for(int i = 0; i < workers.length; i ++){
            workers[i] = new Worker("Worker", i);
            workers[i].start();
		}
    }
}
