package jobseeker.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
		// If the tables are not created already, create them
		Statement stmt1;
		try {
			stmt1 = c.createStatement();
			// Create table jobs
			String sql1 = "CREATE TABLE jobs " + "(id       INTEGER  PRIMARY KEY AUTOINCREMENT,"
					+ " name     TEXT     NOT NULL, " + " description  TEXT	 NOT NULL, " + " salary  REAL	 NOT NULL, "
					+ " startDate  DATE	 NOT NULL, " + " endDate  DATE	 NOT NULL)";
			stmt1.executeUpdate(sql1);
			// Create table people
			sql1 = "CREATE TABLE people " + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " + "name TEXT NOT NULL)";
			stmt1.executeUpdate(sql1);
			// Create table jobs_people
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
			// Close database connection
			c.close();
		} catch (SQLException e) {
			System.out.println("There was a problem while closing the database connection.");
			e.printStackTrace();
		}
	}

	@Override
	public void addPerson(Person p) {
		try {
			// Id is chosen by the database
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
		try {
			String sql = "SELECT * FROM people WHERE id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				String personName = rs.getString("name");
				return new Person(id, personName);
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<Person> searchPersonByName(String name) {
		List<Person> people = new ArrayList<Person>();
		try {
			String sql = "SELECT * FROM people WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
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
		try {
			String sql = "INSERT INTO jobs (name, description, salary, startDate, endDate) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setString(1, j.getName());
			prep.setString(2, j.getDescription());
			prep.setFloat(3, j.getSalary());
			prep.setDate(4, j.getStartDate());
			prep.setDate(5, j.getEndDate());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Job getJob(int id) {
		try {
			String sql = "SELECT * FROM jobs WHERE id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				return new Job(id, rs.getString("name"), rs.getString("description"), rs.getFloat("salary"),
						rs.getDate("startDate"), rs.getDate("endDate"));
			}
			rs.close();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Job> searchJobsByName(String name) {
		List<Job> jobs = new ArrayList<Job>();
		try {
			String sql = "SELECT * FROM jobs WHERE name LIKE ?";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, "%" + name + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// Create a job with everything except the list of people that work here
				Job job = new Job(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
						rs.getFloat("salary"), rs.getDate("startDate"), rs.getDate("endDate"));
				// Adds the people that work in the job to thejob
				job.setPeople(this.getPeopleOfJob(job.getId()));
				// Adds the job to the list that will be returned
				jobs.add(job);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobs;
	}

	@Override
	public void hire(Person p, Job j) {
		try {
			String sql = "INSERT INTO jobs_people (job_id, person_id) VALUES (?,?)";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, j.getId());
			prep.setInt(2, p.getId());
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fire(int personId, int jobId) {
		try {
			String sql = "DELETE FROM jobs_people WHERE job_id = ? AND person_id = ?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, jobId);
			prep.setInt(2, personId);
			prep.executeUpdate();
			prep.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Person> getPeopleOfJob(int jobId) {
		List<Person> people = new ArrayList<Person>();
		try {
			String sql = "SELECT * FROM jobs_people WHERE job_id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, jobId);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				int personId = rs.getInt("person_id");
				people.add(this.getPerson(personId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return people;
	}

}
