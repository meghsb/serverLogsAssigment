package com.cs.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Alert")
public class Alert {
	
	@Id
	@JsonProperty("id")
	private String id;

	@Column
	@JsonProperty("timestamp")
	private long timeStamp;

	@Column  
	@JsonProperty("type")
	private String type;
	
	@Column  
	@JsonProperty("host")
	private String host;
	
	@Column  
	@JsonProperty("alert")
	private Boolean alert;

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

	public Boolean getAlert() {
		return alert;
	}

	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

	public Alert() {
		super();
	}

	@Override
	public String toString() {
		return "Alert [id=" + id + ", timeStamp=" + timeStamp + ", type=" + type + ", host=" + host + ", alert=" + alert
				+ "]";
	}

	public Alert(FileContent content, long timeStamp) {
		super();
		this.id = content.getId();
		this.timeStamp = timeStamp;
		this.type = content.getType();
		this.host = content.getHost();
		this.alert = Boolean.FALSE;
	}

}
