package client;

import java.net.Inet4Address;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.security.MessageDigest;
import java.util.Arrays;

import server.MySecurityManager;
import server.ServerCommHandler;
import server.ServerCommInterface;

public class Client {

	public static void main(String[] args) throws Exception {

		System.out.println("Client starting...");
		
		byte[] problem = null;
		
		//Client part
		//10.7.153.53
		//10.7.145.46
		
		ServerCommInterface sci = (ServerCommInterface)Naming.lookup("rmi://localhost:1099/server");
		
		ClientCommHandler cch = new ClientCommHandler();
		System.out.println("Client registers with the server");
		
		String name = "noi";
		sci.register(name, cch);
		
		//Server part
		
		System.setSecurityManager(new MySecurityManager());

		LocateRegistry.createRegistry(3232);
		
		String addressClient = Inet4Address.getLocalHost().getHostAddress();
		System.setProperty("java.rmi.server.hostname",addressClient);

		ClientCommHandler cc = new ClientCommHandler();
		
		Naming.rebind("rmi://"+addressClient+":3232/raspberry", cc);
		
		while (true) {
			while (cch.currProblem==null){
				Thread.sleep(5);
			}
			
			while(cch.subClientList.size() != cch.numRasperries){
				Thread.sleep(5);
			}
			
			problem = cch.currProblem;
			
			int subproblemSize=0;
			
			if(cch.subClientList!=null){
				subproblemSize =  cch.currProblemSize/cch.subClientList.size();
			}
			
			if(!cch.calculatingSolution){
				for(int i = 0 ; i < cch.subClientList.size();i++){
					cch.subClientList.get(i).publishSubProblem(cch.currProblem,i*subproblemSize,((i+1)*subproblemSize)-1);
				}
				cch.calculatingSolution = true;
			}
			
		}
//			cch.subclientList.get(0).publishSubProblem(cch.currentHash, 0, cch.currProblemSize/2);
//			cch.subclientList.get(1).publishSubProblem(cch.currentHash, cch.currProblemSize/2+1 ,cch.currProblemSize);
	}
}
