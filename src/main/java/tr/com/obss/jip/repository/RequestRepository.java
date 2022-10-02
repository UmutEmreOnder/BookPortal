package tr.com.obss.jip.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.AddingBookRequest;
import tr.com.obss.jip.model.Author;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<AddingBookRequest, Long> {
    List<AddingBookRequest> findAddingBookRequestsByAuthor(Author authenticatedAuthor, Pageable pageable);
}
