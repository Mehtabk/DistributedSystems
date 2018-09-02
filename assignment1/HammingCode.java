
import java.util.*;
import java.io.*;
/*
//Distributed Systems Assignment 
//Authors:  Baig Mirza Abdullah(12315) and Mehtab Kayani(9497)
*/
public class HammingCode {
	public static void main (String args[]){

		System.out.println("0. Enter a string for encoding.");
		System.out.println("1.  Enter the previous message for decoding(if any). ");

		System.out.println("Choose a number either 0 or 1 to perform the operation. ");
		//Taking the input option       
		Scanner option = new Scanner(System.in);

		int selectOP = option.nextInt();

		if(selectOP == 0){
			// Taking the string as an input for encoding 
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter a message for encoding: ");
			String msg = scan.nextLine();
			encode(msg, "Code.txt");
		}
		else if (selectOP == 1){
			decode("Code.txt");
		}
		else{
			System.out.println("Incorrect number. Please enter a valid nr.");
			System.exit(1);
		}	



	}
	// Encoding
	public static void encode(String msg, String filename){

		try {
			PrintWriter writer = new PrintWriter(filename);

			for (int i=0; i<msg.length(); i++){

				String binary = Integer.toBinaryString(msg.charAt(i));

				if(binary.length()<8){	//create 8 bit binary string if they are less

					while(binary.length() != 8){
						binary = '0'+binary;
					}
				}

				String binary1 = ""+binary.charAt(0)+binary.charAt(1)+binary.charAt(2)+binary.charAt(3);
				String binary2 = ""+binary.charAt(4)+binary.charAt(5)+binary.charAt(6)+binary.charAt(7);

				writer.println(binary1 +":"+ checkParitybits(binary1)+":"+binary2 +":"+ checkParitybits(binary2));


				System.out.println (binary1 + checkParitybits(binary1)+"\t"+binary2+checkParitybits(binary2));

			}
			writer.close();

			System.out.println("The binary message :  "+msg+"  : is now saved in "+filename);

			System.out.println("");
			System.out.println(" In case of introducing one bit error, please write it in the file  "+filename+" .");

			Scanner scanDecode = new Scanner(System.in);

			System.out.println("Do you want to decode the message inserted (yes or no):  ");
			if(scanDecode.nextLine().equals("yes") ){
				decode(filename);
			}else{
				System.exit(1);
			}

		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}


	}
	// Decoding 
	public static void decode(String filename){

		File file = new File (filename);

		try {
			Scanner scanFile = new Scanner(file);

			StringBuilder decString = new StringBuilder("");

			int countErr = 0;

			while(scanFile.hasNext()){
				String decodeString = scanFile.nextLine();
				String [] Binarray = decodeString.split(":");

				if(!Binarray[1].equals(checkParitybits(Binarray[0])) ){			
					countErr++;

					Binarray[0]= fixBinarray(Binarray[1], checkParitybits(Binarray[0]), Binarray[0]);
				}

				if (!Binarray[3].equals(checkParitybits(Binarray[2])) ){		
					countErr++;

					Binarray[2]=fixBinarray(Binarray[3], checkParitybits(Binarray[2]), Binarray[2]);
				}

				decString.append(Character.toChars(Integer.parseInt(Binarray[0]+Binarray[2],2)));

			}
			System.out.println("Total 1 bit errors decoded and corrected : " +countErr);
			System.out.println("The decoded String is : "+decString.toString());

		} catch (FileNotFoundException ex) {

			ex.printStackTrace();
		}


	}

	public static String checkParitybits(String databits){

		String checkParitybits;
		int par = (Integer.parseInt(""+databits.charAt(0), 2)^Integer.parseInt(""+databits.charAt(1), 2)^Integer.parseInt(""+databits.charAt(2), 2));
		int par1 = (Integer.parseInt(""+databits.charAt(1), 2)^Integer.parseInt(""+databits.charAt(2), 2)^Integer.parseInt(""+databits.charAt(3), 2));
		int par2 = (Integer.parseInt(""+databits.charAt(2), 2)^Integer.parseInt(""+databits.charAt(3), 2)^Integer.parseInt(""+databits.charAt(0), 2));
		checkParitybits = ""+par+""+par1+""+par2;
		return checkParitybits;

	}

	// Method for detecting and correcting 1 bit errors
	public static String fixBinarray (String encodeParitybits, String decodeParitybits, String databits){

		StringBuilder databit = new StringBuilder(databits + encodeParitybits);
		System.out.print(databit.toString() + " ---> ");

		String errBit;												 
		String[][] error = { {"101","0"},{"110","1"},				
				{"111","2"},{"011","3"},
				{"100","4"},{"010","5"},{"001","6"} };

		if(encodeParitybits.charAt(0) == decodeParitybits.charAt(0)){
			errBit = "0";
		}else{
			errBit = "1";
		}

		if(encodeParitybits.charAt(1) == decodeParitybits.charAt(1)){
			errBit =errBit+ "0";
		}else{
			errBit =errBit+ "1";
		}

		if(encodeParitybits.charAt(2) == decodeParitybits.charAt(2)){
			errBit = errBit+"0";
		}else{
			errBit = errBit+"1";
		}

		for (int i=0; i<error.length; i++){

			if(errBit.equals(error[i][0])){

				int errIndex = Integer.parseInt(error[i][1]);

				if(databit.charAt(errIndex) == '0'){
					databit.setCharAt(errIndex, '1');
				}else{
					databit.setCharAt(errIndex, '0');
				}

			}
		}

		System.out.println(databit.toString());
		return databit.toString().substring(0, 4);
	}

}

