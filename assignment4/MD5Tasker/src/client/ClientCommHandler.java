package client;

import java.rmi.RemoteException;
import server.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import server.Server;
import subclient.SubClientInterface;


public class ClientCommHandler extends UnicastRemoteObject implements ClientCommInterface {

	private static final long serialVersionUID = 1008837264497538584L;
	public static ArrayList<SubClientInterface> subClientList;
	public String currentSolution = null;
	public final int numRasperries = 1;
	public byte[] currProblem = null;
	public int currProblemSize = 0;
	public static boolean calculatingSolution = false;
	public static ServerCommInterface serv;

	protected ClientCommHandler() throws RemoteException {
		subClientList = new ArrayList<SubClientInterface>();
	}
	
	@Override
	public void publishProblem(byte[] hash, int problemsize, ServerCommInterface serv) {
		if (hash == null){
			System.out.println("Problem is empty!");
		}else {
			System.out.println("Client received new problem of size " + problemsize);
		}
		
		currProblem = hash;
		currProblemSize = problemsize;
		this.serv = serv;
		calculatingSolution=false;
	}

	@Override
	public void submitSolution(String name, String sol) throws Exception {
		System.out.println(name);
		System.out.println(sol);
		serv.submitSolution(name, sol);
		
	}

	@Override
	public void registerSubClient(SubClientInterface cc) throws Exception {
		subClientList.add(cc);
	}

}
