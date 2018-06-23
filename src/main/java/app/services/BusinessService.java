package app.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.models.Business;
import app.repositories.BusinessRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BusinessService {
	
	@Autowired
	BusinessRepository businessRepository;
	
	@GetMapping("/api/business")
	public Iterable<Business> findAllBusinesses() {
		return businessRepository.findAll(); 
	}
	
	@GetMapping("/api/business/{businessId}")
	public Business findBusinessById(@PathVariable("businessId") int id) {
		Optional<Business> b = businessRepository.findById(id);
		if(b.isPresent()) {
			return b.get();
		}
		return null;
	}

}
