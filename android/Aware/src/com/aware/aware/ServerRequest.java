package com.aware.aware;

public class ServerRequest {
	String verb;
	String data;
	String method;
	
	public ServerRequest(String verb) {
		this(verb, null);
	}
	
	public ServerRequest(String verb, String data) {
		this.verb = verb;
		this.data = data;
		this.method = data == null ? "GET" : "POST";
	}

}