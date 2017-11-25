import java.util.concurrent.ThreadLocalRandom;

public class Deleter extends ThreadType
{
	// Constructor
	Deleter(String name, int id)
    {
        super(name, id);
	}

	// Can Delete
	public boolean canAct()
	{
		return !Main.GetDeleteLock() && !Main.GetInsertLock();
	}

	// Delete
	public void action() throws InterruptedException
    {
		//System.out.println(String.valueOf(ThreadType.getTime()) + " " + Thread.currentThread().getName() +
        //        " SerLck:" + String.valueOf(Main.searchLock) +
        //        " InsLck:" + String.valueOf(Main.insertLock) +
        //        " DelLck:" + String.valueOf(Main.deleteLock) +
		//		" List:" + Main.list.display());
        //System.out.flush();

        // Work
        Main.SetDeleteLock(true);
        int r = 0;
        if (Main.GetSize()>0) {
            int max = Main.GetSize();
            int min = 0;
            r = ThreadLocalRandom.current().nextInt(min, max);

            Main.DeleteItem(r);
        }

        System.out.println("["+Thread.currentThread().getName()+"]" + " List:" + Main.DisplayList() + " (Del:" + String.valueOf(r) + ")");
        System.out.flush();
        Thread.sleep(500);

        Main.SetDeleteLock(false);
    }
}
