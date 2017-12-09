package introsde.assignment3.soap.ws;
import introsde.assignment3.soap.dao.ActivityDao;
import introsde.assignment3.soap.dao.ActivityTypeDao;
import introsde.assignment3.soap.dao.DatabaseDao;
import introsde.assignment3.soap.dao.PersonDao;
import introsde.assignment3.soap.model.Activity;
import introsde.assignment3.soap.model.ActivityType;
import introsde.assignment3.soap.model.Person;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

//Service Implementation

@WebService(endpointInterface = "introsde.assignment3.soap.ws.People",
    serviceName="PeopleService")
public class PeopleImpl implements People {
	
	//init
	@Override
	public void init() {
		DatabaseDao.instance.DropDB();
    	DatabaseDao.instance.Init();
	}
	
	//1
    @Override
    public Person readPerson(long id) {
        System.out.println("---> Reading Person by id = "+id);
        Person p = PersonDao.instance.getPersonById(id);
        if (p!=null) {
            System.out.println("---> Found Person by id = "+id+" => "+p.getFirstname());
        } else {
            System.out.println("---> Didn't find any Person with  id = "+id);
        }
        return p;
    }

    //2
    @Override
    public List<Person> readPersonList() {
        return PersonDao.instance.getAll();
    }


    //3
    @Override
    public Person updatePerson(Person person) {
    	Person p = PersonDao.instance.getPersonById(person.getId());
    	if(person.getFirstname()!=""&&person.getFirstname()!=null)
    		p.setFirstname(person.getFirstname());
    	if(person.getLastname()!=""&&person.getLastname()!=null)
    		p.setLastname(person.getLastname());
    	if(person.getBirthdate()!=""&&person.getBirthdate()!=null)
    		p.setBirthdate(person.getBirthdate());
        p = PersonDao.instance.updatePerson(p);
        return p;
    }
    
    //4
    @Override
    public Person createPerson(Person person) {
    	person.setId(PersonDao.instance.getNewId());

    	List<Activity> aa = new ArrayList<Activity>();
        for(Activity a : person.getActivities()) {
        	ActivityType at = ActivityTypeDao.instance.getActivityTypeByName(a.getType().getName());
        	if(at==null) {
        		at = ActivityTypeDao.instance.addActivityType(a.getType());
        	}
        	a.setId(ActivityDao.instance.getNewId());
        	a = ActivityDao.instance.addActivity(a);
        	aa.add(a);
        }
        person.setActivities(new ArrayList<Activity>());
        Person p = PersonDao.instance.addPerson(person);
        p.setActivities(aa);
        p = PersonDao.instance.updatePerson(p);
        return p;
    }

    //5
    @Override
    public void deletePerson(long id) {
        Person p = PersonDao.instance.getPersonById(id);
        if (p!=null) {
            PersonDao.instance.removePerson(p.getId());
            return;
        } else {
            return;
        }
    }

    //6
	@Override
	public List<Activity> readPersonPreferences(long id, String activity_type) {
		// TODO Auto-generated method stub
		List<Activity> activities = ActivityDao.instance.getPersonActivitiesByType(id, activity_type);
		return activities;
	}

	//7
	@Override
	public List<Activity> readPreferences() {
		// TODO Auto-generated method stub
		List<Activity> activities = ActivityDao.instance.getAll();
		return activities;
	}

	//8
	@Override
	public Activity readPersonPreferences2(long id, long a_id) {
		// TODO Auto-generated method stub
		Person p = PersonDao.instance.getPersonById(id);
		Activity a = ActivityDao.instance.getActivityById(a_id);
		/*if(p.getActivities().contains(a))
			return a;*/
		for(Activity a2 : p.getActivities())
		{
			if(a2.getId()==a.getId())
				return a;
		}
		return null;
	}

	//9
	@Override
	public void savePersonPreferences(long id, Activity activity) {
		// TODO Auto-generated method stub
		activity.setId(ActivityDao.instance.getNewId());
		
		if(activity.getType().getName()!=""&&activity.getType().getName()!=null) {
			ActivityType at = ActivityTypeDao.instance.getActivityTypeByName(activity.getType().getName());
			if(at!=null) {
				activity.setType(at);
			}
			else{
				ActivityType new_at = new ActivityType();
				new_at.setName(activity.getType().getName());
				new_at = ActivityTypeDao.instance.addActivityType(new_at);
				activity.setType(new_at);
			}
		}
		
		Activity a = ActivityDao.instance.addActivity(activity);
		Person p = PersonDao.instance.getPersonById(id);
		p.addActivity(a);
		PersonDao.instance.updatePerson(p);
	}

	//10
	@Override
	public Activity updatePersonPreferences(long id, Activity activity) {
		// TODO Auto-generated method stub
		Activity a = ActivityDao.instance.getActivityById(activity.getId());
		if(a==null)
			return null;
		
		Person p = PersonDao.instance.getPersonById(id);
		boolean found=false;
		for(Activity a2 : p.getActivities()) {
			if(a2.getId()==a.getId())
				found=true;
		}
		if(!found)
			return null;
		
		if(activity.getName()!=""&&activity.getName()!=null)
			a.setName(activity.getName());
		if(activity.getDescription()!=""&&activity.getDescription()!=null)
			a.setDescription(activity.getDescription());
		if(activity.getPlace()!=""&&activity.getPlace()!=null)
			a.setPlace(activity.getPlace());
		if(activity.getStartdate()!=""&&activity.getStartdate()!=null)
			a.setStartdate(activity.getStartdate());
		if(activity.getRating()>-1)
			a.setRating(activity.getRating());
		if(activity.getType().getName()!=""&&activity.getType().getName()!=null) {
			ActivityType at = ActivityTypeDao.instance.getActivityTypeByName(activity.getType().getName());
			if(at!=null) {
				a.setType(at);
			}
			else{
				ActivityType new_at = new ActivityType();
				new_at.setName(activity.getType().getName());
				new_at = ActivityTypeDao.instance.addActivityType(new_at);
				a.setType(new_at);
			}
		}
		a = ActivityDao.instance.updateActivity(a);
		return a;
	}

	//11
	@Override
	public Activity evaluatePersonPreferences(long id, Activity activity, int value) {
		// TODO Auto-generated method stub
		Person p = PersonDao.instance.getPersonById(id);
		Activity a = ActivityDao.instance.getActivityById(activity.getId());
		for(Activity a2 : p.getActivities()) {
			if(a2.getId()==a.getId()) {
				a.setRating(value);
				a = ActivityDao.instance.updateActivity(a);
				return a;
			}
		}
		return null;
	}

	//12
	@Override
	public List<Activity> getBestPersonPreferences(long id) {
		// TODO Auto-generated method stub
		List<Activity> aa = PersonDao.instance.getPersonById(id).getActivities();
		int max=0;
		for(Activity a : aa) {
			if(a.getRating()>max)
				max = a.getRating();
		}
		List<Activity> r_aa = new ArrayList<Activity>();
		for(Activity a : aa) {
			if(a.getRating()>=max) {
				r_aa.add(a);
			}
		}
		return r_aa;
	}
}