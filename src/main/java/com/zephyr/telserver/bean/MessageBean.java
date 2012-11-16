package com.zephyr.telserver.bean;

import java.util.List;

/**
 * ����ʵ����
 * @author wanghongliang
 *
 */
public class MessageBean {
	
	private int messageLength ;
	private int validFlag  ;
	private String operationNumber ; 
	private String serialNumber ;
	private String telephoneId ;
	private String telephoneNumber ;
	
	private String studentCardId ;
	private List<Studentfamily> familyList  ;
	
	private List<CallHistoryBean> callDetails ;
	
	private String callTimes ; //��������
	
	private String powerStatus ; //�е�״̬  1��� 2��ع��� 3��ص�ѹ��
	private String handleLine ; //�ֱ���״̬ 1���� 2����״̬
	private String shellDoors ;//�����  1�ر� 2��
	private String versionInfo ; //�汾��Ϣ
	

	
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public List<CallHistoryBean> getCallDetails() {
		return callDetails;
	}
	public void setCallDetails(List<CallHistoryBean> callDetails) {
		this.callDetails = callDetails;
	}
	
	public int getMessageLength() {
		return messageLength;
	}
	public void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}
	public int getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(int validFlag) {
		this.validFlag = validFlag;
	}
	public String getOperationNumber() {
		return operationNumber;
	}
	public void setOperationNumber(String operationNumber) {
		this.operationNumber = operationNumber;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTelephoneId() {
		return telephoneId;
	}
	public void setTelephoneId(String telephoneId) {
		this.telephoneId = telephoneId;
	}
	public String getStudentCardId() {
		return studentCardId;
	}
	public void setStudentCardId(String studentCardId) {
		this.studentCardId = studentCardId;
	}
	public List getFamilyList() {
		return familyList;
	}
	public void setFamilyList(List familyList) {
		this.familyList = familyList;
	}
	public String getCallTimes() {
		return callTimes;
	}
	public void setCallTimes(String callTimes) {
		this.callTimes = callTimes;
	}
	
	public String getPowerStatus() {
		return powerStatus;
	}
	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}
	public String getHandleLine() {
		return handleLine;
	}
	public void setHandleLine(String handleLine) {
		this.handleLine = handleLine;
	}
	public String getShellDoors() {
		return shellDoors;
	}
	public void setShellDoors(String shellDoors) {
		this.shellDoors = shellDoors;
	}
	public String getVersionInfo() {
		return versionInfo;
	}
	public void setVersionInfo(String versionInfo) {
		this.versionInfo = versionInfo;
	}

}
