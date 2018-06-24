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
import app.models.Photo;
import app.models.User;
import app.repositories.BusinessRepository;
import app.repositories.PhotoRepository;

@RestController
@CrossOrigin(origins = "${ORIGINS}", maxAge = 3600, allowCredentials = "true")
public class PhotoService {

	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired 
	BusinessRepository businessRepository;
	
	@GetMapping("/api/photo")
	public Iterable<Photo> findAllPhotos() {
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
	
	@PostMapping("/api/photo/{photoId}/like")
	public void likePhoto(HttpSession session, HttpServletResponse response) {
		User currentUser = (User) session.getAttribute("currentUser");
		
		if(currentUser == null) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@PostMapping("/api/business/{businessId}/photo")
	public Photo createPhoto(@RequestBody Photo photo, 
			@PathVariable("businessId") int businessId) {
		Optional<Business> b = businessRepository.findById(businessId);
		
		if(b.isPresent()) {
			Business bus = b.get();
			photo.setBusiness(bus);  // the lesson belongs to this module
			return photoRepository.save(photo);
		}
		return null;
	}
	
}
