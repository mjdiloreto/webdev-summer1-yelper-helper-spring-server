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

import app.models.Photo;
import app.repositories.PhotoRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PhotoService {

	@Autowired
	PhotoRepository photoRepository;
	
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
	
//	@PostMapping("/api/business/{businessId}/photo")
//	public Photo createPhoto(@RequestBody Photo photo, 
//			@PathVariable("businessId") int businessId) {
//		Optional<Business> b = businessRepository.findById(businessId);
//		
//		if(b.isPresent()) {
//			Business bus = b.get();
//			Photo.setBusiness(bus);  // the lesson belongs to this module
//			return photoRepository.save(photo);
//		}
//		return null;
//	}
	
}
