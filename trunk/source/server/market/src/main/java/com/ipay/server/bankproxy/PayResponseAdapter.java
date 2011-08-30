package com.ipay.server.bankproxy;

public class PayResponseAdapter {
	
	private String source;
	
	private byte[] sign;
	
	private String cardnum;
	
	public PayResponseAdapter(PayResponse payResponse,String cardnum){
		this.source = payResponse.getSource();
		this.sign = payResponse.getSign();
		this.cardnum = cardnum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public byte[] getSign() {
		return sign;
	}

	public void setSign(byte[] sign) {
		this.sign = sign;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

}
