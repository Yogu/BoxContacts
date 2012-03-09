package de.yogularm.boxcontacts.utils;

public class Hex {
	public static String toHex(byte[] bytes) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				// could use a for loop, but we're only dealing with a single byte
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
