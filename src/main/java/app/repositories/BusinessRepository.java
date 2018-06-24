package app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.models.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {
	@Query("SELECT b FROM Business b WHERE b.id=:yelpId")
	Iterable<Business> findByYelpId(
		@Param("yelpId") String yelpId);
}
