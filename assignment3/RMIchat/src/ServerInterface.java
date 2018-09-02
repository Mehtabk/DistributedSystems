
import java.rmi.RemoteException;
import java.rmi.Remote;
/*
Distributed Systems Assignment 3
Author: Mehtab Kayani(9497)
ServerInterface.Java
*/
public interface ServerInterface extends Remote{
    public void listClients(ClientInterface client) throws RemoteException;
    public void connect(ClientInterface client) throws RemoteException;
    public void disconnectClient(ClientInterface client) throws RemoteException;
    public void broadcast(String message) throws RemoteException;
}
