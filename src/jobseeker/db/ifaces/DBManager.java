package jobseeker.db.ifaces;

import java.util.List;

import jobseeker.db.pojos.Job;
import jobseeker.db.pojos.Person;

public interface DBManager {
 
	public void connect();
	public void disconnect();
	
	public void addPerson(Person p);
	public Person getPerson(int id);
	public List<Person> searchPersonByName(String name);
	
	public void addJob(Job j);
	public Job getJob(int id);
	public List<Job> searchJobsByName(String name);
	
	public void hire(Person p, Job j);
	
}
