package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(AbstractEmailContext email) throws MessagingException;
}
