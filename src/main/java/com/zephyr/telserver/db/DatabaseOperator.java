package com.zephyr.telserver.db;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.ClassInfo;
import com.zephyr.telserver.bean.MessageBean;
import com.zephyr.telserver.bean.Studentfamily;
import com.zephyr.telserver.bean.Studentrfid;
import com.zephyr.telserver.bean.TelephoneStatusBean;


public class DatabaseOperator {

	BaseDAO dao = new BaseDAO();

	public void saveCallDetails(MessageBean message) {

		for (CallHistoryBean callHistory : message.getCallDetails()) {
			dao.saveOrUpdate(callHistory);
			dao.updateCallTimes(callHistory);

		}

	}

	/**
	 * query student info by card
	 * 
	 * @param callHistory
	 */
	public void queryStudentInfo(CallHistoryBean callHistory) {
		List list = dao.getStudentInfoByCardNo(callHistory.getStudentCardId());
		if ( ! list.isEmpty() ) {
			Object[] objArray = (Object[]) list.get(0);
			for (Object object : objArray) {
				if (object instanceof Studentrfid) {
					callHistory.setStudentName(((Studentrfid) object)
							.getStudentName());

				}
				if (object instanceof Studentfamily) {
					callHistory.setFamilyRelations((((Studentfamily) object).getRelationship()));
					callHistory.setFamilyName(((Studentfamily) object).getFamilyName());
				}
				if (object instanceof ClassInfo) {

					callHistory.setStudentClass(((ClassInfo) object).getClassName());
				}
			}
		}
	}

	/**
	 * query student family info .
	 * 
	 * @param message
	 * @return
	 */
	public String queryStudentFamily(MessageBean message) {
		List list = dao.getStudentFamily(message.getStudentCardId());
		if (list.isEmpty()) {
			return "error";
		} else {

			message.setFamilyList(list);
		}
		return "success";

	}

	public void updateConnectStatus(MessageBean message) {
		List list = dao.getObjectByTelephoneId(message.getTelephoneId());
		TelephoneStatusBean tb = new TelephoneStatusBean();
		if (!list.isEmpty()) {
			tb = (TelephoneStatusBean) list.get(0);
			  Calendar cal=Calendar.getInstance();
			tb.setConnectionTime(cal);
			dao.saveOrUpdate(tb);
		}
	}

	public void updateTelephoneStatue(MessageBean message) {
		List list = dao.getObjectByTelephoneId(message.getTelephoneId());
		TelephoneStatusBean tb = new TelephoneStatusBean();
		if (!list.isEmpty()) {
			tb = (TelephoneStatusBean) list.get(0);
			tb.setPowerStatus(message.getPowerStatus());
			tb.setHandleLine(message.getHandleLine());
			tb.setShellDoors(message.getShellDoors());
			tb.setVersionInfo(message.getVersionInfo());
			dao.saveOrUpdate(tb);
		} else {
			tb.setPowerStatus(message.getPowerStatus());
			tb.setHandleLine(message.getHandleLine());
			tb.setShellDoors(message.getShellDoors());
			tb.setVersionInfo(message.getVersionInfo());
			tb.setTelephoneId(message.getTelephoneId());
			dao.saveOrUpdate(tb);

		}
	}

}
