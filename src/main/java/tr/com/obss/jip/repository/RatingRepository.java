package tr.com.obss.jip.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
}
