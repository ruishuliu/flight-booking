package booking.flightservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "flight")
public class Flight {
	
	@Id
	private String id;
	private String flightID;
	private String departureTime;
	private String departureDate;
	private String arrivalTime;
	private String arrivalDate;
	private String routeID;
	
	public Flight(String flightID, String departureTime, String departureDate, String arrivalTime,
			String arrivalDate, String routeID) {

		this.flightID = flightID;
		this.departureTime = departureTime;
		this.departureDate = departureDate;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.routeID = routeID;
	}

	public Flight() {
		
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDat(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}
	
	

}
