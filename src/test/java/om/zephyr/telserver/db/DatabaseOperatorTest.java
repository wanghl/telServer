package om.zephyr.telserver.db;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.MessageBean;
import com.zephyr.telserver.db.DatabaseOperator;

public class DatabaseOperatorTest {
	
	public void testSaveCallDetails(){
		
		CallHistoryBean bean = new CallHistoryBean();
		MessageBean msg = new MessageBean(); 
		DatabaseOperator db = new DatabaseOperator();
		bean.setCalledDuration("200");
		bean.setCallNumber("13488669242");
		bean.setStart_time("1933");
		bean.setFamilyName("ÊÇÄãµù"); 
		bean.setFamilyRelations("°Ö°Ö");
		bean.setStudentCardId("1113");
		bean.setStudentName("Äã´óÒ¯");
		bean.setCalledDuration("100");
		List list = new ArrayList();
		list.add(bean);
		msg.setCallDetails(list) ;
		
		db.saveCallDetails(msg);
		
		
	}
	
	public void testQueryStudentInfo(){
		CallHistoryBean bean = new CallHistoryBean();
		DatabaseOperator db = new DatabaseOperator();
		bean.setCalledDuration("200");
		bean.setCallNumber("13488669242");
		bean.setStart_time("1933");
		bean.setStudentCardId("1113");
		bean.setStudentName("Äã´óÒ¯");
		bean.setCalledDuration("100");
		db.queryStudentInfo(bean);
		
		assertTrue( bean.getFamilyName() != null );
		
	}
	public void testGetStudentFamily(){
		MessageBean message = new MessageBean();
		DatabaseOperator db = new DatabaseOperator();
		
		String result = db.queryStudentFamily(message);
		
		assertTrue ( "error".equals(result) );
		
	}
	
	//@Test
	public void testUpdateConnectStatus(){
		DatabaseOperator db = new DatabaseOperator();
		MessageBean message = new MessageBean();
		message.setTelephoneId("w2w23e3rr4t5t55y6y");
		db.updateConnectStatus(message);
		
		
	}
	@Test
	public void testUpdatetelephoneStatus(){
		
		DatabaseOperator db = new DatabaseOperator();
		MessageBean message = new MessageBean();
		
		message.setShellDoors("2");
		message.setTelephoneId("w2w23e3rr4t5t55y6y");
		
		db.updateTelephoneStatue(message);
		
	}

}
