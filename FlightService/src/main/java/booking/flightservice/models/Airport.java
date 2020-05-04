package booking.flightservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="airports")
public class Airport {
	
	@Id
	private String id;
	private String airportID;
	private String name;
	private String city;
	private String country;
	private String code;
	
	public Airport(String airportID, String name, String city, String country, String code) {
	
		this.airportID = airportID;
		this.name = name;
		this.city = city;
		this.country = country;
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirportID() {
		return airportID;
	}

	public void setAirportID(String airportID) {
		this.airportID = airportID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}
