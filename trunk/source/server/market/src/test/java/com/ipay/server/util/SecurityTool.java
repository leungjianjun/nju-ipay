package com.ipay.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import sun.security.x509.*;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.codec.Base64;

public class SecurityTool {
	
	public static void main(String args[]) throws Exception{
		generateX509Certificate();
		//RSAexample();
		KeyPair keyPair = RSATool.creatmyKey();
		System.out.println(keyPair.getPrivate().toString());
		System.out.println(keyPair.getPublic().toString());
		String plainText = "today is sunday";
		System.out.println(new String(plainText.getBytes()));
		for(byte b :plainText.getBytes()){
			System.out.println(b);
		}
		byte[] encodeText = RSATool.encryptByRSA(keyPair.getPublic().getEncoded(), plainText.getBytes());
		System.out.println(new String(encodeText));
		
		byte[] decodeText = RSATool.decryptByRSA(keyPair.getPrivate().getEncoded(), encodeText);
		System.out.println(new String(decodeText));
		
		byte[] encodeText2 = RSATool.encryptByRSA(keyPair.getPrivate().getEncoded(), plainText.getBytes());
		//System.out.println(new String(encodeText2));
		
		byte[] decodeText2 = RSATool.decryptByRSA(keyPair.getPublic().getEncoded(), encodeText2);
		System.out.println(new String(decodeText2));
	}
	
	public static void encodePassword(){
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		System.out.println(sha.encodePassword("admin", "admin"));
	}
	
	public static void RSAexample() throws NoSuchAlgorithmException{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		KeyPair keyPair = kpg.genKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		System.out.println(publicKey.toString());
		System.out.println("===================================");
		System.out.println(privateKey.toString());
	}
	
	public static void test() throws NoSuchAlgorithmException {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed("www.川江号子.cn".getBytes()); // 初始化随机产生器
		keygen.initialize(1024, secrand);
		KeyPair keys = keygen.genKeyPair();
		PublicKey pubkey = keys.getPublic();
		PrivateKey prikey = keys.getPrivate();
		byte[] pubKey = Base64.encode(pubkey.getEncoded());
		byte[] priKey = Base64.encode(prikey.getEncoded());
		System.out.println("pubKey = " + new String(pubKey));
		System.out.println("priKey = " + new String(priKey));
	}

	public static byte[] sign(byte[] priKeyText, String plainText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			// 用私钥对信息生成数字签名
			Signature signet = java.security.Signature
					.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			byte[] signed = Base64.encode(signet.sign());
			return signed;
		} catch (java.lang.Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}
		return null;
	}

	public static boolean verify(byte[] pubKeyText, String plainText,
			byte[] signText) {
		try {
			// 解密由base64编码的公钥,并构造X509EncodedKeySpec对象
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(
					Base64.decode(pubKeyText));
			// RSA对称加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			// 解密由base64编码的数字签名
			byte[] signed = Base64.decode(signText);
			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			// 验证签名是否正常
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Throwable e) {
			System.out.println("校验签名失败");
			e.printStackTrace();
			return false;
		}
	}
	
	public static void generateX509Certificate() throws Exception {
		String keystoreFile = "G:\\temp\\a\\keyStoreFile.bin";
		String caAlias = "caAlias";
		String certToSignAlias = "cert";
		String newAlias = "newAlias";

		char[] password = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		char[] caPassword = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		char[] certPassword = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h' };

		File file = new File(keystoreFile);
		file.createNewFile();
		FileInputStream input = new FileInputStream(file);
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(input, password);
		input.close();

		PrivateKey caPrivateKey = (PrivateKey) keyStore.getKey(caAlias,
				caPassword);
		java.security.cert.Certificate caCert = keyStore
				.getCertificate(caAlias);

		byte[] encoded = caCert.getEncoded();
		X509CertImpl caCertImpl = new X509CertImpl(encoded);

		X509CertInfo caCertInfo = (X509CertInfo) caCertImpl
				.get(X509CertImpl.NAME + "." + X509CertImpl.INFO);

		X500Name issuer = (X500Name) caCertInfo.get(X509CertInfo.SUBJECT + "."
				+ CertificateIssuerName.DN_NAME);

		java.security.cert.Certificate cert = keyStore
				.getCertificate(certToSignAlias);
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(certToSignAlias,
				certPassword);
		encoded = cert.getEncoded();
		X509CertImpl certImpl = new X509CertImpl(encoded);
		X509CertInfo certInfo = (X509CertInfo) certImpl.get(X509CertImpl.NAME
				+ "." + X509CertImpl.INFO);

		Date firstDate = new Date();
		Date lastDate = new Date(firstDate.getTime() + 365 * 24 * 60 * 60
				* 1000L);
		CertificateValidity interval = new CertificateValidity(firstDate,
				lastDate);

		certInfo.set(X509CertInfo.VALIDITY, interval);

		certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(
				(int) (firstDate.getTime() / 1000)));

		certInfo.set(
				X509CertInfo.ISSUER + "." + CertificateSubjectName.DN_NAME,
				issuer);

		AlgorithmId algorithm = new AlgorithmId(
				AlgorithmId.md5WithRSAEncryption_oid);
		certInfo.set(CertificateAlgorithmId.NAME + "."
				+ CertificateAlgorithmId.ALGORITHM, algorithm);
		X509CertImpl newCert = new X509CertImpl(certInfo);

		newCert.sign(caPrivateKey, "MD5WithRSA");

		keyStore.setKeyEntry(newAlias, privateKey, certPassword,
				new java.security.cert.Certificate[] { newCert });

		FileOutputStream output = new FileOutputStream(keystoreFile);
		keyStore.store(output, password);
		output.close();
	}
}
