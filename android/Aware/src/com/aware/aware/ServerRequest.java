package com.aware.aware;

public class ServerRequest {
	String verb;
	String data;
	String method;
	
	public ServerRequest(String verb, String data, String post) {
		this.verb = verb;
		this.data = data;
		this.method = method;
	}

}