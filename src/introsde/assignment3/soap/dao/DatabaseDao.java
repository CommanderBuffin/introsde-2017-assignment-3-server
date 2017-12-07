package introsde.assignment3.soap.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import introsde.assignment3.soap.model.Activity;
import introsde.assignment3.soap.model.ActivityType;
import introsde.assignment3.soap.model.Person;

public enum DatabaseDao {
	instance;
	
	private EntityManagerFactory emf;
	  
	public EntityManager createEntityManager() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;    
    }

	    public void closeConnections(EntityManager em) {
	        em.close();
	    }

	    public EntityTransaction getTransaction(EntityManager em) {
	        return em.getTransaction();
	    }

	    public EntityManagerFactory getEntityManagerFactory() {
	        return emf;
	    }  
	  
	   private DatabaseDao() {
	     if(emf!=null)
	       emf.close();
	     emf = Persistence.createEntityManagerFactory("introsde-2017-assignment-3-server");
	   }
	   
	   public void DropDB() {
		   EntityManager em = DatabaseDao.instance.createEntityManager();
		   em.getTransaction().begin();
		   Query q = em.createQuery("DELETE FROM Person p");
		   Query q2 = em.createQuery("DELETE FROM Activity a");
		   Query q3 = em.createQuery("DELETE FROM ActivityType at");
		   q.executeUpdate();
		   q3.executeUpdate();
		   q2.executeUpdate();
		   em.getTransaction().commit();
		   em.close();
	    }
	   
	   private void add(String ...values) {
		   Person p = new Person();
		   p.setId(new Long(values[0]));
		   p.setFirstname(values[1]);
		   p.setLastname(values[2]);
		   p.setBirthdate(values[3]);
		   List<Activity> aa = new ArrayList<Activity>();
		   p.setActivities(aa);
		   
		   //
		   PersonDao.instance.addPerson(p);
		   
		   ActivityType at = new ActivityType();
		   at.setName(values[4]);
		   
		   //
		   ActivityTypeDao.instance.addActivityType(at);
		   
		   Activity a = new Activity();
		   a.setId(new Long(values[5]));
		   a.setName(values[6]);
		   a.setDescription(values[7]);
		   a.setPlace(values[8]);
		   a.setStartdate(values[9]);
		   a.setRating(new Integer(values[10]));
		   a.setType(at);
		   
		   a.setPerson(p);   
		   
		   Activity ra = ActivityDao.instance.addActivity(a);
		   p.addActivity(ra);
		   PersonDao.instance.updatePerson(p);
	   }
	   
	   public void Init() {
		   add("1","Mattia","Buffa","24-02-1992","Game","1","Play PUBG","Play PUBG Squadd FPP","Home","15-11-2017 21:30:00.0","4");
		   add("2","Linda Marilena","Bertolli","28-01-1994","School","2","Thesis","Explain thesis","Unitn","20-11-2017 09:00:00.0","3");
		   add("3","Denis","Gallo","08-04-1993","Media","3","Watch Stream","Watch Buffin4576' stream","Home","15-11-2017 21:30:00.0","2");
		   add("4","Davide","Buffa","24-02-1992","Social","4","Facebook","Post things on Facebook","Home","18-11-2017 21:30:00.0","1");
		   add("5","Luca","Buffa","24-02-1992","Sport","5","Ruuning","400m run","Stadium","22-11-2017 11:00:00.0","1"); 
	   }
}