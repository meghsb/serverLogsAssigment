package com.cs.example.model;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonProperty;


public class FileContent {
	@JsonProperty("id")
	private String id;

	@JsonProperty("state")
	private State state;

	@JsonProperty("timestamp")
	private long timeStamp;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("host")
	private String host;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public FileContent(String id, State state, long timeStamp, String type, String host) {
		super();
		this.id = id;
		this.state = state;
		this.timeStamp = timeStamp;
		this.type = type;
		this.host = host;
	}

	public FileContent() {
		super();
	}
	
	@Override
	public String toString() {
		return "FileContent [id=" + id + ", state=" + state + ", timeStamp=" + timeStamp + ", type=" + type + ", host="
				+ host + "]";
	}

	
}
