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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.models.Business;
import app.models.Photo;
import app.models.User;
import app.repositories.BusinessRepository;
import app.repositories.PhotoRepository;
import app.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "${ORIGINS}", maxAge = 3600, allowCredentials = "true")
public class PhotoService {

	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	BusinessRepository businessRepository;
	
	@GetMapping("/api/photo")
	public Iterable<Photo> findAllPhotos(@RequestParam(value="src", required=false) String src) {
		if(src != null && !src.isEmpty()) {
			return photoRepository.findBySrc(src);
		}
		return photoRepository.findAll(); 
	}
	
	@GetMapping("/api/photo/{photoId}")
	public Photo findPhotoById(@PathVariable("photoId") int id) {
		Optional<Photo> p = photoRepository.findById(id);
		if(p.isPresent()) {
			return p.get();
		}
		return null;
	}
	
	@GetMapping("/api/photo/popular")
	public Iterable<Photo> findPopularPhotos() {
		List<Photo> pops = ((List<Photo>) photoRepository.findPopular());
		if(pops.size() > 4) {
			return pops.subList(0, 3);
		} else {
			return pops;
		}
	}
	
	@PostMapping("/api/photo/{photoId}/like")
	public Photo likePhoto(HttpSession session, HttpServletResponse response,
			@PathVariable("photoId") Integer id) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(currentUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} else {
			Optional<Photo> optP = photoRepository.findById(id);
			
			if(optP.isPresent()) {
				Photo p = optP.get();
				if(p.getLikes() == null) {
					p.setLikes(1);
				} else {
					p.setLikes(p.getLikes() + 1);
				}
				
				currentUser.getLikedPhotos().add(p);
				userRepository.save(currentUser);
				return photoRepository.save(p);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
		}
	}
	
	@PostMapping("/api/photo/{photoId}/dislike")
	public Photo dislikePhoto(HttpSession session, HttpServletResponse response,
			@PathVariable("photoId") Integer id) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(currentUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		} else {
			Optional<Photo> optP = photoRepository.findById(id);
			
			if(optP.isPresent()) {
				Photo p = optP.get();
				if(p.getDislikes() == null) {
					p.setDislikes(1);
				} else {
					p.setDislikes(p.getLikes() + 1);
				}
				
				currentUser.getLikedPhotos().add(p);
				userRepository.save(currentUser);
				return photoRepository.save(p);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return null;
			}
		}
	}
	
	@PostMapping("/api/business/{businessId}/photo")
	public Photo createPhoto(@RequestBody Photo photo, 
			@PathVariable("businessId") String businessId,
			HttpServletResponse response) {
		List<Business> b = (List<Business>) businessRepository.findByYelpId(businessId);
		
		if(b.size() != 0) {
			Business bus = b.get(0);
			photo.setBusiness(bus);  // the lesson belongs to this module
			return photoRepository.save(photo);
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
}
