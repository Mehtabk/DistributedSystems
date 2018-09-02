package server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class Server {

	// Determines how hard the problems are, the larger this number, the longer cracking will take
	public static final int PROBLEMSIZE = 10000000;

	public static void main(String[] args) throws Exception {
		
		// How many problems will be generated
		int RUNS = 20;// Integer.parseInt(args[0]);
		
		System.setSecurityManager(new SecurityManager());

		LocateRegistry.createRegistry(1099);
		HashMap<String,Integer> scoreMap = new HashMap<String,Integer>();
		ServerCommHandler sc = new ServerCommHandler(scoreMap);
		Naming.rebind("rmi://10.7.162.144/server", sc);
		
		// Sleep for 5 seconds to give clients time to register before giving the first task
		// But it is also possible to register later..
		Thread.sleep(5000);
		System.out.println("Server starts...");
		
		// Now create problems and wait until they get solved
		for (int r=1; r<=RUNS; r++) {
			System.out.println("\n  Creating Problem " + r);
			sc.createAndPublishProblem();
			// wait until problem is solved..
			while (!sc.isSolved()) { Thread.sleep(5);}
		}
		sc.currentHash = null;
		System.out.println("\nTasks finished");
		
		// Print the results
		printScores(scoreMap);
	}

	// To print the final scores
	private static void printScores(HashMap<String, Integer> scoreMap) {
		System.out.println("\nFinal score:");
		for (String s : scoreMap.keySet())
			System.out.println("  Team: " + s + " - score: " + scoreMap.get(s));
		
	}

}
