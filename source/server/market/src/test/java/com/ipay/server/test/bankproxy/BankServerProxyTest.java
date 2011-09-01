package com.ipay.server.test.bankproxy;

import org.junit.Test;

import com.ipay.server.bankproxy.BankProxyServerException;
import com.ipay.server.bankproxy.BankServerProxy;

import junit.framework.Assert;
import junit.framework.TestCase;

public class BankServerProxyTest extends TestCase{
	
	@Test
	public void testGetEncryptPrivakeKey() throws BankProxyServerException{
		byte[] encryptPrivatekey = BankServerProxy.getEncryptPrivakeKey("837941h43bh32h");
		Assert.assertEquals(656, encryptPrivatekey.length);
	}

}
