package com.zephyr.telserver.bean;

public class CallHistoryBean extends MessageBean{
	
	private String objuid ;
	
	private String start_time ; //开始呼叫时间
	private String calledDuration ; //通话时长
	private String callNumber ; //呼叫号码
	private String studentName ; //学生姓名
	private String studentClass; //班级
	private String familyRelations ; //家庭关系
	private String familyName ; //家长姓名
	
	
	public String getObjuid() {
		return objuid;
	}
	public void setObjuid(String objuid) {
		this.objuid = objuid;
	}

	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String startTime) {
		start_time = startTime;
	}
	public String getCalledDuration() {
		return calledDuration;
	}
	public void setCalledDuration(String calledDuration) {
		this.calledDuration = calledDuration;
	}
	public String getCallNumber() {
		return callNumber;
	}
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	public String getFamilyRelations() {
		return familyRelations;
	}
	public void setFamilyRelations(String familyRelations) {
		this.familyRelations = familyRelations;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

}
