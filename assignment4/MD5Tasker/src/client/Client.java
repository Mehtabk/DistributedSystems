package client;

import java.rmi.Naming;
import java.security.MessageDigest;
import java.util.Arrays;

import server.ServerCommInterface;

public class Client {

	public static void main(String[] args) throws Exception {

		System.out.println("Client starting..");
		
		// Initially we have no problem :)
		byte[] problem = null;
		
		// Lookup the server
		ServerCommInterface sci = (ServerCommInterface)Naming.lookup("rmi://localhost/server");
		
		// Create a communication handler and register it with the server
		// The communication handler is the object that will receive the tasks from the server
		ClientCommHandler cch = new ClientCommHandler();
		System.out.println("Client registers with the server");
		sci.register("TheCoolTeam", cch);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		// Now forever solve tasks given by the server
		while (true) {
			// Wait until getting a problem from the server
			while (cch.currProblem==null) {Thread.sleep(5);}
			problem = cch.currProblem;
			// Then bruteforce try all integers till problemsize
			for (Integer i=0; i<=cch.currProblemSize; i++) {
				// Calculate their hash
				byte[] currentHash = md.digest(i.toString().getBytes());
				// If the calculated hash equals the one given by the server, submit the integer as solution
				if (Arrays.equals(currentHash, problem)) {
					System.out.println("client submits solution");
					sci.submitSolution("TheCoolTeam", i.toString());
					cch.currProblem=null;
					break;
				}
			}
		}
	}
}
