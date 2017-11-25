import java.util.concurrent.ThreadLocalRandom;

public class Main
{
    // Locks
    private static int numWorkers = 0;

    // Threads
    public static Worker[] workers;

	public static void main(String[] args)throws Exception
	{
		// Threads
        workers = new Worker[3];

		// Create threads
		for(int i = 0; i < workers.length; i ++){
            workers[i] = new Worker("Worker", i);
            workers[i].start();
		}
    }
}