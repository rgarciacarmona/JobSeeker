package jobseeker.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -64567603420449597L;
	
	private Integer id;
	private String name;
	private List<Job> jobs;
	
	
	
	public Person() {
		super();
		this.jobs = new ArrayList<Job>();
	}
	
	public Person(String name) {
		super();
		this.name = name;
		this.jobs = new ArrayList<Job>();
	}

	public Person(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.jobs = new ArrayList<Job>();
	}






	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
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
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	
	
	
}
