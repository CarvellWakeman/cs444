public class Main
{
	public static void main(String[] args)throws Exception
	{
		// Create linked list

		// Threads
		Searcher[] searchers = new Searcher[1];
		Inserter[] inserters = new Inserter[1];
		Deleter[] deleters = new Deleter[1];

		// Create Searchers
		for(int i = 0; i < searchers.length; i ++){
			searchers[i] = new Searcher(String.valueOf(i));
			searchers[i].start();
		}

        // Create Inserters
        for(int i = 0; i < inserters.length; i ++){
            inserters[i] = new Inserter(String.valueOf(i));
            inserters[i].start();
        }

        // Create Deleters
        for(int i = 0; i < deleters.length; i ++){
            deleters[i] = new Deleter(String.valueOf(i));
            deleters[i].start();
        }
	}
}
