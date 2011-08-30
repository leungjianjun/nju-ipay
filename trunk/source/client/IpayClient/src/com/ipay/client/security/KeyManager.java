package com.ipay.client.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.ipay.client.security.tool.codec.Hex;
import com.ipay.client.security.tool.encrypt.BytesEncryptor;
import com.ipay.client.security.tool.encrypt.Encryptors;
import com.ipay.client.security.tool.util.Digest;

public class KeyManager {
	
	private final static String BANK_PUBLIC_KEY_LOCATION = Thread.currentThread().getContextClassLoader().getResource("bank_public.key").getPath();
	
	public static byte[] getBankPublickey(){
		File file = new File(BANK_PUBLIC_KEY_LOCATION);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			byte[] publickey = new byte[162];
			fis.read(publickey);
			return publickey;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 鐢熸垚RSA瀵嗙爜�?�涔熷氨鏄叕閽ュ拰绉侀�?
	 * 
	 * @return
	 * 		瀵嗙爜�?�?	 */
	public static KeyPair generatorKeypair(){
		KeyPair myPair;
		long mySeed;
		mySeed = System.currentTimeMillis();
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			random.setSeed(mySeed);
			keyGen.initialize(1024, random);
			myPair = keyGen.generateKeyPair();
		} catch (Exception e1) {
			return null;
		}
		return myPair;
	}
	
	/**
	 * 浣跨敤aes-256 鍔犲瘑绉�?��
	 * @param privateKey
	 * 			绉侀�?
	 * @param rawPass
	 * 			鍘熷�?嗙爜
	 * @param salt
	 * 			鐩愬�?
	 * @return
	 * 			鍔犲瘑鐨勭閽�
	 */
	public static byte[] encryptPrivateKey(PrivateKey privateKey,String rawPass,String salt){
		BytesEncryptor be = Encryptors.standard(rawPass, String.valueOf(Hex.encode(salt.getBytes())));
		return be.encrypt(privateKey.getEncoded());
	}
	
	public static byte[] decryptPrivatekey(byte[] encryptPrivateKey,String rawPass,String salt){
		BytesEncryptor be = Encryptors.standard(rawPass, String.valueOf(Hex.encode(salt.getBytes())));
		return be.decrypt(encryptPrivateKey);
	}
	
	/**
	 * 浣跨敤绉�?��鍔犲瘑鏁版嵁 鐢ㄤ竴涓凡鎵撳寘鎴恇yte[]褰㈠紡鐨勭閽ュ姞�?嗘暟鎹紝鍗虫暟瀛楃鍚�?	 * 
	 * @param privateKeyBytes
	 *            鎵撳寘鎴恇yte[]鐨勭�?���?	 * @param message
	 *            瑕佺鍚嶇殑鏁版�?
	 * @return 绛惧�?byte[]
	 */
	public static byte[] sign(byte[] privateKeyBytes, String message) {
		try {
			PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(privKey);
			byte[] source = Digest.MdigestSHA(message);//鐢熸垚淇℃伅鎽樿�?
			sig.update(source);
			return sig.sign();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 瀵圭鍚嶈繘琛岄獙璇�?	 * 
	 * @param publicKeyBytes
	 * 			鍏�?
	 * @param source
	 * 			鍘熸暟鎹�?	 * @param sign
	 * 			绛惧悕鏁版嵁
	 * @return
	 * 			楠岃瘉缁撴灉
	 */
	public static boolean verify(byte[] publicKeyBytes, byte[] source, byte[] sign) {
		try {
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			Signature sig = Signature.getInstance("SHA1withRSA");
			X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
			sig.initVerify(pubKey);
			sig.update(source);
			return sig.verify(sign);
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 浣跨敤RSA鍏挜鍔犲瘑鏁版�?
	 * 
	 * @param publicKeyBytes
	 *            鎵撳寘鐨刡yte[]褰㈠紡鍏挜
	 * @param data
	 *            瑕佸姞�?嗙殑鏁版�?
	 * @return 鍔犲瘑鏁版嵁
	 */
	public static byte[] encryptByRSA(byte[] publicKeyBytes, byte[] data) {
		try {
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec pub_spec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey pubKey = mykeyFactory.generatePublic(pub_spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 鐢≧SA绉侀挜瑙ｅ瘑
	 * 
	 * @param privateKeyBytes
	 *            绉侀挜鎵撳寘鎴恇yte[]褰㈠�?
	 * @param data
	 *            瑕佽В瀵嗙殑鏁版嵁
	 * @return 瑙ｅ瘑鏁版嵁
	 */
	public static byte[] decryptByRSA(byte[] privateKeyBytes, byte[] data) {
		try {
			PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(
					privateKeyBytes);
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			return null;
		}
	}

}
