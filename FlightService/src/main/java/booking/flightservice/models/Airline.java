package booking.flightservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "airlines")
public class Airline {

	@Id
	private String id;
	private String airlineID;
	private String name;
	private String code;
	private String country;
	
	public Airline(String airlineID, String name, String code, String country) {
		
		this.airlineID = airlineID;
		this.name = name;
		this.code = code;
		this.country = country;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAirlineID() {
		return airlineID;
	}
	public void setAirlineID(String airlineID) {
		this.airlineID = airlineID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
