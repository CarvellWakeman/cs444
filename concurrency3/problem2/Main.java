import java.util.concurrent.ThreadLocalRandom;

public class Main
{
    // Locks
   private static boolean searchLock = false;
   private static boolean insertLock = false;
   private static boolean deleteLock = false;

    public static synchronized boolean GetSearchLock(){return searchLock;}
    public static synchronized boolean GetInsertLock(){return insertLock;}
    public static synchronized boolean GetDeleteLock(){return deleteLock;}

    public static synchronized void SetSearchLock(boolean var){searchLock = var;}
    public static synchronized void SetInsertLock(boolean var){insertLock = var;}
    public static synchronized void SetDeleteLock(boolean var){deleteLock = var;}

    // Singly linked list
    static LinkedList list;


    // Threads
    public static Searcher[] searchers;
    public static Inserter[] inserters;
    public static Deleter[] deleters;

	public static void main(String[] args)throws Exception
	{
        // Create singly linked list
        list = new LinkedList();

		// Threads
		searchers = new Searcher[5];
		inserters = new Inserter[5];
		deleters = new Deleter[5];

		// Create Searchers
		for(int i = 0; i < searchers.length; i ++){
			searchers[i] = new Searcher("Search", i);
			searchers[i].start();
		}

        // Create Inserters
        for(int i = 0; i < inserters.length; i ++){
            inserters[i] = new Inserter("Insert", i);
            inserters[i].start();
        }

        // Create Deleters
        for(int i = 0; i < deleters.length; i ++){
            deleters[i] = new Deleter("Delete", i);
            deleters[i].start();
        }


        // Linked list testing
        //while (true){
        //    System.out.println("ListB4:" + list.display());
        //    int r = ThreadLocalRandom.current().nextInt(0,2);
        //    switch(r){
        //        case 0: // insert
        //            list.insertAtEnd("Data" + String.valueOf(list.getSize()));
        //            break;
        //        case 1: // delete
        //            int d = ThreadLocalRandom.current().nextInt(0, Math.max(1,Main.list.getSize()));
        //            System.out.println("Delete:" + String.valueOf(d));
        //            list.deleteAtPos(d);
        //            break;
        //    }
        //    System.out.println("ListA4:" + list.display());
        //}
        //System.out.println("List:" + Main.list.display());
        //// Insert first value
        //list.insertAtEnd("Data0");
        //System.out.println("List:" + Main.list.display());
        //// Insert second value
        //list.insertAtEnd("Data1");
        //System.out.println("List:" + Main.list.display());
        //// Insert third value
        //list.insertAtEnd("Data2");
        //System.out.println("List:" + Main.list.display());
        //// Remove
        //int r = ThreadLocalRandom.current().nextInt(0, Main.list.getSize());
        //System.out.println("RIndex:" + String.valueOf(r));
        //list.deleteAtPos(r);
        //System.out.println("List:" + Main.list.display());
    }
}
