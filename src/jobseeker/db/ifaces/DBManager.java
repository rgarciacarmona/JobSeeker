package jobseeker.db.ifaces;

import java.util.List;

import jobseeker.db.pojos.Job;
import jobseeker.db.pojos.Person;

public interface DBManager {
 
	// Connects with the database and, if needed, performs necessary setup
	public void connect();
	// Closes the connection with the database
	// To be called when the application ends
	public void disconnect();
	
	// Add a new person
	public void addPerson(Person p);
	// Get a particular person
	public Person getPerson(int id);
	// Search for a person by name
	// If name is empty or null, return all people
	public List<Person> searchPersonByName(String name);
	
	// Add a new job
	public void addJob(Job j);
	// Get a particular job
	public Job getJob(int id);
	// Search for a job by name
	// If name is empty or null, return all jobs
	public List<Job> searchJobsByName(String name);
	
	// Gets the people that work at a given job
	public List<Person> getPeopleOfJob(int jobId);
	
	// Assign a person to a job
	public void hire(Person p, Job j);
	// Remove a person from a job
	public void fire(int personId, int jobId);
	
}
