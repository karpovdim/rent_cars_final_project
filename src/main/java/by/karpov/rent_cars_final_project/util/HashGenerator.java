package by.karpov.rent_cars_final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class HashGenerator {
	
	private static final Logger LOGGER = LogManager.getLogger(HashGenerator.class);

	private static final HashGenerator INSTANCE = new HashGenerator();
	
	private static final String ALGORITHM_SHA256 = "SHA-256";
	
	private static final String HEXADECIMAL_FORMAT = "%02x";


	private HashGenerator() {
	}
	public static HashGenerator getInstance() {
		return INSTANCE;
	}


	public String generatePasswordHash(String password) {
		LOGGER.info("method generatePasswordHash()");
		byte[] passwordBytes = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_SHA256);
			messageDigest.update(password.getBytes());
			passwordBytes = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error( "Algorithm is not found", e);
		}
		String result = convertBytesToString(passwordBytes);
		LOGGER.info("password has been hashed");
		return result;
	}


	private static String convertBytesToString(byte[] passwordBytes) {
		StringBuilder builder = new StringBuilder();
		for (byte b : passwordBytes) {
			builder.append(String.format(HEXADECIMAL_FORMAT, b));
		}
		return builder.toString();
	}
}
