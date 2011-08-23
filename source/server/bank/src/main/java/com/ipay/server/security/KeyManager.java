package com.ipay.server.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.ipay.server.security.codec.Hex;
import com.ipay.server.security.encrypt.BytesEncryptor;
import com.ipay.server.security.encrypt.Encryptors;
import com.ipay.server.security.util.Digest;

public class KeyManager {
	
	/**
	 * 生成RSA密码对,也就是公钥和私钥
	 * 
	 * @return
	 * 		密码对
	 */
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
		try {
			PKCS8EncodedKeySpec priv_spec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory mykeyFactory = KeyFactory.getInstance("RSA");
			PrivateKey privKey = mykeyFactory.generatePrivate(priv_spec);
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initSign(privKey);
			byte[] source = Digest.MdigestSHA(message);//生成信息摘要
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

}
