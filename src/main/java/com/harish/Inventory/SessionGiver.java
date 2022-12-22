package com.harish.Inventory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionGiver 
{
	public static SessionFactory sessionFactory;
	
	private SessionGiver() {
		// TODO Auto-generated constructor stub
	}
	public static SessionFactory getSessionFactory()
	{
		if(sessionFactory==null)
		{
		 sessionFactory=new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}

}
