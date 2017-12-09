package introsde.assignment3.soap.endpoint;

import introsde.assignment3.soap.dao.DatabaseDao;
import introsde.assignment3.soap.ws.PeopleImpl;

import javax.xml.ws.Endpoint;

public class PeoplePublisher {
    public static String SERVER_URL = "http://localhost";
    public static String PORT = "6902";
    public static String BASE_URL = "/ws/people";

    public static String getEndpointURL() {
        //return SERVER_URL+":"+PORT+BASE_URL;
        return SERVER_URL+":"+System.getenv("PORT")+BASE_URL;
    }

    public static void main(String[] args) {
    	DatabaseDao.instance.DropDB();
    	DatabaseDao.instance.Init();
        String endpointUrl = getEndpointURL();
        System.out.println("Starting People Service...");
        System.out.println("--> Published at = "+endpointUrl);
        Endpoint.publish(endpointUrl, new PeopleImpl());
    }
}