package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import client.ClientCommInterface;

public class ServerCommHandler extends UnicastRemoteObject implements ServerCommInterface {

	private static final long serialVersionUID = -753493834709721813L;
	HashMap<String, Integer> myScoreMap;
	private Set<ClientCommInterface> myClients;
	String currentSolution = null;
	byte[] currentHash = null;
	boolean isSolved = false;
	MessageDigest md = null;
	
	// Constructor
	public ServerCommHandler(HashMap<String, Integer> sm) throws RemoteException, NoSuchAlgorithmException {
		myScoreMap = sm;
		myClients = new HashSet<ClientCommInterface>();
		md = MessageDigest.getInstance("MD5");
	}

	@Override
	// What do to if a client registers
	public synchronized void register(String name, ClientCommInterface cc) throws Exception {
		System.out.println("Registering team " + name);
		if (!myScoreMap.containsKey(name))
			myScoreMap.put(name,0);
		myClients.add(cc);
		cc.publishProblem(currentHash, Server.PROBLEMSIZE, this);
		
	}

	public synchronized void createAndPublishProblem() throws Exception {
		// Create problem
		isSolved = false;
		currentSolution = generateRandomString(Server.PROBLEMSIZE);
		currentHash = md.digest(currentSolution.getBytes());
		for (ClientCommInterface cc : myClients) {
			try {cc.publishProblem(currentHash, Server.PROBLEMSIZE,this);}
			catch (Exception e) {System.out.println("Could not print to some client");}
		}
		System.out.println("  Problem created and published: " + currentSolution);
	}

	private String generateRandomString(int max) {
		Integer random = (int)Math.round(Math.random()*max);
		return random.toString();
	}

	@Override
	public synchronized void submitSolution(String name, String sol) {
		byte[] solHash = md.digest(sol.getBytes());
		if (Arrays.equals(solHash, currentHash)) {
			isSolved = true;
			currentHash = null; // So that one cannot submit this solution a second time and get points again
			//myScoreMap.put(name, myScoreMap.get(name)) ;
			System.out.println("  Team " + name + " solved the problem correctly");
		}else {
		//	myScoreMap.put(name, myScoreMap.get(name)-1) ;
			System.out.println("  Team " + name + " submitted an incorrect solution");
		}
		
	}

	public boolean isSolved() {
		return isSolved;
	}

}
