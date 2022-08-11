package tr.com.obss.jip.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.BaseUser;

import java.util.Optional;

@Repository
public interface BaseUserRepository extends CrudRepository<BaseUser, Long> {

    Optional<BaseUser> findUserByUsername(String username);

    Optional<BaseUser> findUserByName(String name);
    Optional<BaseUser> findBaseUserByUsernameAndPassword(String username, String password);

    Optional<BaseUser> findUserByEmail(String email);
}
