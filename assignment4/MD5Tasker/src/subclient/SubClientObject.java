package subclient;


import client.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientCommInterface;

public class SubClientObject extends UnicastRemoteObject implements SubClientInterface {

	private static final long serialVersionUID = 1008837264497538584L;
	public byte[] currProblem = null;
	int currbegins = -1;
	int currends = -1;
	
	protected SubClientObject() throws RemoteException {
		super();
	}

	public void publishSubProblem(byte[] hash, int begins,int ends) throws Exception {
		
		if (hash==null){
			System.out.println("Problem is empty!");
		}else{
			System.out.println(" Client received new problem of size from " + begins +"to "+ends+" .");
		}
		currProblem = hash;
		currbegins= begins;
		currends = ends;

	}
	
}