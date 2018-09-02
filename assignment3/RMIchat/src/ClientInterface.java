
import java.rmi.Remote;
import java.rmi.RemoteException;
/*
Distributed Systems Assignment 3
Author: Mehtab Kayani(9497)
ClientInterface.Java
*/
public interface ClientInterface extends Remote{
    public void disconnect() throws RemoteException;
    public void setLoginStatus(boolean loginValid) throws RemoteException;
    public boolean getLoginStatus() throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String nome) throws RemoteException;
    public void displayMessage(String msg) throws RemoteException;
    public void broadcast(String msg) throws RemoteException;

}