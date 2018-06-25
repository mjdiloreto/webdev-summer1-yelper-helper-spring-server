package app.services;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import app.models.Review;
import app.models.User;
import app.repositories.BusinessRepository;
import app.repositories.ReviewRepository;


@RestController
@CrossOrigin(origins = "${ORIGINS}", maxAge = 3600, allowCredentials = "true")
public class BusinessService {
	
	@Autowired
	BusinessRepository businessRepository;
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@GetMapping("/api/business")
	public Iterable<Business> findAllBusinesses() {
		return businessRepository.findAll(); 
	}
	
	@GetMapping("/api/business/{businessId}")
	public Iterable<Business> findBusinessById(@PathVariable("businessId") String id) {
		return businessRepository.findByYelpId(id);
	}
	
	@PostMapping("/api/business/{businessId}/review")
	public Review createReview(@PathVariable("businessId") Integer id,
			@RequestBody Review review,
			HttpSession session, HttpServletResponse response) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(currentUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} else {
			Optional<Business> optBus = businessRepository.findById(id);
			
			if(optBus.isPresent()) {
				Business b = optBus.get();
				
				review.setBusiness(b);
				review.setUser(currentUser);
				
				return reviewRepository.save(review);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
		}
	}
	
//	@PostMapping("/api/business/{businessId}/like")
//	public Business likeBusiness(@PathVariable("businessId") Integer id, 
//			HttpSession session, HttpServletResponse response) {
//		User currentUser = (User) session.getAttribute("currentUser");
//		
//		if(currentUser == null) {
//			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			return null;
//		} else {
//			Optional<Business> optBus = businessRepository.findById(id);
//			
//			if(optBus.isPresent()) {
//				Business b = optBus.get();
//				
//				if(b.get)
//			}
//		}
//		
//	}
	
	@PostMapping("/api/business")
	public Business createBusiness(@RequestBody Business business) {
		return businessRepository.save(business);
	}

}
