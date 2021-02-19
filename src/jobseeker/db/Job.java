package jobseeker.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Job implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1077803972062837560L;
	private int b;
	private Integer id;
	private String name;
	private String description;
	private float salary;
	private Date startDate;
	private Date endDate;
	private List<Person> people;
	
	public Job() {
		this.people = new ArrayList<Person>();
	}
	
	public Job(String name, String description, float salary, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.description = description;
		this.salary = salary;
		this.startDate = startDate;
		this.endDate = endDate;
		this.people = new ArrayList<Person>();
	}
	
	
	
	public Job(Integer id, String name, String description, float salary, Date startDate, Date endDate,
			List<Person> people) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.salary = salary;
		this.startDate = startDate;
		this.endDate = endDate;
		this.people = people;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + ", description=" + description + ", salary=" + salary
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", people=" + people + "]";
	}

	public void addPerson(Person person) {
		if (!people.contains(person)) {
			people.add(person);
		}
	}
	
	public void removePerson(Person person) {
		if (people.contains(person)) {
			people.remove(person);
		}
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
	
}
