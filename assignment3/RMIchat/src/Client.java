
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
/*
Distributed Systems Assignment 3
Author: Mehtab Kayani(9497)
Client.Java
 */
public class Client extends UnicastRemoteObject implements ClientInterface {
	
	private static String userName;
	private static Client client;
	private static String message;
	private static boolean connectionStatus = true;

	private static Scanner scanInput = new Scanner(System.in);
	
	public static void signIn() {
		String addressIP;

		try {
			do {
				System.out.println("Set username: ");
				userName = scanInput.nextLine().trim();
				System.out.println("IP-Address to connect: ");
				addressIP = scanInput.nextLine().trim();

				client = new Client(userName, addressIP);


			} while (!client.getLoginStatus());

		} catch (RemoteException e) {
			System.out.println("Error: " + e.toString());
		} catch (MalformedURLException e) {
			System.out.println("Error: " + e.toString());
		} catch (NotBoundException e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static void users() {
		System.out.println("Users in chatroom:");
		try {
			client.listClients();
		} catch (RemoteException e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static void duringChat() {

		System.out.println("Welcome to the chat .. You can start typing messages");
	}

	public static void message() {
		message = scanInput.nextLine();

		while (message.equals("")) {
			System.out.println();
			message = scanInput.nextLine();
		}

	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		signIn();
		users();
		duringChat();
		while (connectionStatus) {
			message();
			if (message.equals("/connectedusers")) {
				users();
				System.out.println();
				duringChat();
			} else if (message.equals("/quit")) {
				client.disconnect();
				connectionStatus = false;
			}
			try {
				client.broadcast(message);
			} catch (RemoteException e) {
				System.out.println("Error: " + e.toString());
			}

		}

	}

	private static final long serialVersionUID = 1L;
	private ServerInterface server;
	private boolean isLogged = true;

	public Client(String name, String ip) throws RemoteException, MalformedURLException, NotBoundException{
		this.userName = name;
		try{
			this.server = (ServerInterface) Naming.lookup("rmi://"+ ip + "/server");
			server.connect(this);
		}
		catch (RemoteException e) {
			setLoginStatus(false);
			System.out.println("IP-Address not found!");
		}
	}

	@Override
	public void setLoginStatus(boolean isLogged) throws RemoteException {
		this.isLogged = isLogged;
	}

	public boolean getLoginStatus() throws RemoteException{
		return this.isLogged;
	}

	@Override
	public void disconnect() throws RemoteException {
		this.server.disconnectClient(this);

	}

	@Override
	public void displayMessage(String msg) throws RemoteException {
		System.out.println(msg);
	}

	@Override
	public void broadcast(String msg) throws RemoteException {
		this.server.broadcast(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " " 
	+ this.getName() + ": " + msg.replace("\n", ""));
	}

	public void listClients() throws RemoteException{
		this.server.listClients(this);
	}
	@Override
	public String getName() {
		return userName;
	}

	@Override
	public void setName(String name) {
		this.userName = name;
	}

}
