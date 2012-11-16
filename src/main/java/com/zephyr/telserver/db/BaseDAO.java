package com.zephyr.telserver.db;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.zephyr.telserver.bean.CallHistoryBean;
import com.zephyr.telserver.bean.Studentfamily;
import com.zephyr.telserver.bean.TelephoneStatusBean;




public class BaseDAO {

	private final Logger log = Logger.getLogger(BaseDAO.class);

	public void saveOrUpdate(Object obj) {

		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.saveOrUpdate(obj);
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}

		}
	}
	
	public List getObject(Object obj){
		Session session = null;
		List list = null ;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			list = session.createCriteria(obj.getClass()).list();
		}catch (Exception e){
			log.error(e);
		}finally {
			if ( session != null ){
				session.close(); 
			}
		}
		return list ;
	}
	
	public List getStudentFamily(String cardNo){
		Session session = null; 
		List list = null ;
		try {
			session = HibernateUtil.getSession() ;
			session.beginTransaction();
			list = session.createSQLQuery("select f.* FROM studentfamily  f , studentrfid  s WHERE s.StudentUID = f.StudentUID AND s.LowCardNumber=" + cardNo)
					.addEntity(Studentfamily.class).list();
		}catch (Exception e){
			log.error(e);
		}finally {
			if ( session != null ){
				session.close() ;
			}
		}
		return list ;
	}

	public List getStudentInfoByCardNo(String cardNo) {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			 list = session
					.createQuery(
							"FROM Studentrfid s ,ClassInfo c ,Studentfamily f WHERE s.classUID = c.classUID AND s.studentUID = f.studentUID AND s.lowCardNumber = " + cardNo)
					.list();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list ;
	}
	
	public List getObjectByTelephoneId(String telephoneid){
		Session session = null ;
		List list = null ;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			list = session.createSQLQuery("select * from telephonestatus as t where t.telephoneId='" + telephoneid + "'").addEntity(TelephoneStatusBean.class).list();
			session.getTransaction().commit();
			
		}catch (Exception e){
			log.error(e);
		}finally {
			if ( session != null ){
				session .close() ;
			}
		}
		return list ;
	}

	public void updateCallTimes(CallHistoryBean callHistory) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			session.beginTransaction();
			session.createSQLQuery("update studentrfid set remaining_time = remaining_time -" + callHistory.getCalledDuration() + " where lowcardnumber='" + callHistory.getStudentCardId() + "'").executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
