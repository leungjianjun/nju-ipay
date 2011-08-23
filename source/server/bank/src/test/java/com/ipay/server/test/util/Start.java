package com.ipay.server.test.util;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "";

	public static void main(String[] args) throws Exception {
		Server server = Start.buildNormalServer(PORT, CONTEXT);
		server.start();
		System.out.println("===================================");
		System.out.println("The website address is:");
		System.out.println("http://localhost:"+PORT+CONTEXT);
		System.out.println("===================================");
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
	
	/**
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	private static Server buildNormalServer(int port, String contextPath) {
		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		return server;
	}
}