package booking.bookingservice.models;

import java.util.List;
import java.util.ArrayList;

public class FlightDetail {

	private String flightID;
	private String departureTime;
	private String departureDate;
	private String departureAirportName;
	private String departureCountry;
	private String arrivalTime;
	private String arrivalDate;
	private String arrivalAirportName;
	private String arrivalCountry;
	private String airlineName;
	private String airlineCountry;
	private List<String> firstSeatsLeft;
	private List<String> ecoSeatsLeft;
	
	public FlightDetail(String flightID, String departureTime, String departureDate, String departureAirportName,
			String departureCountry, String arrivalTime, String arrivalDate, String arrivalAirportName, String arrivalCountry, String airlineName,
			String airlineCountry) {

		this.flightID = flightID;
		this.departureTime = departureTime;
		this.departureDate = departureDate;
		this.departureAirportName = departureAirportName;
		this.departureCountry = departureCountry;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.arrivalAirportName = arrivalAirportName;
		this.arrivalCountry = arrivalCountry;
		this.airlineName = airlineName;
		this.airlineCountry = airlineCountry;
		this.firstSeatsLeft= new ArrayList<>();
		this.ecoSeatsLeft = new ArrayList<>();
	}
	
	
	public FlightDetail(String flightID, String departureTime, String departureDate, String departureAirportName,
			String departureCountry, String arrivalTime, String arrivalDate, String arrivalAirportName,
			String arrivalCountry, String airlineName, String airlineCountry, List<String> firstSeatsLeft,
			List<String> ecoSeatsLeft) {
		super();
		this.flightID = flightID;
		this.departureTime = departureTime;
		this.departureDate = departureDate;
		this.departureAirportName = departureAirportName;
		this.departureCountry = departureCountry;
		this.arrivalTime = arrivalTime;
		this.arrivalDate = arrivalDate;
		this.arrivalAirportName = arrivalAirportName;
		this.arrivalCountry = arrivalCountry;
		this.airlineName = airlineName;
		this.airlineCountry = airlineCountry;
		this.firstSeatsLeft = firstSeatsLeft;
		this.ecoSeatsLeft = ecoSeatsLeft;
	}


	public FlightDetail() {
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

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureAirportName() {
		return departureAirportName;
	}

	public void setDepartureAirportName(String departureAirportName) {
		this.departureAirportName = departureAirportName;
	}

	public String getDepartureCountry() {
		return departureCountry;
	}

	public void setDepartureCountry(String departureCountry) {
		this.departureCountry = departureCountry;
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

	
	public String getArrivalAirportName() {
		return arrivalAirportName;
	}



	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}



	public String getArrivalCountry() {
		return arrivalCountry;
	}

	public void setArrivalCountry(String arrivalCountry) {
		this.arrivalCountry = arrivalCountry;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineCountry() {
		return airlineCountry;
	}

	public void setAirlineCountry(String airlineCountry) {
		this.airlineCountry = airlineCountry;
	}

	public List<String> getFirstSeatsLeft() {
		return firstSeatsLeft;
	}

	public void setFirstSeatsLeft(List<String> firstSeatsLeft) {
		this.firstSeatsLeft = firstSeatsLeft;
	}

	public List<String> getEcoSeatsLeft() {
		return ecoSeatsLeft;
	}

	public void setEcoSeatsLeft(List<String> ecoSeatsLeft) {
		this.ecoSeatsLeft = ecoSeatsLeft;
	}
	
	
}
