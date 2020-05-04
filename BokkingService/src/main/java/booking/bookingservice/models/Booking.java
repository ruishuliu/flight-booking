package booking.bookingservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking")
public class Booking {
	
	@Id
	private String Id;
	private String ticketID;
	private String flightID;
	private String seatNum;
	private String classType;
	private String passengerID;
	
	public Booking () {
		
	}
	
	public Booking(String ticketID, String flightID, String seatNum, String classType, String passengerID) {

		this.ticketID = ticketID;
		this.flightID = flightID;
		this.seatNum = seatNum;
		this.classType = classType;
		this.passengerID = passengerID;
	}

	public Booking(String id, String ticketID, String flightID, String seatNum, String classType, String passengerID) {

		Id = id;
		this.ticketID = ticketID;
		this.flightID = flightID;
		this.seatNum = seatNum;
		this.classType = classType;
		this.passengerID = passengerID;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getPassengerID() {
		return passengerID;
	}

	public void setPassengerID(String passengerID) {
		this.passengerID = passengerID;
	}
	
	
}

