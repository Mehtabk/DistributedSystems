package server;

import java.net.Inet4Address;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

public class Server {

	public static final int PROBLEMSIZE = 10;
	
	public static void main(String[] args) throws Exception {
		
		int RUNS = 20;
	
		System.setSecurityManager(new MySecurityManager());
		
		String address = Inet4Address.getLocalHost().getHostAddress();
		
		System.setProperty("java.rmi.server.hostname",address);
		
		LocateRegistry.createRegistry(1099);
		HashMap<String,Integer> scoreMap = new HashMap<String,Integer>();
		
		ServerCommHandler sc = new ServerCommHandler(scoreMap);
		Naming.rebind("rmi://"+address+":1099/server", sc);
		
		Thread.sleep(5000);
		System.out.println("Server starts...");
		
		for (int r=1; r<=RUNS; r++) {
			System.out.println("\n  Creating Problem " + r);
			sc.createAndPublishProblem();
			while (!sc.isSolved()) { 
				Thread.sleep(5);
			}
		}
		
		sc.currentHash = null;
		System.out.println("\nTasks finished");
		
		printScores(scoreMap);
	}
	private static void printScores(HashMap<String, Integer> scoreMap) {
		System.out.println("\nFinal score:");
		
		for (String s : scoreMap.keySet()){
			System.out.println("  Team: " + s + " - score: " + scoreMap.get(s));
		}
	}

}
