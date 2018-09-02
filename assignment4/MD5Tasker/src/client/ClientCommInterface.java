package client;

import java.rmi.Remote;
import server.*;

import subclient.SubClientInterface;

public interface ClientCommInterface extends Remote {

	void publishProblem(byte[] hash, int problemsize, ServerCommInterface serv) throws Exception;
	
	public void submitSolution(String name, String sol) throws Exception;

	void registerSubClient(SubClientInterface cc) throws Exception;

}
