package tr.com.obss.jip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Iterable<User> findUserByNameContains(String keyword);
}
