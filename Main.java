import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		try{
			//QUESTION 3: shift each non-space character back by 3
			decryptShift("FHQWXULRQ KLSSRSRWDPXV WR DWWDFN JDXOLVK YLOODJH DW WZHOYH", 3);
	
			// QUESTION 4: add each message & relative key character's index-in-the-alphabet together (thus emulating your table in lecture 3)
			//			 	then find the letter at that index (aka. shift each non-space character by the index-in-the-alphabet of the relative key character)
			encryptPoly("Cryptography is Cool", "JACKSON");
			
			//QUESTION 6: add each letter to the end of a respective string in a  string array, then concatenate those string together
			//				(could be more optimal with less looping, instead I could take the message's letters out-of-order, respective to the number of rails)
			encryptRailFence("ALAN TURING THE ENIGMA MACHINE", 3);
			
			/*//QUESTION 7: MISSION FAILED
			decryptTransp("AEEOUHWEOEOELEFGLSLRRANCGVTE", "CIPHERS");*/
			
			//QUESTION 8: replace spaces with underscores, sort the key, build the table, fetch columns based on the key's alphabetical order
			//				(gotten by comparing key to the sorted key)
			encryptTransp("THE JOKER SAID THAT IT WAS ALL PART OF THE PLAN", "CIPHERS");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String decryptShift(String message, int shiftAmount) {
		char[] msg = message.toUpperCase().toCharArray();

		String out = "";

		for (char m : msg)
			out += getShiftedAlph(m, -shiftAmount);

		System.out.println(out);
		return out;
	}

	private static char getShiftedAlph(char c, int shift) {
		if (c <= 'Z' && c >= 'A')
			return (char) ('A' + getAlphIndex(c + shift)); // I added 26 to prevent negative numbers being mod-ed
		else
			return (char) c;
	}

	private static int getAlphIndex(int c) {
		return (c - 'A' + 26) % 26;
	}

	private static String encryptPoly(String message, String key) throws IllegalArgumentException{
		key = key.toUpperCase();
		message = message.toUpperCase(); //I made the message all uppercase for ease, I hope that that's not an issue
		
		int keyLen = key.length();

		String out = "";

		for (int i = 0; i < message.length(); i++) {
			char temp = key.charAt(i % keyLen);
			if (temp <= 'Z' && temp >= 'A') // assumes the key only consists of letters of the alphabet
				out += getShiftedAlph(message.charAt(i), getAlphIndex(temp));
			else
				throw new IllegalArgumentException("Poly-Encrypt key contains non-alphabet letter: '" + temp + "'");
		}

		System.out.println(out);
		return out;
	}
	
	private static String encryptRailFence(String message, int numOfRails) throws IllegalArgumentException{
		if(numOfRails > message.length())
			throw new IllegalArgumentException("The number of rails is greater than the length of the message");
		
		char[] msg = message.toUpperCase().replaceAll("\\s","").toCharArray();
		
		String[] fence = new String[numOfRails];
		for(int i = 0; i < numOfRails; i++)
			fence[i] = "";
		
		String out = "";
		
		for(int i = 0, incr = 1, j = 0; i < msg.length; i++) {
			if(j >= numOfRails - incr|| j < -incr)
				incr = -incr;
			fence[j] += msg[i];
			j += incr;
			
		}
		for(String f : fence)
			out += f;
		
		System.out.println(out);
		return out;
	}
	
	
	/*
	//WIP
	private static String decryptTransp(String message, String key) {
		char[] sortedKey = key.toCharArray();
		Arrays.sort(sortedKey);
		int keyLen = key.length(), msgLen = message.length(),
				rowNum = (int)Math.ceil(msgLen/keyLen);
		String[][] table = new String[rowNum][keyLen];
		String out = "";
		for(int i = 0; i < keyLen; i++) {
			int col = key.indexOf(sortedKey[i]), temp = i * rowNum;
			table[col] = message.substring(temp, Math.min(msgLen-1, temp + rowNum)).split("");
		}
		for(int i = 0; i < rowNum; i++) {
			for(int j = 0; j < keyLen; j++) {
				out += table[i][j];
			}
		}
		System.out.println(out);
		return out;
	}
	*/
	
	private static String encryptTransp(String message, String key) {
		message = message.replace(' ', '_');
		char[] sortedKey = key.toCharArray();
		Arrays.sort(sortedKey);
		int keyLen = key.length(), msgLen = message.length(),
				rowNum = (int)Math.ceil(msgLen/keyLen);
		String[][] table = new String[keyLen][rowNum];
		String out = "";
		for(int i = 0; i < rowNum; i++) {
			int temp = i * keyLen;
			table[i] = message.substring(temp, Math.min(msgLen-1, temp + keyLen)).split("");
		}
		for(int i = 0; i < keyLen; i++) {
			int col = key.indexOf(sortedKey[i]);
			//System.out.println(col);
			for(int j = 0; j < rowNum; j++) {
				out += table[j][col];
			}
		}
		System.out.println(out);
		return out;
	}
}

