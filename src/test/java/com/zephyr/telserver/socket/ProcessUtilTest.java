package com.zephyr.telserver.socket;

import static org.junit.Assert.*;

import org.junit.Test;

import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.MessageBean;

public class ProcessUtilTest {
	
	@Test
	public void testParseMessgae10(){
		MessageBean msg = new MessageBean();
		// 10 
		String message = "10999915010519840701871X        " ;
		msg = ProcessUtil.parseMessage(message);
		assertEquals( msg.getOperationNumber() , "10" );
		assertEquals( msg.getSerialNumber() , "9999" );
		
	}
	
	@Test
	public void testParsemesssage01(){
		MessageBean msg = new MessageBean();
		//1
		String message = "01999913488669242    15010519840701871X                  1113              20121013173028";
		msg = ProcessUtil.parseMessage(message);
		
		assertEquals( msg.getStudentCardId() , "1113" );
		
		
	}
	
	@Test
	public void testParsemesssage82(){
		MessageBean msg = new MessageBean();
		//1
		String message = "82999915010519840701871XVER1.00 2004/05/10123                                                   VER1.00 2005/10/10";
		msg = ProcessUtil.parseMessage(message);
		
		assertEquals( msg.getTelephoneId(), "15010519840701871X" );
		assertEquals( msg.getVersionInfo(), "VER1.00 2004/05/10" );
		assertEquals( msg.getPowerStatus(), "1" );
		assertEquals( msg.getHandleLine(), "2" ) ;
		assertEquals( msg.getShellDoors(), "3" );
		
	}
	
	@Test
	public void testParsemesssage03(){
		MessageBean msg = new MessageBean();
		//1
		String message = "03999913488669242    15010519840701871X1113                                022012101318240000010013488669242             2012101318240000010013488669242             ";
		msg = ProcessUtil.parseMessage(message);
		
		assertEquals( msg.getTelephoneId(), "15010519840701871X" );
		assertEquals( msg.getTelephoneNumber(), "13488669242" );
		assertEquals( msg.getStudentCardId(), "1113" );
		
		CallHistoryBean c1 = msg.getCallDetails().get(0);
		assertEquals( c1.getStart_time() , "20121013182400") ;
		assertEquals( c1.getCalledDuration() ,"000100" );
		assertEquals( c1.getCallNumber() ,"13488669242");
		
		CallHistoryBean c2 = msg.getCallDetails().get(0);
		assertEquals( c2.getStart_time() , "20121013182400") ;
		assertEquals( c2.getCalledDuration() ,"000100" );
		assertEquals( c2.getCallNumber() ,"13488669242");
		
	}

}
