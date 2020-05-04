package booking.bookingservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class UserItem {
	
	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String dob;
	
	
//	public UserItem(String id, String firstName, String lastName, String email, String username, String dob) {
//
//		this.id = id;
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.email = email;
//		this.username = username;
//		this.dob = dob;
//	}
	
	public UserItem( String firstName, String lastName, String email, String username, String dob) {


		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.dob = dob;
	}	

	public UserItem(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;

	}
	
	public UserItem() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}
	
	
}
