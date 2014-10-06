package com.studentLotto.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.mail.MessagingException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.studentLotto.support.mail.MailSenderImpl;
import com.studentLotto.support.mail.MessageCreator;

/**
 * 
 * @author Elie the account utilities class used to assist the account ORM with
 *         the account related operations
 *
 */
public class AccountUtilities {

	private enum ACTIVATION_CODES_STATUS {
		KEY_UNIQUE, KEY_EXISTS_FOR_USER, KEY_EXISTS_FOR_OTHER_USER
	}

	private static final int INACTIVE_ACCOUNT = 0;

	@Autowired
	// private AccountActivationRepository accountActivationRepo;
	public AccountActivation emailAccountActivation(String email,
			String password, AccountActivationRepository accountActivationRepo) {
		int randomNum = 0;
		int increment = 0;
		AccountActivation accountActivation = null;
		// generates a random number between 0 - 1000
		Random rnd = new Random(1000);

		// generate the first activation key
		String activationkey = null;
		try {
			activationkey = generateAccountActivationKey(email, password);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e2) {
			System.out
					.println("cannot run the SHA algorithm or encode to byte64");
			return null;
		}
		// for some reasons we can't perform the SHA or the encode to 64 bytes
		// algorithm!! Big problem. bail out.
		if (activationkey == null) {
			return null;
		}

		ACTIVATION_CODES_STATUS acs = null;
		try {
			acs = isActivationKeyUnique(email, activationkey,
					accountActivationRepo);
		} catch (MyException e1) {
			// TODO Auto-generated catch block
			System.out.println("cannot verify activation code if unique");
			return null;
		}
		// if the key exists for other user, then we need to generate a
		// different key for the current user
		if (acs == ACTIVATION_CODES_STATUS.KEY_EXISTS_FOR_OTHER_USER) {
			// Keep generating activation key until we find a unique key
			while (acs == ACTIVATION_CODES_STATUS.KEY_EXISTS_FOR_OTHER_USER) {
				randomNum = rnd.nextInt();
				String modifiedEmail = email + randomNum;
				String modifiedPassword = password + randomNum;
				try {
					activationkey = generateAccountActivationKey(modifiedEmail,
							modifiedPassword);
				} catch (NoSuchAlgorithmException
						| UnsupportedEncodingException e1) {

					System.out
							.println("cannot run the SHA algorithm or encode to byte64");
					return null;
				}
				// avoid infinite loop after 10 tries
				if (increment > 10) {
					break;
				}
				try {
					acs = isActivationKeyUnique(email, activationkey,
							accountActivationRepo);
				} catch (MyException e) {
					// TODO Auto-generated catch block
					System.out.println("cannot verify activation key");
					return null;
				}
				increment++;

			}
		}
		// at this point we have a good and a unique activation key
		// we first save the information {email, activationKey} to the
		// AccountActivation database
		if (acs == ACTIVATION_CODES_STATUS.KEY_UNIQUE) {
			accountActivation = new AccountActivation(email, activationkey,
					INACTIVE_ACCOUNT);
		}
		// if key already exists for the same user, we dont need to re save it!
		// instead all we need to do is re generate the activation link and
		// email it to the user
		new MailSenderImpl().sendMail("sweng505team3@gmail.com", email,
				"Account Activation", new MessageCreator()
						.registrationValidationEmail(email, activationkey));
		return accountActivation;
	}

	public String generateAccountActivationKey(String emailAddress,
			String password) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		String accountActivationkey = null;
		// create the base key out of email and password
		String baseKey = emailAddress + password;

		// Encrypt the base key using SHA-256
		MessageDigest digest;

		digest = MessageDigest.getInstance("SHA-256");

		byte[] hash = digest.digest(baseKey.getBytes("UTF-8"));
		// convert the base key to base 64
		byte[] encodedBytes = Base64.encodeBase64(bytesToString(hash)
				.getBytes());
		// Convert the encoded bytes to string
		accountActivationkey = new String(encodedBytes);

		// return the activation key
		return accountActivationkey;
	}

	public ACTIVATION_CODES_STATUS isActivationKeyUnique(String email,
			String activationCode,
			AccountActivationRepository accountActivationRepo)
			throws MyException {
		String databaseEmail = "";

		databaseEmail = accountActivationRepo
				.doesActivationCodeExist(activationCode);
		System.out.println(databaseEmail);

		if (databaseEmail == null) {
			return ACTIVATION_CODES_STATUS.KEY_UNIQUE;
		}
		// case 1: the key can exist for the same user, in this case we
		// shouldn't
		// re update the database with the {same email, same key}
		else if (databaseEmail.equals(email)) {
			return ACTIVATION_CODES_STATUS.KEY_EXISTS_FOR_USER;
			// in this case, the key is unique and was not found
		}
		// in this case, their was a key "clash" as in the key existed for other
		// user due to the SHA 256 salt effect
		return ACTIVATION_CODES_STATUS.KEY_EXISTS_FOR_OTHER_USER;
	}

	/**
	 * Method used to convert the encrypted sha256 bytes to string format
	 * 
	 * @param bytes
	 *            the bytes to be converted
	 * @return sha256 string representation
	 */
	public String bytesToString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

}
