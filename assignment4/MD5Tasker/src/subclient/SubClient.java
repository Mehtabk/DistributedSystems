package subclient;

import java.rmi.Naming;
import java.security.MessageDigest;
import java.util.Arrays;

import client.ClientCommInterface;
import server.ServerCommInterface;

public class SubClient {
	
	public static void main(String[] args) throws Exception {

		System.out.println("SubClient starting...");
		
		byte[] problem = null;
		
		ClientCommInterface sc = (ClientCommInterface)Naming.lookup("rmi://10.7.153.53:3232/raspberry");

		SubClientObject cch = new SubClientObject();
		
		System.out.println("Client registers with the server");
		sc.registerSubClient(cch);
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		while (true) {
			
			while (cch.currProblem==null) {
				Thread.sleep(5);
			}
			problem = cch.currProblem;
			
			for (Integer i=cch.currbegins; i<=cch.currends; i++) {
				byte[] currentHash = md.digest(i.toString().getBytes());
				
				if (Arrays.equals(currentHash, problem)) {
					System.out.println("SubClient submits solution");
					sc.submitSolution("the", i.toString());
					cch.currProblem=null;
					break;
				}
			}
		
		}
	}

}
