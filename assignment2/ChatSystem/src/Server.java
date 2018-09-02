import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
/*
Distributed Systems Assignment 2
Author: Mehtab Kayani(9497)
Server.Java
*/
public class Server extends JFrame {
	
	//Testing server
	public static void main(String[] args) {
		Server kayani = new Server();
		kayani.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kayani.start();
	}
	
	//GUI components
	private JTextField textUsr;
	private JTextArea chatArea;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ServerSocket serverSocket;
	private Socket socket;
	
	//constructor
	public Server(){
		super("Server ");
		textUsr = new JTextField();
		textUsr.setEditable(false);
		textUsr.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent e){
					sendMessage(e.getActionCommand());
					textUsr.setText("");
				}
			}
		);
		add(textUsr, BorderLayout.NORTH);
		chatArea = new JTextArea();
		add(new JScrollPane(chatArea));
		setSize(500, 300); //Sets the window size
		setVisible(true);
		

	}
	
	public void start(){
		try{
			// Port 6789 is used for testing and can be edited, 100 is the maximum user limit. 
			serverSocket = new ServerSocket(6789, 100); 
			  
			while(true){
				try{
					//Trying to connect and converse 
					waitConnection();
					setStreams();
					duringChat();
				}catch(EOFException eofException){
					displayMessage("\n Server ended the connection! ");
				} finally{
					disconnect(); 
				}
			}
		} catch (IOException ioE){
			ioE.printStackTrace();
		}
	}
	//waiting  for connection to be established and then show connection information
	private void waitConnection() throws IOException{
		displayMessage(" Server running and available for users to connect... \n");
		socket = serverSocket.accept();
		displayMessage(" Now connected to " + socket.getInetAddress().getHostName());
	}
	
	//getting the streams set to send and receive data
	private void setStreams() throws IOException{
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		
		in = new ObjectInputStream(socket.getInputStream());
		
		displayMessage("\n Streams are set now \n");
	}
	
	//during the chat conversation
	private void duringChat() throws IOException{
		String message = " You are now connected now ";
		sendMessage(message);
		enableTyping(true);
		do{
			try{
				message = (String) in.readObject();
				displayMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				displayMessage("The user has sent an unknown object!");
			}
		}while(!message.equals("CLIENT - /quit"));
	}
	//Closing connection function for server
	public void disconnect(){
		displayMessage("\n Disconnecting... \n");
		enableTyping(false);
		try{
			out.close(); //output path closed for the client
			in.close(); //Input path to the server closed from the client 
			socket.close(); //closing the socket connection
		}catch(IOException ioE){
			ioE.printStackTrace();
		}
	}
	
	//Sending message to the client
	private void sendMessage(String message){
		try{
			out.writeObject("SERVER - " + message);
			out.flush();
			displayMessage("\nSERVER -" + message);
		}catch(IOException ioException){
			chatArea.append("\n ERROR: Message can not be sent, Please try again");
		}
	}
	
	//Updating the window after the message
	private void displayMessage(final String txt){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatArea.append(txt);
				}
			}
		);
	}
	//Enable typing for server
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