package introsde.assignment3.soap.ws;
import introsde.assignment3.soap.model.Activity;
import introsde.assignment3.soap.model.Person;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) //optional
public interface People {
//1
    @WebMethod(operationName="readPersonList")
    @WebResult(name="people") 
    public List<Person> readPersonList();
//2
    @WebMethod(operationName="readPerson")
    @WebResult(name="person") 
    public Person readPerson(@WebParam(name="personId") long id);
//3
    @WebMethod(operationName="updatePerson")
    @WebResult(name="person") 
    public Person updatePerson(@WebParam(name="personIn") Person person);
//4
    @WebMethod(operationName="createPerson")
    @WebResult(name="person") 
    public Person createPerson(@WebParam(name="personIn") Person person);
//5
    @WebMethod(operationName="deletePerson")
    @WebResult(name="personId") 
    public void deletePerson(@WebParam(name="personId") long id);
//6
    @WebMethod(operationName="readPersonPreferences")
    @WebResult(name="preferences") 
    public List<Activity> readPersonPreferences(@WebParam(name="personId") long id, @WebParam(name="type") String activity_type);
 //7
    @WebMethod(operationName="readPreferences")
    @WebResult(name="preferences") 
    public List<Activity> readPreferences();
//8
    @WebMethod(operationName="readPersonPreferences2")
    @WebResult(name="preference") 
    public Activity readPersonPreferences2(@WebParam(name="personId") long id, @WebParam(name="activityId") long a_id);
//9 return void???
    @WebMethod(operationName="savePersonPreferences")
    @WebResult(name="preferences") 
    public void savePersonPreferences(@WebParam(name="personId") long id, @WebParam(name="activity") Activity activity);
//10
    @WebMethod(operationName="updatePersonPreferences")
    @WebResult(name="preference") 
    public Activity updatePersonPreferences(@WebParam(name="personId") long id, @WebParam(name="activity") Activity activity);
//11
    @WebMethod(operationName="evaluatePersonPreferences")
    @WebResult(name="preference") 
    public Activity evaluatePersonPreferences(@WebParam(name="personId") long id, @WebParam(name="activity") Activity activity, @WebParam(name="value") int value);
//12
    @WebMethod(operationName="getBestPersonPreferences")
    @WebResult(name="preferences") 
    public List<Activity> getBestPersonPreferences(@WebParam(name="personId") long id);

}