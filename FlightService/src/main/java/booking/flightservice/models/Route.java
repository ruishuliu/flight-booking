package booking.flightservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="routes")
public class Route {

	@Id
	private String id;
	private String routeID;
	private String airlineID;
	private String departureAirportID;
	private String arrivalAirportID;
	
	public Route(String routeID, String airlineID, String departureAirportID, String arrivalAirportID) {

		this.routeID = routeID;
		this.airlineID = airlineID;
		this.departureAirportID = departureAirportID;
		this.arrivalAirportID = arrivalAirportID;
	}

	public Route() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public String getAirlineID() {
		return airlineID;
	}

	public void setAirlineID(String airlineID) {
		this.airlineID = airlineID;
	}

	public String getDepartureAirportID() {
		return departureAirportID;
	}

	public void setDepartureAirportID(String departureAirportID) {
		this.departureAirportID = departureAirportID;
	}

	public String getArrivalAirportID() {
		return arrivalAirportID;
	}

	public void setArrivalAirportID(String arrivalAirportID) {
		this.arrivalAirportID = arrivalAirportID;
	}
	
	
	
}
