package tr.com.obss.jip.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.RespondedBookRequest;

import java.util.List;

@Repository
public interface RespondedRequestRepository extends JpaRepository<RespondedBookRequest, Long> {

    List<RespondedBookRequest> findRespondedBookRequestByAuthor(Author authenticatedAuthor, Pageable pageable);
}
