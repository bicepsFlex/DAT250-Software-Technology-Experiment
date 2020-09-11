package expass2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pincode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String pincode;
	private int count;
	
	public int getId() {
		return id;
	}
	
	public String getPincode() {
		return pincode;
	}
	
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Pincode [id=" + id + ", pincode=" + pincode + ", count=" + count + "]";
	}
	
}
