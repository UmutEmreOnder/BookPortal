package tr.com.obss.jip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.AddingBookRequest;

@Repository
public interface RequestRepository extends JpaRepository<AddingBookRequest, Long> {
}
