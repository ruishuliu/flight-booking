
public class Resident {
	private String name;
	private String birthday;
	
	public Resident(String name, String date) {
		
		this.name = name;
		this.birthday = date;
	}
	public Resident(Resident person) {
		this.name = person.getName();
		this.birthday = person.getBirthday();
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getBirthday() {
		return this.birthday;
	}
	public void setBirthday(String date) {
		this.birthday= date;
	}
	public String toString() {
		return "Name: " + this.name + ", Birthday: " + this.birthday;
	}
}
