package com.zephyr.telserver.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.media.Log;
import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.MessageBean;
import com.zephyr.telserver.bean.Studentfamily;
import com.zephyr.telserver.db.DatabaseOperator;

/**
 * @author wanghongliang 读、写报、解析文帮助类
 */
public class ProcessUtil {

	private static final Logger log = Logger.getLogger(ProcessUtil.class);
	private static DatabaseOperator dboperator = new DatabaseOperator();

	/**
	 * read message from socket .
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String readMessage(BufferedReader reader) throws IOException {
		String message = null;
		char[] length = new char[4];
		reader.read(length);
		int bodayLength = Integer.parseInt(new String(length));
		// 读报文体
		char[] boday = new char[bodayLength - 4];
		reader.read(boday);
		message = new String(boday);
		if (log.isDebugEnabled()) {
			log.debug("收到客户端报文，长度：" + bodayLength);
			log.debug("报文体: " + message);
		}

		return message;
	}

	/**
	 * write message to client .
	 * 
	 * @param writer
	 * @param messageContent
	 */
	public static void writeMessage(OutputStream writer, String messageContent) {
 
		try {
			writer.write(messageContent.getBytes("GBK"));
			writer.flush();
		} catch (IOException e) {
			log.error(e);
		}

	}

	/**
	 * pase message .
	 * 
	 * @param message
	 * @return
	 */
	public static MessageBean parseMessage(String message) {
		MessageBean bean = new MessageBean();
		try {
			bean.setOperationNumber(message.substring(0, 2));
			bean.setSerialNumber(message.substring(2, 6));
			bean.setMessageLength(message.length() + 4);
			// telephone authentictation message
			if (bean.getOperationNumber().equals("10")) {
				ProcessThread._TELEPHONE_IDENTIFY = message.substring(6, 25);
			}
			// student login message;
			if (bean.getOperationNumber().equals("01")) {
				
				bean.setStudentCardId(message.substring(39, 57).trim());
			}
			// telephone status message ;
			if (bean.getOperationNumber().equals("82")) {
				bean.setTelephoneId(message.substring(6, 24));
				bean.setVersionInfo(message.substring(24, 42));
				bean.setPowerStatus(message.substring(42, 43));
				bean.setHandleLine(message.substring(43, 44));
				bean.setShellDoors(message.substring(44, 45));

			}
			// call history
			if (bean.getOperationNumber().equals("03")) {
				List<CallHistoryBean> list = new ArrayList<CallHistoryBean>();
				bean.setTelephoneNumber(message.substring(6, 20).trim());
				bean.setTelephoneId(message.substring(21, 39).trim());
				bean.setStudentCardId(message.substring(39, 57).trim());
				int call_times = Integer.parseInt(message.substring(75, 77)
						.trim());
				bean.setCallTimes(Integer.toString(call_times));
				message = message.substring(77, message.length()).trim();
				String ps = null;
				if ( call_times == 1 ){
					CallHistoryBean history = new CallHistoryBean();
					history.setStart_time(message.substring(0, 14).trim());
					history.setCalledDuration(message.substring(14, 20).trim());
					history.setCallNumber(message.substring(20, message.length()));
					history.setStudentCardId(bean.getStudentCardId().trim());
					// query student infomation
					dboperator.queryStudentInfo(history);
					list.add(history);
				}else {
					
					for (int i = 0; i < call_times; i++) {
						int length = message.length() / 2;
						ps = message.substring(0, length).trim();
						// start parse message
						CallHistoryBean history = new CallHistoryBean();
						history.setStart_time(ps.substring(0, 14).trim());
						history.setCalledDuration(ps.substring(14, 20).trim());
						history.setCallNumber(ps.substring(20, ps.length()));
						history.setStudentCardId(bean.getStudentCardId().trim());
						// query student infomation
						dboperator.queryStudentInfo(history);
						list.add(history);
					}
				}

				bean.setCallDetails(list);
			}
			// connection status ;
			if (bean.getOperationNumber().equals("05")) {
				bean.setTelephoneId(ProcessThread._TELEPHONE_IDENTIFY);
			}
		} catch (Exception e) {
			log.error("/********************报文解析错误:****************");
			log.error("操作码：" + bean.getOperationNumber()) ;
			log.error("报文内容：" + message) ;
			log.error("异常 ：" + e);
			log.error("/*********************************************");
			return null;
		}
		return bean;
	}

	/**
	 * make a authentication answer message .
	 * 
	 * @param bean
	 * @return
	 */
	public static String getAuthenticationAnswerMessage(MessageBean bean) {

		String message = bean.getOperationNumber() + bean.getSerialNumber()
				+ "1";
		return countMessageLength(message) + message;

	}

	public static String getValidationFailedAnswerMessage(MessageBean bean) {
		String message = bean.getOperationNumber() + bean.getSerialNumber()
				+ "0";
		return countMessageLength(message) + message;
	}

	/**
	 * make a telephone status query message .
	 * 
	 * @param bean
	 * @return
	 */
	public static String getTelephoneStatusQueryAnswerMessage(MessageBean bean) {
		String message = "82" + bean.getSerialNumber() + getCurrentDateAll();
		return countMessageLength(message) + message;
	}

	/**
	 * @param bean
	 * @return
	 */
	public static String getHeartBeatsAnswerMessage(MessageBean bean) {
		String message = bean.getOperationNumber() + bean.getSerialNumber();
		return countMessageLength(message) + message;
	}

	/**
	 * make a studen login answer message
	 * 
	 * @param bean
	 * @return
	 */
	public static String getStudentLoginAnswerMessage(MessageBean bean) {
		String result = dboperator.queryStudentFamily(bean);
		if (result.equals("error")) {
			return "error";
		}
		StringBuffer numberBuffer = new StringBuffer();
		StringBuffer familyrpBuffer = new StringBuffer();

		for (Iterator it = bean.getFamilyList().iterator(); it.hasNext();) {
			Studentfamily family = (Studentfamily) it.next();
			numberBuffer.append(padding(family.getFamilyPhone(), 14));
			familyrpBuffer.append(padding(family.getRelationship(),4));
		}
		String family_num = Integer.toString(bean.getFamilyList().size());
		if (family_num.length() < 2) {
			family_num = "0" + family_num;
		}
		String message = bean.getOperationNumber() + bean.getSerialNumber()
				+ "1" + family_num + numberBuffer.toString()
				+ familyrpBuffer.toString() + "0000" + getCurrentDateAll() + "999999";

		return getWordCount(message) + message;
		//
	}

	private static String padding(String str, int length) {
		for (int i = str.length(); i <= length; i++) {
			str += " ";
		}
		return str;
	}

	/**
	 * make a student sign answer message
	 * 
	 * @param bean
	 * @return
	 */
	public static String getStudentSignAnswerMessage(MessageBean bean) {
		String message = bean.getOperationNumber() + bean.getSerialNumber()
				+ "1";
		return countMessageLength(message) + message;
	}

	/**
	 * count message length .
	 * 
	 * @param message
	 * @return
	 */
	private static String countMessageLength(String message) {
		String length = Integer.toString(message.length() + 4);
		while (length.length() < 4) {
			length = "0" + length;
		}
		return length;
	}

	/**
	 * 计算中英文混合字符串的长度
	 * @param s
	 * @return
	 */
	public static  String getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		String len = Integer.toString(length + 4 );
		while ( len.length() < 4) {
			len = "0" + len;
		}
		return len;

	}

	public static String getCurrentDateAll() {
		Locale loc = new Locale("zh", "CN");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", loc);

		return sdf.format(new Date());
	}

	public static void main(String[] argvs) {
		String s = "2012101517331300082413910718125             ";
		System.out.println(s.substring(0,14));
		System.out.println(s.substring(14,20));
		System.out.println(s.substring(20,31));
	}
	
	
}
