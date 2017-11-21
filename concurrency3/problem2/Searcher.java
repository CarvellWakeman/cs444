public class Searcher extends ThreadType
{
	// Constructor
	Searcher(String name, int id)
	{
		super(name, id);
	}

	// Can Search
	public boolean canAct()
	{
        return !Main.GetDeleteLock();
	}

	// Search
	public void action() throws InterruptedException
	{
        //System.out.println(String.valueOf(ThreadType.getTime()) + " " + Thread.currentThread().getName() +
        //        " SerLck:" + String.valueOf(Main.searchLock) +
        //        " InsLck:" + String.valueOf(Main.insertLock) +
        //        " DelLck:" + String.valueOf(Main.deleteLock));
        //System.out.flush();

        Main.SetSearchLock(true);

        // Work
        System.out.println(Thread.currentThread().getName() + " List:" + Main.list.display());
        System.out.flush();
        Thread.sleep(500);

        Main.SetSearchLock(false);
    }
}
