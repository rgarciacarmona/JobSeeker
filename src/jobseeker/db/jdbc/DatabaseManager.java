package jobseeker.db.jdbc;

import java.util.ArrayList;
import java.util.List;

import jobseeker.db.Job;
import jobseeker.db.Person;

public class DatabaseManager {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// New job
		Job garbageCollector = new Job();
		// New person
		Person manolo = new Person("Manolo", null);
		// Add the person to the job
		garbageCollector.addPerson(manolo);
		// Add the job to the person
		List<Job> jobs = new ArrayList<Job>();
		jobs.add(garbageCollector);
		manolo.setJobs(jobs);
		// Print everything
		System.out.println(manolo);
	}

}
