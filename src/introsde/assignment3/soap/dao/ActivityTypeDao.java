package introsde.assignment3.soap.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import introsde.assignment3.soap.model.ActivityType;

public enum ActivityTypeDao {
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
	  
	  private Map<Long, ActivityType> contentProvider = new HashMap<Long, ActivityType>();
	  
	  public Map<Long, ActivityType> getDataProvider() {
	    return contentProvider;
	  }
	  
	   private ActivityTypeDao() {
	     if(emf!=null)
	       emf.close();
	     emf = Persistence.createEntityManagerFactory("introsde-2017-assignment-3-server");
	     /*Connect();
	     contentProvider.put(john.getPersonId(), john);*/
	   }
	   
	   public void DropDB() {
	     Path p = Paths.get("./people.db");
	     try {
	      Files.delete(p);
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	   }
	   
	   public ActivityType addActivityType(ActivityType at) {
	     EntityManager em = ActivityTypeDao.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();
	     tx.begin();
	     em.persist(at);
	     tx.commit();

	     /*em.clear();
	     em.close();*/
	     ActivityTypeDao.instance.closeConnections(em);
	     return at;
	   }
	   
	   public List<ActivityType> getAll(){
	     EntityManager em = ActivityTypeDao.instance.createEntityManager();
	     List<ActivityType> list = em.createNamedQuery("ActivityType.findAll", ActivityType.class).getResultList();
	     ActivityTypeDao.instance.closeConnections(em);
	     return list;
	   }
	   
	   public ActivityType getActivityTypeByName(String name) {
		   EntityManager em = ActivityTypeDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT at FROM ActivityType at WHERE at.name=:arg1");
		   query.setParameter("arg1", name);
		   ActivityType at = null;
		   try {
			   at = (ActivityType) query.getSingleResult();
		   }
		   catch(Exception e) {}
		   return at;
	   }
}