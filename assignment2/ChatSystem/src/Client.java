import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
Distributed Systems Assignment 2
Author: Mehtab Kayani(9497)
Client.Java
*/
public class Client extends JFrame{
	
	public static void main(String[] args) {
		 
// test Run
		
		Client client;
		client = new Client("127.0.0.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.start();
	}
	private String IPserver;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	//GUI components
	private JTextField textUsr;
	private JTextArea chatArea;

	private String msg = "";
	
	
	//constructor
	public Client(String host){
		super("Client ChatSystem");
		IPserver = host;
		textUsr = new JTextField();
		textUsr.setEditable(false);
		textUsr.addActionListener(
				new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					textUsr.setText("");
				}
			}
		);
		add(textUsr, BorderLayout.NORTH);
		chatArea = new JTextArea();
		add(new JScrollPane(chatArea));
		setSize(300, 150); 
		setVisible(true); 
		
	}
	
	//connect to server
	public void start(){
		try{
			serverConnection();
			setStreams();
			duringChat();
		}catch(EOFException eofException){
			displayMessage("\n  The connection has been terminated by the client");
		}catch(IOException ioE){
			ioE.printStackTrace();
		}finally{
			disconnect();
		}
	}
	
	//connecting to the server
	private void serverConnection() throws IOException{
		displayMessage("\n Trying to establish connection... \n");
		socket = new Socket(InetAddress.getByName(IPserver), 6789);
		displayMessage("Connection Established! Connected to: " + socket.getInetAddress().getHostName());
	}
	
	// setting up the streams
	private void setStreams() throws IOException{
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		displayMessage("\n The streams are set now.");
	}
	
	//while chatting 
	private void duringChat() throws IOException{
		enableTyping(true);
		do{
			try{
				msg = (String) in.readObject();
				displayMessage("\n" + msg);
			}catch(ClassNotFoundException classNotFoundException){
				displayMessage("AN unkown object received!");
			}
		}while(!msg.equals("SERVER - /quit"));	
	}
	
	//Closing connection function for client
	private void disconnect(){
		displayMessage("\n Disconnecting !");
		enableTyping(false);
		try{
			out.close();
			in.close();
			socket.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//sending message to server
	private void sendMessage(String message){
		try{
			out.writeObject("CLIENT - " + message);
			out.flush();
			displayMessage("\nCLIENT - " + message);
		}catch(IOException ioException){
			chatArea.append("\n Sorry! Something has gone wrong");
		}
	}
	
	//updating the chat window after the reception of the message
	private void displayMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatArea.append(message);
				}
			}
		);
	}
	
	//Enable typing for users
	private void enableTyping(final boolean et){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					textUsr.setEditable(et);
				}
			}
		);
	}
}