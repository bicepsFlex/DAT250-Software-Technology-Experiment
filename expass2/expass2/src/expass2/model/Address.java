package expass2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String Street;
	private int number;
	
	@ManyToMany(mappedBy = "addresses")
	private List<Person> people;
	
	public Address() {
		people = new ArrayList<Person>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getStreet() {
		return Street;
	}
	
	public void setStreet(String street) {
		Street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", Street=" + Street + ", number=" + number + ", people=" + people + "]";
	}
	
}
