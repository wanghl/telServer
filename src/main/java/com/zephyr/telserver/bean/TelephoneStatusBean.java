package com.zephyr.telserver.bean;

import java.util.Calendar;
import java.util.Date;


public class TelephoneStatusBean extends MessageBean{
	
	private String school ;
	private Calendar connectionTime ;
	private String connectionStatus ;
	private String objuid ;

	
	public String getObjuid() {
		return objuid;
	}
	public void setObjuid(String objuid) {
		this.objuid = objuid;
	}
	
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}

	public Calendar getConnectionTime() {
		return connectionTime;
	}
	public void setConnectionTime(Calendar connectionTime) {
		this.connectionTime = connectionTime;
	}
	public String getConnectionStatus() {
		return connectionStatus;
	}
	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

}
