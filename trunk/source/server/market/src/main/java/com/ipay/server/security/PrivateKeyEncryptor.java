package com.ipay.server.security;

import com.ipay.server.security.tool.codec.Hex;
import com.ipay.server.security.tool.encrypt.BytesEncryptor;
import com.ipay.server.security.tool.encrypt.Encryptors;

/**
 * 商场注册者需要从银行获取加密的私钥,而这个私钥会保存到商场服务器.
 * 为避免由于服务器遭到攻击等各种可能泄露数据的情况,编写私钥的加密
 * 方案,目前暂时采取AES加密.
 * 
 * @author ljj
 *
 */
public class PrivateKeyEncryptor {
	
	private static BytesEncryptor be = Encryptors.standard("ipay", String.valueOf(Hex.encode("popcorn".getBytes())));
	
	public static byte[] encrypt(byte[] rawPrivateKey){
		return be.encrypt(rawPrivateKey);
	}
	
	public static byte[] decrypt(byte[] encryptPrivateKey){
		return be.decrypt(encryptPrivateKey);
	}

}
