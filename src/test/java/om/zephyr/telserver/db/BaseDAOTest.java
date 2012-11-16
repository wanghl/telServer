package om.zephyr.telserver.db;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.Studentfamily;
import com.zephyr.telserver.bean.TelephoneStatusBean;
import com.zephyr.telserver.db.BaseDAO;

public class BaseDAOTest {
	
	@Test
	public void testSaveOrUpdate(){
		TelephoneStatusBean tb = new TelephoneStatusBean();
		tb.setConnectionStatus("1");
		tb.setHandleLine("1");
		tb.setPowerStatus("2");
		tb.setShellDoors("3");
		tb.setTelephoneId("w2w23e3rr4t5t55y6y");
		tb.setTelephoneNumber("13488669242");
		BaseDAO dao = new BaseDAO();
		dao.saveOrUpdate(tb);
		List list = dao.getObject(tb);
		assertTrue( ! list.isEmpty() );
		
	}
	@Test
	public void testGetStudentFamily(){
		BaseDAO dao = new BaseDAO() ;
		List list = dao.getStudentFamily("1113");
		assertTrue( list.get(0) instanceof Studentfamily ) ;
	}
	@Test
	public void tetGetStudentInfoByCardNo(){
		BaseDAO dao = new BaseDAO() ;
		List list = dao.getStudentInfoByCardNo("1113");
		assertTrue( ! list.isEmpty() );
	}
	@Test
	public void testGetObjectByTelephoneId(){
		BaseDAO dao = new BaseDAO();
		List list = dao.getObjectByTelephoneId("w2w23e3rr4t5t55y6y");
		assertTrue( list.get(0) instanceof TelephoneStatusBean ) ; 
	}
	@Test
	public void testUpdateCallTimes(){
		BaseDAO dao = new BaseDAO(); 
		CallHistoryBean callhistory = new CallHistoryBean();
		callhistory.setStudentCardId("1113");
		callhistory.setCallTimes("100");
		dao.updateCallTimes(callhistory);
	}

}
