package subclient;

import java.rmi.Remote;

public interface SubClientInterface extends Remote {
	
	void publishSubProblem(byte[] hash, int begins, int ends) throws Exception;
}
