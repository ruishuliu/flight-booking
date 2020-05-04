package booking.bookingservice.models;

public class BookingDetail {
	private String ticketID;
	private FlightDetail flight;
	private String classType;
	private String seatNum;
	private Passenger passenger;
	
	public BookingDetail(String ticketID, FlightDetail flight, String classType, String seatNum, Passenger passenger) {
		super();
		this.ticketID = ticketID;
		this.flight = flight;
		this.classType = classType;
		this.seatNum = seatNum;
		this.passenger = passenger;
	}

	public BookingDetail() {
		
	}

	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public FlightDetail getFlight() {
		return flight;
	}

	public void setFlight(FlightDetail flight) {
		this.flight = flight;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}
	
	
}
