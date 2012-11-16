package com.zephyr.telserver.socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.zephyr.telserver.bean.MessageBean;
import com.zephyr.telserver.bean.Studentfamily;
import com.zephyr.telserver.db.DatabaseOperator;

public class ProcessThread implements Runnable{
	
	private final Logger log = Logger.getLogger(ProcessThread.class);
	
	public static String _TELEPHONE_IDENTIFY = "";
	
	private Socket client = null ;
	private ProcessThread(){
		
	}
	
	public ProcessThread(Socket client){
		this.client = client ;
	}
	public void run() {
		BufferedReader reader = null ;
		OutputStream writer = null ;
		//应答报文
		String answerMessage = null ;
		//接收的报文
		String messageBody = null ;
		//
		MessageBean messageBean = null ;
		DatabaseOperator dboperator = new DatabaseOperator() ;
		try {
			client.setKeepAlive(true);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
			writer = new BufferedOutputStream(client.getOutputStream()) ;
			
			while (true){
				// read message .
				messageBody = ProcessUtil.readMessage(reader);
				//pase message .
				messageBean = ProcessUtil.parseMessage(messageBody);
				if (messageBean != null ){
					switch (Integer.parseInt(messageBean.getOperationNumber())){
					
					case 10: //telephone authentication 
						log.info("收到公话请求认证报文,操作码：" + messageBean.getOperationNumber());
						log.info("整体报文长度:" + messageBean.getMessageLength()) ;
						log.info("报文内容： " + messageBody);
						log.info("请求认证公话ID：" + messageBean.getTelephoneId());
						//TODO: 需要考虑，每部公话是否注册后才能使用。如果是，则在这里要验证
						//公话发来的ID在系统中有没有注册。不需要的话，目前直接返回认证成功报文
						answerMessage = ProcessUtil.getAuthenticationAnswerMessage(messageBean);
						//answer messge
						log.info("回复认证报文：" + answerMessage);
						ProcessUtil.writeMessage(writer, answerMessage);
						// telephone status check 
						answerMessage = ProcessUtil.getTelephoneStatusQueryAnswerMessage(messageBean);
						log.info("下发公话状态查询报文:" + answerMessage ) ;
						ProcessUtil.writeMessage(writer, answerMessage) ;
						log.info("************************ 公话认证处理流程结束    *******************");
						break ;
					case 1: //student card login message .
						log.info("收到学生卡登录报文，操作码：" + messageBean.getOperationNumber()) ;
						log.info("整体报文长度:" + messageBean.getMessageLength()) ;
						log.info("报文内容： " + messageBody);
						log.info("刷卡学生卡号:" + messageBean.getStudentCardId());
						log.info("查询卡号对应的亲情号码...");
						// answer message 
						answerMessage = ProcessUtil.getStudentLoginAnswerMessage(messageBean);
						if ( answerMessage.equals("error")){
							// student not found 
							log.info("卡号为[ " + messageBean.getStudentCardId() + " ]的学生不存在，返回无效卡报文" );
							answerMessage = ProcessUtil.getValidationFailedAnswerMessage(messageBean);
							ProcessUtil.writeMessage(writer, answerMessage) ;
							log.info("************************ 学生卡登录处理流程结束    *******************");
							break ;
						}
						log.info("亲情号码个数：" + messageBean.getFamilyList().size()) ;
						if ( log.isDebugEnabled() ){
							Studentfamily fm ;
							if ( ! messageBean.getFamilyList().isEmpty() ){
								for ( Iterator it = messageBean.getFamilyList().iterator(); it.hasNext(); ){
									fm = (Studentfamily) it.next();
									log.debug("家长姓名:" + fm.getFamilyName());
									log.debug("家庭关系：" + fm.getRelationship());
									log.debug("电话：" + fm.getFamilyPhone());
								}
							}
							
						}
						log.info("回复学生卡登录报文：" + answerMessage);
						ProcessUtil.writeMessage(writer, answerMessage) ;
						log.info("************************ 学生卡登录处理流程结束    *******************");
						break ;
					case 3 : // call details . save to log table .
						log.info("收到详细话单报文，操作码：" + messageBean.getOperationNumber()) ;
						log.info("整体报文长度:" + messageBean.getMessageLength()) ;
						log.info("报文内容： " + messageBody);
						//save call details and update student call-times; 
						log.info("公话ID:" + messageBean.getTelephoneId());
						log.info("公话号码:" + messageBean.getTelephoneNumber());
						log.info("学生卡号：" + messageBean.getStudentCardId()) ;
						log.info("话单个数" + messageBean.getCallTimes()) ;
						log.info("被叫号码：" + (messageBean.getCallDetails().get(0)).getCallNumber()) ;
						log.info("通话时长：" + (messageBean.getCallDetails().get(0)).getCalledDuration()) ;
						dboperator.saveCallDetails(messageBean);
						answerMessage = ProcessUtil.getAuthenticationAnswerMessage(messageBean);
						ProcessUtil.writeMessage(writer, answerMessage);
						log.info("************************ 通话话单处理流程结束    *******************");
						break ;
					case 4 : // 卡签到，忽略此报文，直接回复成功
						log.info("收到卡签到报文。忽略，直接回复成功") ;
						answerMessage = ProcessUtil.getStudentSignAnswerMessage(messageBean);
						ProcessUtil.writeMessage(writer, answerMessage) ;
                        break ;
					case 82 : // telephone status message . parse this message ,then update database  ;
						log.info("收到话机状态报文" + messageBean.getOperationNumber());
						log.info("整体报文长度:" + messageBean.getMessageLength()) ;
						log.info("报文内容： " + messageBody);
						log.info("公话ID:" + messageBean.getTelephoneId());
						log.info("市电状态:" + messageBean.getPowerStatus());
						log.info("外壳门状态：" + messageBean.getShellDoors());
						log.info("手柄线状态: " + messageBean.getHandleLine());
						log.info("软件版本：" +messageBean.getVersionInfo());
						dboperator.updateTelephoneStatue(messageBean);
						log.info("************************ 公话状态更新处理流程结束    *******************");
						break ;
					case 5 : // connection status ;
						log.info("收到心跳报文，公话ID：" + _TELEPHONE_IDENTIFY);
						dboperator.updateConnectStatus(messageBean);
						answerMessage = ProcessUtil.getHeartBeatsAnswerMessage(messageBean);
						ProcessUtil.writeMessage(writer, answerMessage);
						break;
					default : // unknow messgae
						log.info("Receive default Message:" + messageBody) ;
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
			// then client connection reset ,close this thread .
			return ;
		}
		
	}

}
