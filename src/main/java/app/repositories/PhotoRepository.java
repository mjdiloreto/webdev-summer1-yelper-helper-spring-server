package app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.models.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {

	@Query("SELECT p FROM Photo p WHERE p.src=:src")
	Iterable<Photo> findBySrc(
		@Param("src") String src);

}
