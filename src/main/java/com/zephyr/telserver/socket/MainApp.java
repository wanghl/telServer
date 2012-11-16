package com.zephyr.telserver.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.zephyr.telserver.db.HibernateUtil;

/**
 * 平安通项目——学生信息机服务端程序V1.0
 * 公话通过GPRS连接到服务端,本质是一个基于TCP/IP的socket通信过程。
 * 所有公话连接到服务端后，都是长连接。
 * 公话加电开机后的通讯流程：
 * a 发送认证报文  c->s 
 * b 回复认证报文  s->c 
 * c 下发公话状态查询报文 s->c
 * d 发送公话状态报文 c->s
 * 经上述流程后，公话开始正常工作。
 * 详细通讯协议参考《鑫诺爱贝通公话软件接口规范(北京V1.01).pdf》
 * 
 * 王宏亮  2012/10/11
 *
 */
public class MainApp {
	
	private static final Logger log = Logger.getLogger(MainApp.class) ;
	public static void main(String[] argvs){
		log.info("连接数据库");
		HibernateUtil.getSession();
		MainApp mainApp = new MainApp();
		mainApp.doListen() ;
	}
	
	
	private void doListen(){
		
		ServerSocket socket = null ;
		Socket client =  null ;
		
		try {
			log.info("主程序启动");
			socket = new ServerSocket(4700);
			log.info("绑定端口　:" + 4700);
			while(true){
				client = socket.accept();
				Thread runThread = new Thread(new ProcessThread(client));
				runThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		}
		
	}

}
