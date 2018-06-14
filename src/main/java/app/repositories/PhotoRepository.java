package app.repositories;

import org.springframework.data.repository.CrudRepository;
import app.models.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {

}
