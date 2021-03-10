package jobseeker.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jobseeker.db.ifaces.DBManager;
import jobseeker.db.pojos.Job;
import jobseeker.db.pojos.Person;

public class JDBCManager implements DBManager {

	private Connection c;

	@Override
	public void connect() {
		try {
			// Open database connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/jobSeeker.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			this.createTables();
		} catch (SQLException sqlE) {
			System.out.println("There was a database exception.");
			sqlE.printStackTrace();
		} catch (Exception e) {
			System.out.println("There was a general exception.");
			e.printStackTrace();
		}
	}

	private void createTables() {
		// TODO If the tables are not created already, create them
		Statement stmt1;
		try {
			// Create the jobs table
			stmt1 = c.createStatement();

			String sql1 = "CREATE TABLE jobs " + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL, " + " description  TEXT	 NOT NULL, " + " salary  REAL	 NOT NULL, "
					+ " startDate  DATE	 NOT NULL, " + " endDate  DATE	 NOT NULL)";
			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE people " + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL)";
			stmt1.executeUpdate(sql1);

			sql1 = "CREATE TABLE jobs_people " + "(job_id INTEGER REFERENCES jobs(id), "
					+ "person_id INTEGER REFERENCES people(id), " + "PRIMARY KEY (job_id, person_id))";
			
			stmt1.executeUpdate(sql1);
			stmt1.close();
		} catch (SQLException e) {
			if (!e.getMessage().contains("already exists")) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("There was a problem while closing the database connection.");
			e.printStackTrace();
		}
	}

	@Override
	public void addPerson(Person p) {
		try {
			Statement stmt = c.createStatement();
			String sql = "INSERT INTO people (name) VALUES ('" + p.getName() + "')";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Person getPerson(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> searchPersonByName(String name) {
		List<Person> people = new ArrayList<Person>();
		try {
			Statement stmt = c.createStatement();
			String sql = "SELECT * FROM people WHERE name LIKE '%" + name + "%'";
			// SELECT * FROM people WHERE name LIKE '%lo%'
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) { // true: there is another result and I have advanced to it
								// false: there are no more results
				int id = rs.getInt("id");
				String personName = rs.getString("name");
				Person person = new Person(id, personName);
				people.add(person);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return people;
	}

	@Override
	public void addJob(Job j) {
		// TODO Auto-generated method stub

	}

	@Override
	public Job getJob(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Job> searchJobsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void hire(Person p, Job j) {
		// TODO Auto-generated method stub

	}

}
