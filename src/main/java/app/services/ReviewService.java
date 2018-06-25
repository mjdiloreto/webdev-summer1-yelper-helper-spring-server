package app.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.models.Business;
import app.models.Review;
import app.models.User;
import app.repositories.BusinessRepository;
import app.repositories.ReviewRepository;

@RestController
@CrossOrigin(origins = "${ORIGINS}", maxAge = 3600, allowCredentials = "true")
public class ReviewService {
	
	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	BusinessRepository businessRepository;
	
	@GetMapping("/api/review/{reviewId}")
	public Review findReviewById(@PathVariable("reviewId") int id) {
		Optional<Review> optRev = reviewRepository.findById(id);
		if(optRev.isPresent()) {
			return optRev.get();
		} else {
			return null;
		}
	}
	
	@GetMapping("/api/review")
	public Iterable<Review> findAll() {
		return reviewRepository.findAll();
	}
	
	@DeleteMapping("/api/review/{reviewId}")
	public void deleteReview(@PathVariable("reviewId") int id) {
		reviewRepository.deleteById(id);
	}
	
	@GetMapping("/api/review/{reviewId}/user")
	public User findUserOfReview(@PathVariable("reviewId") int id) {
		Optional<Review> optRev = reviewRepository.findById(id);
		if(optRev.isPresent()) {
			return optRev.get().getUser();
		} else {
			return null;
		}
	}
	
	@GetMapping("/api/review/{reviewId}/business")
	public Business findBusinessOfReview(@PathVariable("reviewId") int id) {
		Optional<Review> optRev = reviewRepository.findById(id);
		if(optRev.isPresent()) {
			return optRev.get().getBusiness();
		} else {
			return null;
		}
	}
	
	@GetMapping("/api/review/recent")
	public Iterable<Review> findRecent() {
		List<Review> allReviews = (List<Review>) reviewRepository.findAll();
		if(allReviews.size() > 4) {
			return allReviews.subList(0, 3);
		}
		
		return allReviews;
	}

	@PostMapping("/api/business/{businessId}/review")
	public Review createReview(@PathVariable("businessId") String id,
			@RequestBody Review review,
			HttpSession session, HttpServletResponse response) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(currentUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} else {
			List<Business> optBus = (List<Business>) businessRepository.findByYelpId(id);
			
			if(optBus.size() != 0) {
				Business b = optBus.get(0);
				
				review.setBusiness(b);
				review.setUser(currentUser);
				
				return reviewRepository.save(review);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
		}
	}
}
