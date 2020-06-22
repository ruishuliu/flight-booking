package booking.flightservice.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.MongoClient;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import booking.flightservice.models.*;


@RestController
@RequestMapping("/flight")
public class FlightResource{

	private final static String db = "local";
	private final static String mongo= "localhost";
	
	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	@Bean
	MongoClient mongoClient() {
		return new MongoClient(mongo);
	}
	
	@Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient, db);
	}
	

	// insert a flight
	@RequestMapping( method=RequestMethod.POST)
	public ResponseEntity<Flight> newFlight(@RequestBody Flight flight) {
		try {
			mongoTemplate.insert(flight);
		}catch (Exception ex) {
			return new ResponseEntity<Flight>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Flight>(flight,HttpStatus.CREATED);
	}
	
	// delete a flight by flightID or _id
	@RequestMapping(value="/{flightID}", method=RequestMethod.DELETE)
	public ResponseEntity<Flight> delFlight(@PathVariable("flightID") String flightID){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("flightID").is(flightID));
	
		try {
			mongoTemplate.findAllAndRemove(query, Flight.class);
		} catch( Exception ex) {
			return  new ResponseEntity<Flight>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Flight>(HttpStatus.OK);
			
	}
	
	// update info
	@RequestMapping( method=RequestMethod.PUT)
	public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight){
		
		Query query = new Query();
		Update update = new Update();
		
		if(flight.getId() != null)
			query.addCriteria(Criteria.where("id").is(flight.getId()));
		else if (flight.getFlightID() != null)
			query.addCriteria(Criteria.where("flightID").is(flight.getFlightID()));
		else 
			return new ResponseEntity<Flight>(HttpStatus.OK);
		
		if (flight.getFlightID() !=null) 
			update.set("flightID",flight.getFlightID());
		if (flight.getArrivalDate() !=null)
			update.set("arrivalDate", flight.getArrivalDate());
		if (flight.getArrivalTime() != null)
			update.set("arrivalTime", flight.getArrivalTime());
		if (flight.getDepartureDate() != null)
			update.set("departureDate", flight.getDepartureDate());
		if (flight.getDepartureTime() != null)
			update.set("departureTime",flight.getDepartureTime());
		if (flight.getRouteID() != null)
			update.set("routeID", flight.getRouteID());
		
		try {
			mongoTemplate.upsert(query, update, Flight.class);
		} catch( Exception ex) {
			return new ResponseEntity<Flight>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<Flight>(HttpStatus.CREATED);
	}
	
	//retrieve filght by ID
	@RequestMapping(value="/{flightID}", method=RequestMethod.GET)
	public ResponseEntity<FlightDetail> searchFlightbyID(@PathVariable("flightID") String flightID) {
		
		FlightDetail flightDetail = getFlightDetail(flightID);
		return new ResponseEntity<FlightDetail>(flightDetail,HttpStatus.OK) ;
		
	}
	
    //retrieve all flights
	@RequestMapping(value="/flights", method=RequestMethod.GET)
	public ResponseEntity<Collection<Flight>> allFlights() {
		
		Query query = new Query();
		List<Flight> result = mongoTemplate.find(query,Flight.class);
		if (result == null) {
			throw new ResponseStatusException(
					HttpStatus.UNPROCESSABLE_ENTITY,
					"NO user found");
		}
		else
			return new ResponseEntity<Collection<Flight>>(result, HttpStatus.OK);
		
	}	
	
	//search flights
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<FlightDetail>> searchbyDate(@RequestParam(value="departureDate",required=true) String depDate,
																@RequestParam(value="departureCity") String depCity,
																@RequestParam(value="arrivalCity") String arrCity){
		List<FlightDetail> flightsDetail = new ArrayList();
		List<String> airportIDs = getAirportIDs(depCity);
		List<String> airportIDs2 = getAirportIDs(arrCity);
		
		Query query= new Query();
		query.addCriteria(Criteria.where("departureAirportID").in( airportIDs)
				.and("arrivalAirportID").in(airportIDs2));
		
		List<Route> routes = mongoTemplate.find(query,Route.class);
		
		List<Flight> flights = getFlights(depDate, routes);
		
		
		for(int i=0; i <flights.size(); i ++) {
			flightsDetail.add(getFlightDetail(flights.get(i).getFlightID()));
		}
		return new ResponseEntity<Collection<FlightDetail>>(flightsDetail,HttpStatus.OK);
	}

	//Get flights based on routeID and date
	private List<Flight> getFlights(String depDate, List<Route> routes) {
		List<Flight> flights = new ArrayList<>();
		
		for (int i =0; i < routes.size(); i ++) {
			Query query= new Query();
			query.addCriteria(Criteria.where("departureDate").is(depDate).and("routeID").is(routes.get(i).getRouteID()));
			flights.addAll( mongoTemplate.find(query, Flight.class));
		}
		return flights;
	}

	//Get all related airportID by city name
	private List<String> getAirportIDs(String cityName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("city").is(cityName));
		List<Airport> airports1= mongoTemplate.find(query, Airport.class);
		
		List<String> airportIDs = new ArrayList();
		for ( int i =0; i < airports1.size(); i ++)
			airportIDs.add(airports1.get(i).getAirportID());
		return airportIDs;
	}
	
	// Combine related info of a flight
	private FlightDetail getFlightDetail(String flightID) {
		Query query= new Query();
		query.addCriteria(Criteria.where("flightID").is(flightID));
		
		try {
			Flight flight = mongoTemplate.findOne(query, Flight.class);
			
			Query routeQuery = new Query();
			routeQuery.addCriteria(Criteria.where("routeID").is(flight.getRouteID()));
			Route route = mongoTemplate.findOne(routeQuery, Route.class);
			
			Query airlineQ = new Query();
			airlineQ.addCriteria(Criteria.where("airlineID").is(route.getAirlineID()));
			Airline airline = mongoTemplate.findOne(airlineQ, Airline.class);
			
			Query airportQ = new Query();
			airportQ.addCriteria(Criteria.where("airportID").is(route.getDepartureAirportID()));
			Airport departureAirport = mongoTemplate.findOne(airportQ, Airport.class);
			
			Query airportQ2 = new Query();
			airportQ2.addCriteria(Criteria.where("airportID").is(route.getArrivalAirportID()));
			Airport arrivalAirport = mongoTemplate.findOne(airportQ2, Airport.class);
			
			FlightDetail flightDetail = new FlightDetail(flightID, flight.getDepartureTime(),flight.getDepartureDate()
					,departureAirport.getName(),departureAirport.getCountry(), flight.getArrivalTime(),flight.getArrivalDate()
					,arrivalAirport.getName(), arrivalAirport.getCountry(), airline.getName(), airline.getCountry());
			return flightDetail;
		} catch(Exception ex) {
			return null;
		}
		
	}
}
 