

package com.zephyr.telserver.bean;

import java.sql.Date;
import java.util.Set;

/**
 * ClassName:Studentrfid Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author lenovo
 * @version
 * @since Ver 1.1
 * @Date 2010-9-7 下午07:28:48
 * 
 * @see
 */
public class Studentrfid {

	private String studentUID;
	private String studentID;
	private String studentName;
	

	private String rfidCardID;
	private String studentSex;
	private Date studentBirthday;
	private String classUID;
	private String teacherUID ;
	//剩余通话时长
	private String remainingTime; ;
	//低频卡号
	private String lowCardNumber ;
	public String getStudentUID() {
		return studentUID;
	}
	public void setStudentUID(String studentUID) {
		this.studentUID = studentUID;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRfidCardID() {
		return rfidCardID;
	}
	public void setRfidCardID(String rfidCardID) {
		this.rfidCardID = rfidCardID;
	}
	public String getStudentSex() {
		return studentSex;
	}
	public void setStudentSex(String studentSex) {
		this.studentSex = studentSex;
	}
	public Date getStudentBirthday() {
		return studentBirthday;
	}
	public void setStudentBirthday(Date studentBirthday) {
		this.studentBirthday = studentBirthday;
	}
	public String getClassUID() {
		return classUID;
	}
	public void setClassUID(String classUID) {
		this.classUID = classUID;
	}
	public String getTeacherUID() {
		return teacherUID;
	}
	public void setTeacherUID(String teacherUID) {
		this.teacherUID = teacherUID;
	}
	public String getRemainingTime() {
		return remainingTime;
	}
	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}
	public String getLowCardNumber() {
		return lowCardNumber;
	}
	public void setLowCardNumber(String lowCardNumber) {
		this.lowCardNumber = lowCardNumber;
	}
	
}