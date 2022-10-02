package tr.com.obss.jip.mail;

import lombok.*;
import org.springframework.web.util.UriComponentsBuilder;
import tr.com.obss.jip.model.BaseUser;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;

    @Override
    public <T> void init(T context) {
        BaseUser customer = (BaseUser) context;
        put("firstName", customer.getName());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("bookportaljip2022@gmail.com");
        setTo(customer.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationUrl(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL).path("/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }
}
