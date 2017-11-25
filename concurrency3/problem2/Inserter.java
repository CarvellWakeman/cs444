public class Inserter extends ThreadType
{
	// Constructor
	Inserter(String name, int id)
    {
        super(name, id);
	}

	// Can Insert
	public boolean canAct()
	{
		return !Main.GetDeleteLock() && !Main.GetInsertLock();
	}

	// Insert
	public void action() throws InterruptedException
	{
        //System.out.println(String.valueOf(ThreadType.getTime()) + " " + Thread.currentThread().getName() +
        //        " SerLck:" + String.valueOf(Main.searchLock) +
        //        " InsLck:" + String.valueOf(Main.insertLock) +
        //        " DelLck:" + String.valueOf(Main.deleteLock));
        //System.out.flush();

        Main.SetInsertLock(true);

        // Work
		Main.AddItem(name + String.valueOf(id));
        System.out.println("["+Thread.currentThread().getName()+"]" + " List:" + Main.DisplayList());
        System.out.flush();
        Thread.sleep(500);

        Main.SetInsertLock(false);
    }
}
