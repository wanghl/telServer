/**
 * Studentfamily.java
 * com.zephyr.studentsafe.bo
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2010-9-7 		lenovo
 *
 * Copyright (c) 2010, TNT All Rights Reserved.
 */

package com.zephyr.telserver.bean;

/**
 * ClassName:Studentfamily Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author lenovo
 * @version
 * @since Ver 1.1
 * @Date 2010-9-7 下午07:26:40
 * 
 * @see
 */
public class Studentfamily {

	public String familyUID;
	public String studentUID;
	public String familyName;
	public String familySex;
	public String relationship;
	public String familyPhone;
	public Studentrfid studentRfid;
	
	public Studentrfid getStudentRfid() {
		return studentRfid;
	}

	public void setStudentRfid(Studentrfid studentRfid) {
		this.studentRfid = studentRfid;
	}

	public int isSendMessage;

	public String getFamilyUID() {
		return familyUID;
	}

	public void setFamilyUID(String familyUID) {
		this.familyUID = familyUID;
	}

	public String getStudentUID() {
		return studentUID;
	}

	public void setStudentUID(String studentUID) {
		this.studentUID = studentUID;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getFamilySex() {
		return familySex;
	}

	public void setFamilySex(String familySex) {
		this.familySex = familySex;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public int getIsSendMessage() {
		return isSendMessage;
	}

	public void setIsSendMessage(int isSendMessage) {
		this.isSendMessage = isSendMessage;
	}

}
