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

import introsde.assignment3.soap.model.Person;

public enum PersonDao {
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
	  
	   private PersonDao() {
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
	   
	   public Person addPerson(Person p) {
	     EntityManager em = PersonDao.instance.createEntityManager();
	     EntityTransaction tx = em.getTransaction();
	     tx.begin();
	     em.persist(p);
	     tx.commit();
	     /*em.clear();
	     em.close();*/
	     PersonDao.instance.closeConnections(em);
	     return p;
	   }
	   
	   public List<Person> getAll(){
	     EntityManager em = PersonDao.instance.createEntityManager();
	     List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
	     PersonDao.instance.closeConnections(em);
	     return list;
	   }
	   
	   public Person getPersonById(Long id) {
		   Person p = null;
		   EntityManager em = PersonDao.instance.createEntityManager();
		   Query query = em.createQuery("SELECT p FROM Person p WHERE p.id=:arg1");
		   query.setParameter("arg1", id);
		   try {
			   p = (Person) query.getSingleResult();
		   }catch(Exception e) {p=null;}
		   PersonDao.instance.closeConnections(em);
		   return p;
	   }
	   
	   public Long getNewId() {
		   EntityManager em = createEntityManager();
		   return (Long) em.createQuery("select max(p.id) from Person p").getSingleResult()+1L;
	   }
	   
	   public Person updatePerson(Person p) {
		   EntityManager em = PersonDao.instance.createEntityManager();
		     EntityTransaction tx = em.getTransaction();
		     tx.begin();
		     Person pm = em.merge(p);
		     tx.commit();
		     PersonDao.instance.closeConnections(em);
		     return pm;
	   }
	   
	   public void removePerson(long id) {
		   EntityManager em = PersonDao.instance.createEntityManager();
		   /*Query query = em.createQuery("DELETE FROM Person p WHERE p.id=:arg1");
		   query.setParameter("arg1", id);
		    return query.executeUpdate();*/
		   Person p = em.find(Person.class, id);
		   EntityTransaction tx = em.getTransaction();
		   tx.begin();
		   em.remove(p);
		   tx.commit();
		   PersonDao.instance.closeConnections(em);   
	   }
}