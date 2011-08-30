package com.ipay.client.security;

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
	
	/**
	 * 使用aes-256 加密私钥
	 * @param privateKey
	 * 			私钥
	 * @param rawPass
	 * 			原始密码
	 * @param salt
	 * 			盐值
	 * @return
	 * 			加密的私钥
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
	 * 使用私钥加密数据 用一个已打包成byte[]形式的私钥加密数据，即数字签名
	 * 
	 * @param privateKeyBytes
	 *            打包成byte[]的私钥
	 * @param message
	 *            要签名的数据
	 * @return 签名 byte[]
	 */
	public static byte[] sign(byte[] privateKeyBytes, String message) {
		byte[] source = Digest.MdigestSHA(message);//生成信息摘要
		return sign(privateKeyBytes,source);
	}
	
	public static byte[] sign(byte[] privateKeyBytes, byte[] source) {
		try {
			PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(privKey);
			sig.update(source);
			return sig.sign();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 对签名进行验证
	 * 
	 * @param publicKeyBytes
	 * 			公钥
	 * @param source
	 * 			原数据
	 * @param sign
	 * 			签名数据
	 * @return
	 * 			验证结果
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
	
	public static boolean verify(byte[] publicKeyBytes, String message, byte[] sign){
		byte[] source = Digest.MdigestSHA(message);//生成信息摘要
		return verify(publicKeyBytes,source,sign);
	}
	
	/**
	 * 使用RSA公钥加密数据
	 * 
	 * @param publicKeyBytes
	 *            打包的byte[]形式公钥
	 * @param data
	 *            要加密的数据
	 * @return 加密数据
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
	
	public static byte[] encryptByRSA(byte[] publicKeyBytes, String message) {
		return encryptByRSA(publicKeyBytes,message.getBytes());
	}

	/**
	 * 用RSA私钥解密
	 * 
	 * @param privateKeyBytes
	 *            私钥打包成byte[]形式
	 * @param data
	 *            要解密的数据
	 * @return 解密数据
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
	
	public static String decryptByRSAInString(byte[] privateKeyBytes, byte[] data) {
		return new String(decryptByRSA(privateKeyBytes,data));
	}

}
