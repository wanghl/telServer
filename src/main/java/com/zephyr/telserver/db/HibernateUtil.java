package com.zephyr.telserver.db;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private final static Logger log = Logger.getLogger(HibernateUtil.class);
	private static SessionFactory sessionFactory ;
	
	private HibernateUtil(){
		
	}
	
	static {
		try{
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
		}catch (Exception e){
			log.error(e);
		}
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	
	

}

