package jobseeker.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import jobseeker.db.ifaces.DBManager;
import jobseeker.db.jdbc.JDBCManager;
import jobseeker.db.pojos.Person;

public class Menu {

	private static DBManager dbman = new JDBCManager();
	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws Exception {
		dbman.connect();
		do {
		System.out.println("Choose an option:");
		System.out.println("1. Add a person");
		System.out.println("2. Search people");
		System.out.println("0. Exit");
		int choice = Integer.parseInt(reader.readLine());
		switch (choice) {
		case 1:
			addPerson();
			break;
		case 2:
			searchPeople();
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

}
