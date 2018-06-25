package app.repositories;


import org.springframework.data.repository.CrudRepository;

import app.models.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

}
