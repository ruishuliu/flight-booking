package booking.userservice.resources;

import booking.userservice.models.UserItem;

import java.util.Map;
import java.util.List;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")

public class UserResource {
	private final static String db = "local";
	private final static String mongo= "localhost";
	
	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	//private Map<String, List<UserItem>> dataset;
	
	@Bean
	MongoClient mongoClient() {
		return new MongoClient(mongo);
	}
	
	@Bean
	MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient, db);
	}
	

	
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public ResponseEntity<UserItem> searchUserbyName(@PathVariable("name") String name) {
		Query query = new Query()
				.addCriteria(Criteria.where("username").is(name));
		
		UserItem result = mongoTemplate.findOne(query,UserItem.class);
		if (result == null) {
			throw new ResponseStatusException(
					HttpStatus.UNPROCESSABLE_ENTITY,
					"NO user found");
			
		}else 
			return new ResponseEntity<UserItem>(result,HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Collection<UserItem>> showUsers() {
		Query query = new Query();
				
		List<UserItem> result = mongoTemplate.find(query,UserItem.class);
		if (result == null) {
			throw new ResponseStatusException(
					HttpStatus.UNPROCESSABLE_ENTITY,
					"NO user found");
			
		}else 
			return new ResponseEntity<Collection<UserItem>>(result,HttpStatus.OK);
	}

	// insert a new user
	@RequestMapping(method =RequestMethod.POST)
	public ResponseEntity<UserItem> insertOne(@RequestBody UserItem newUser){
		
		try {
			mongoTemplate.insert(newUser);
		
		}catch (Exception ex) {
			return new ResponseEntity<UserItem>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<UserItem>(HttpStatus.CREATED);
	}
	
	// update userinfo by id, if it is new then insert
	@RequestMapping( method = RequestMethod.PUT)
	public ResponseEntity<UserItem> updateUser(@RequestBody UserItem user1){
		
		Query query = new Query();
		query.addCriteria(Criteria.where("Id").is(user1.getId()));
	
		Update update = new Update();
		
		if (user1.getFirstName() != null)
			update.set("firstName",user1.getFirstName());
		if (user1.getLastName() != null)
			update.set("lastName", user1.getLastName());
		if (user1.getEmail() != null)
			update.set("email", user1.getEmail());
		if (user1.getDob() != null)
			update.set("dob", user1.getDob());
		if (user1.getUserName() != null)
			update.set("username", user1.getUserName());
		
		try {
			UpdateResult result = mongoTemplate.upsert(query, update, UserItem.class);

			} catch (Exception ex) {
				return new ResponseEntity<UserItem>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
		return new ResponseEntity<UserItem>(HttpStatus.CREATED);
	}
	
	//delete a user by username or Id
	@RequestMapping(value="/{name}",method = RequestMethod.DELETE)
	public ResponseEntity<UserItem> deleteUser(@PathVariable("name") String name) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(name));
	
		try {
			mongoTemplate.remove(query, UserItem.class);
		} catch (Exception ex) {
			return new ResponseEntity<UserItem>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<UserItem>(HttpStatus.OK);
	}
	
}
