
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
/*
Distributed Systems Assignment 3
Author: Mehtab Kayani(9497)
Server.Java
*/

public class Server extends UnicastRemoteObject implements ServerInterface {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<ClientInterface> clients;
	
	protected Server() throws RemoteException {
		this.clients = new ArrayList<ClientInterface>();
	}

	@Override
	public void listClients(ClientInterface client) throws RemoteException {
		String clientList = "";
		for (ClientInterface c : clients) {
            clientList += " " + c.getName();
		}
		client.displayMessage(clientList);
	}

	@Override
	public void connect(ClientInterface client) throws RemoteException {
		
		if (clients.isEmpty()){
			client.setLoginStatus(true);
			clients.add(client);
		}
		
		else{
			for (ClientInterface u : clients) {
				if(u.getName().equals(client.getName())){
					client.setLoginStatus(false);
					client.displayMessage("Name already taken!");
					break;
				}
				else{
					client.setLoginStatus(true);
					clients.add(client);
                    this.broadcast(client.getName() + " entered the chat!");
					break;
				}
			}
		}
	}

	public ArrayList<ClientInterface> getclients() {
		return clients;
	}
	
	@Override
	public void disconnectClient(ClientInterface client) throws RemoteException {
		clients.remove(client);
        client.displayMessage("Successfully logged out!");
        this.broadcast(client.getName() + " has left!");
	}
	
	@Override
	public void broadcast(String message) throws RemoteException {
		for (ClientInterface c : clients) {
			c.displayMessage(message);
		}
	}
// The main method
	public static void main(String[] args) {
		
		try {
			Server kayani = new Server();
			/* creating the registry*/
		LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			//LocateRegistry.createRegistry(1099);
			Naming.bind("server", kayani);
			//Naming.bind("//10.7.153.53/server", kayani);
			System.out.println(" Server is running and is available for users to connect... \n");
			
        } catch (RemoteException e) {
            System.out.println("Error: " + e.toString());
        } catch (MalformedURLException e) {
            System.out.println("Error: " + e.toString());
        } catch (AlreadyBoundException e) {
            System.out.println("Error: " + e.toString());
        }
		
	}
}