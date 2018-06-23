package app.repositories;

import org.springframework.data.repository.CrudRepository;
import app.models.Business;

public interface BusinessRepository extends CrudRepository<Business, Integer> {
}
