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

import introsde.assignment3.soap.model.Activity;
import introsde.assignment3.soap.model.ActivityType;
import introsde.assignment3.soap.model.Person;

public enum ActivityDao {
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
	  
	  private Map<Long, Activity> contentProvider = new HashMap<Long, Activity>();
	  
	  public Map<Long, Activity> getDataProvider() {
	    return contentProvider;
	  }
	  
	   private ActivityDao() {
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
	   
	   public Activity addActivity(Activity a) {
	     EntityManager em = ActivityDao.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();
	     tx.begin();
	     em.persist(a);
	     //em.refresh(a.getPerson());
	     //em.clear();
	     tx.commit();

	     /*em.clear();
	     em.close();*/
	     ActivityDao.instance.closeConnections(em);
	     return a;
	   }
	   
	   public Long getNewId() {
		   EntityManager em = createEntityManager();
		   return (Long) em.createQuery("select max(a.id) from Activity a").getSingleResult()+1L;
	   }
	   
	   public List<Activity> getAll(){
	     EntityManager em = ActivityDao.instance.createEntityManager();
	     List<Activity> list = em.createNamedQuery("Activity.findAll", Activity.class).getResultList();
	     ActivityDao.instance.closeConnections(em);
	     return list;
	   }
	   
	   public Activity getActivityById(Long id) {
		   EntityManager em = ActivityDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT a FROM Activity a WHERE a.id=:arg1");
		   query.setParameter("arg1", id);
		   Activity a = null;
		   try {
			   a = (Activity) query.getSingleResult();
		   }
		   catch(Exception e) {}
		   return a;
	   }
	   
	   public List<Activity> getPersonActivitiesByType(Long personId, String type){
		   EntityManager em = ActivityDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT a FROM Activity a WHERE a.person.id=:arg1 AND a.type.name=:arg2");
		   query.setParameter("arg1", personId);
		   query.setParameter("arg2", type);
		   List<Activity> activities = query.getResultList();
		   return activities;
	   }
	   
	   public Activity getPersonActivitiyWithTypeById(Long personId, String type, Long id){
		   EntityManager em = ActivityDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT distinct(a) FROM Activity a JOIN a.person p JOIN a.type t WHERE p.id=:arg1 AND t.name=:arg2 AND a.id=:arg3");
		   query.setParameter("arg1", personId);
		   query.setParameter("arg2", type);
		   query.setParameter("arg3", id);
		   Activity activity = (Activity) query.getSingleResult();
		   return activity;
	   }
	   
	   public Activity updateActivity(Activity a) {
		   EntityManager em = ActivityDao.instance.createEntityManager();
		     EntityTransaction tx = em.getTransaction();
		     tx.begin();
		     Activity am = em.merge(a);
		     tx.commit();
		     ActivityDao.instance.closeConnections(em);
		     return am;
	   }
}