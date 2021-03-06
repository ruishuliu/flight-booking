package booking.bookingservice.resources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.MongoClient;

import booking.bookingservice.models.*;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/booking")
public class BookingResource {
	
//	private final static String userUrl = "http://localhost:8081/user";
//	private final static String flightUrl = "http://localhost:8082/flight";
//	private final static String db = "local";
//	private final static String mongo= "localhost";
	
	private final static int firstSeatMax =20; // 1-20
	private final static int ecoSeatMax =100;   //21-100
	
	@Value("${userUrl}")
	private String userUrl = "http://localhost:8081/user";
	
	@Value("${flightUrl}")
	private String flightUrl = "http://localhost:8082/flight";
	
	@Value("${db}")
	private String db = "local";
	
	@Value("${dbhost}")
	private String mongo= "localhost";
	
	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Bean
	MongoClient mongoClient() {
		return new MongoClient(mongo);
	}
	
	@Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient, db);
	}
	
	@Bean
	RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	//retrieve booking info by username
	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<BookingDetail> getBooking(@RequestParam("username") String username) {
		
		//get passenger info from userservice
		Passenger passenger = getPassengerInfo(1,username);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("passengerID").is(passenger.getPassengerID()));
		
		Booking booking = mongoTemplate.findOne(query, Booking.class);
		
		BookingDetail detail = getBookingDetail(passenger, booking);
		
		return new ResponseEntity<BookingDetail>(detail,HttpStatus.OK);
	}


	//Retrieve booking info by ticketID
	@RequestMapping(value = "/{ticketID}", method = RequestMethod.GET)
	public ResponseEntity<BookingDetail> searchBooking(@PathVariable("ticketID") String ticketID){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("ticketID").is(ticketID));

		Booking booking = mongoTemplate.findOne(query, Booking.class);
		
		Passenger passenger = getPassengerInfo(2,booking.getPassengerID());
		
		BookingDetail detail = getBookingDetail(passenger,booking);
		
		return new ResponseEntity<BookingDetail>(detail,HttpStatus.OK);
	}
	
	//Retrieve all the bookings
	@RequestMapping(value = "/bookings", method = RequestMethod.GET)
	public ResponseEntity <Collection<Booking>> searchBookings(){

		Query query = new Query();
		List<Booking> result = mongoTemplate.find(query, Booking.class);
		if (result == null) {
			throw new ResponseStatusException(
					HttpStatus.UNPROCESSABLE_ENTITY,
					"NO user found");
			
		}else 
			return new ResponseEntity<Collection<Booking>>(result, HttpStatus.OK);
	}
	
	
	//search flights
	@RequestMapping(value="/flights", method = RequestMethod.GET)
	public ResponseEntity<FlightDetail[]> getFlights(@RequestParam(value="departureDate",required=true) String depDate,
			@RequestParam(value="departureCity") String depCity,
			@RequestParam(value="arrivalCity") String arrCity){
		
		String reqUrl = flightUrl+ "?departureDate=" + depDate+ "&departureCity="+ depCity + "&arrivalCity=" + arrCity;
		
		ResponseEntity<FlightDetail[]> flights = restTemplate.getForEntity(reqUrl, FlightDetail[].class);

	return new ResponseEntity<FlightDetail[]>(flights.getBody(),HttpStatus.OK);
	}
	
	//choose one flight&get seats info
	@RequestMapping(value="/flights/{flightID}", method = RequestMethod.GET)
	public ResponseEntity<FlightDetail> flightToBook(@PathVariable("flightID") String flightID)
	{
		String reqUrl = flightUrl+"/" + flightID;
		FlightDetail flight = restTemplate.getForObject(reqUrl, FlightDetail.class);
		
		List<String> firstSeatsLeft = getSeatsLeft(flightID, "First");
		List<String> ecoSeatsLeft = getSeatsLeft(flightID,"Economy");
		
		flight.setFirstSeatsLeft(firstSeatsLeft);
		flight.setEcoSeatsLeft(ecoSeatsLeft);
		return new ResponseEntity<FlightDetail>(flight, HttpStatus.OK);
	}
	
	//create a new booking 
	@RequestMapping(value="/flights/{flightID}", method = RequestMethod.POST)
	public ResponseEntity<Booking> newBooking(@RequestParam("username") String username, @RequestParam("class") String classType,
											@RequestBody FlightDetail flight){
		
		//Create a new passenger
		UserItem user= restTemplate.getForObject(userUrl + "/" + username ,UserItem.class);
		String passengerID = createPassengerID();
		String age = getAge(user.getDob());
		
		Passenger passenger = new Passenger(passengerID,user.getFirstName(),user.getLastName(), 
								user.getEmail(),age);
		try {
			mongoTemplate.insert(passenger);
		} catch(Exception ex) {
			return  new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		//Allocate a seat
		String seatNum="0";
		if(classType.equals("First"))  {
			if(flight.getFirstSeatsLeft().size() == 0)
				return new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
			seatNum=flight.getFirstSeatsLeft().get(0);
		}
		else {
			if(flight.getEcoSeatsLeft().size() == 0)
				return new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
			seatNum=flight.getEcoSeatsLeft().get(0);
		}
		
		Booking booking = new Booking(createTicketID(),flight.getFlightID(),seatNum,classType,passenger.getPassengerID());
		
		try {
			mongoTemplate.insert(booking);
		}catch (Exception ex) {
			return new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Booking>(booking,HttpStatus.CREATED);
	}
	
	//delete a booking info
	@RequestMapping(value="/{ticketID}" , method = RequestMethod.DELETE)
	public ResponseEntity<Booking> delBooking(@PathVariable ("ticketID") String ticketID){
		
		
		Query query2 = new Query();
		query2.addCriteria(Criteria.where("ticketID").is(ticketID));
		Booking booking = mongoTemplate.findOne(query2, Booking.class);
		
		//delete the passenger
		Query query= new Query();
		query.addCriteria(Criteria.where("passengerID").is(booking.getPassengerID()));
		
		mongoTemplate.remove(query, Passenger.class);
		
		//delete the booking info
		try {
			mongoTemplate.remove(query2, Booking.class);
		} catch( Exception ex) {
			return  new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Booking>(HttpStatus.OK);
		
	}
	
	//update seat
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking)
	{
		//check whether given seatnum is available
		List<String> seats = new ArrayList<>();
		String seatNum = booking.getSeatNum();
		Boolean available = false;
		
		seats = getSeatsLeft(booking.getFlightID(), booking.getClassType());
		for(int i =0; i < seats.size(); i++) {
			if(seatNum.equals(seats.get(i))){
				available = true;
				break;
			}	
		}
		if(available == false)
			return new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
			
		Query query = new Query();
		Update update = new Update();
		
		if(booking.getPassengerID() != null)
			update.set("passengerID",booking.getPassengerID());
		update.set("seatNum", booking.getSeatNum());
		update.set("classType",booking.getClassType());
		update.set("flightID",booking.getFlightID());
		
		query.addCriteria(Criteria.where("ticketID").is(booking.getTicketID()));
		try {
			mongoTemplate.updateFirst(query,update,Booking.class);
		}catch(Exception ex) {
			return new ResponseEntity<Booking>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Booking>(HttpStatus.CREATED);
	}
	
	
	private String createPassengerID() {
		int max = 0;
		
		Query query = new Query();
		List<Passenger> passengers = mongoTemplate.find(query, Passenger.class);
		
		if(passengers.size()== 0)
			return "1";
		
		for( int i =0; i< passengers.size(); i ++) {
			if (Integer.valueOf(passengers.get(i).getPassengerID()) > max )
				max = Integer.valueOf(passengers.get(i).getPassengerID());
		}
		max = max +1;
		return Integer.toString(max);
	}
	
	private String createTicketID() {
		int max = 0;
		
		Query query = new Query();
		List<Booking> bookings = mongoTemplate.find(query, Booking.class);
		
		if(bookings.size() ==0)
			return "1";
		
		for( int i =0; i< bookings.size(); i ++) {
			if (Integer.valueOf(bookings.get(i).getTicketID()) > max )
				max = Integer.valueOf(bookings.get(i).getTicketID());
		}
		max = max +1;
		return Integer.toString(max);
	}

	//get user age
	private String getAge(String dob) {
		
		Calendar calendar= Calendar.getInstance();
		int nowYear= calendar.get(Calendar.YEAR);
		
		String syear = dob.substring(6);
		int year = Integer.valueOf(syear);
		year = year + 1900;
		
		int age = nowYear - year;
		if( age >100)
			age = age -100;
		return Integer.toString(age);
	}
	
	// byWhat: 1- by username; 2- by passengerID
	private Passenger getPassengerInfo(int byWhat, String info) {
		
		Query  query = new Query();
		
		if( byWhat == 1) {
			UserItem user= restTemplate.getForObject(userUrl + "/" + info ,UserItem.class);
			query.addCriteria(Criteria.where("firstName").is(user.getFirstName())
			.and("lastName").is(user.getLastName()));
		}
		else 
			query.addCriteria(Criteria.where("passengerID").is(info));
		Passenger passenger = mongoTemplate.findOne(query,Passenger.class);
		return passenger;
	}
	
	private BookingDetail getBookingDetail(Passenger passenger, Booking booking) {
		FlightDetail flight= restTemplate.getForObject(flightUrl + "/" + booking.getFlightID(), FlightDetail.class);
		BookingDetail detail =  new BookingDetail(booking.getTicketID(),flight,booking.getClassType()
				,booking.getSeatNum(),passenger);
		return detail;
	}
	
	private List<String> getSeatsLeft(String flightID, String classType) {
		
		List<String> seatsUsed = new ArrayList<>();
		List<String> seatsLeft = new ArrayList<>();
		int i=0,j=0;
		int seatmin=0, seatmax=0;
		
		Query query = new Query();
		query.addCriteria(Criteria.where("flightID").is(flightID).and("classType").is(classType));
		List<Booking> bookings = mongoTemplate.find(query, Booking.class);
		for(i=0; i< bookings.size(); i ++)
			seatsUsed.add(bookings.get(i).getSeatNum());
		
		if(classType.equals("First")) {
			seatmin=1;
			seatmax=firstSeatMax;
		}
		else {
			seatmin=firstSeatMax +1;
			seatmax=ecoSeatMax;
		}
		
		Boolean ifUsed = false;
		for(i=seatmin; i <= seatmax; i ++) {
			ifUsed =false;
			for( j=0; j< seatsUsed.size(); j ++) {
				if(i == Integer.valueOf(seatsUsed.get(j))) {
					ifUsed = true;
					break;
				}
				
			}
			if( ifUsed == false)
				seatsLeft.add(Integer.toString(i));
		}
		return seatsLeft;	
	}
}
