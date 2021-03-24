package jobseeker.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import jobseeker.db.ifaces.DBManager;
import jobseeker.db.jdbc.JDBCManager;
import jobseeker.db.pojos.Job;
import jobseeker.db.pojos.Person;

public class Menu {

	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	// Used for parsing dates
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		
	public static void main(String[] args) throws Exception {
		dbman.connect();
		do {
		System.out.println("Choose an option:");
		System.out.println("1. Add a person");
		System.out.println("2. Search people");
		System.out.println("3. Add a job");
		System.out.println("4. Search jobs");
		System.out.println("5. Hire person");
		System.out.println("6. Fire person");
		System.out.println("0. Exit");
		int choice = Integer.parseInt(reader.readLine());
		switch (choice) {
		case 1:
			addPerson();
			break;
		case 2:
			searchPeople();
			break;
		case 3:
			addJob();
			break;
		case 4:
			searchJob();
			break;
		case 5:
			hirePerson();
			break;
		case 6:
			firePerson();
			break;
		case 0:
			dbman.disconnect();
			System.exit(0);
			break;
		default:
			break;
		}
		} while (true);
		
	}
	
	private static void addPerson() throws Exception {
		System.out.println("Please, input the person info:");
		System.out.print("Name: ");
		String name = reader.readLine();
		dbman.addPerson(new Person(name));
	}
	
	private static void searchPeople() throws Exception {
		System.out.println("Please, input the search term:");
		System.out.print("Name contains: ");
		String name = reader.readLine();
		List<Person> people = dbman.searchPersonByName(name);
		if (people.isEmpty()) {
			System.out.println("No results.");
		}
		else {
			System.out.println(people);
		}
	}
	
	private static void addJob() throws Exception {
		System.out.println("Please, input the job info:");
		System.out.print("Name: ");
		String name = reader.readLine();
		System.out.print("Description: ");
		String description = reader.readLine();
		System.out.print("Salary: ");
		float salary = Float.parseFloat(reader.readLine());
		System.out.print("Start Date (yyyy-MM-dd): ");
		LocalDate startDate = LocalDate.parse(reader.readLine(), formatter);
		System.out.print("End Date (yyyy-MM-dd): ");
		LocalDate endDate = LocalDate.parse(reader.readLine(), formatter);
		Job job = new Job(name, description, salary, Date.valueOf(startDate), Date.valueOf(endDate));
		dbman.addJob(job);
	}
	
	private static void searchJob() throws Exception {
		System.out.println("Please, input the search term:");
		System.out.print("Name contains: ");
		String name = reader.readLine();
		List<Job> jobs = dbman.searchJobsByName(name);
		if (jobs.isEmpty()) {
			System.out.println("No results.");
		}
		else {
			System.out.println(jobs);
		}
	}
	
	private static void hirePerson() throws Exception {
		System.out.println("Choose the person first:");
		searchPeople();
		System.out.println("Type the chosen person's id:");
		int personId = Integer.parseInt(reader.readLine());
		System.out.println("Now choose the job:");
		searchJob();
		System.out.println("Type the chosen job's id:");
		int jobId = Integer.parseInt(reader.readLine());
		dbman.hire(dbman.getPerson(personId), dbman.getJob(jobId));
	}
	
	private static void firePerson() throws Exception {
		System.out.println("Choose the person first:");
		searchPeople();
		System.out.println("Type the chosen person's id:");
		int personId = Integer.parseInt(reader.readLine());
		System.out.println("Now choose the job:");
		searchJob();
		System.out.println("Type the chosen job's id:");
		int jobId = Integer.parseInt(reader.readLine());
		dbman.fire(personId, jobId);
	}

}
