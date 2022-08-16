package tr.com.obss.jip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.model.SecureToken;

import java.util.Optional;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
    Optional<SecureToken> findSecureTokenByToken(String token);

    Long removeSecureTokenByToken(String token);
}
