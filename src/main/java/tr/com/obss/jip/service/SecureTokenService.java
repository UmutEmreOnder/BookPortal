package tr.com.obss.jip.service;

import tr.com.obss.jip.model.SecureToken;

public interface SecureTokenService {
    SecureToken createSecureToken();

    void saveSecureToken(SecureToken token);

    SecureToken findByToken(String token);

    void removeToken(SecureToken token);

    void removeTokenByToken(String token);
}
