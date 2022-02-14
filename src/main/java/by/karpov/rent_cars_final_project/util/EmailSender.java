package by.karpov.rent_cars_final_project.util;

import com.google.protobuf.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class EmailSender {
    private static final Logger LOGGER = LogManager.getLogger(EmailSender.class);
    private static final EmailSender INSTANCE = new EmailSender();
    private static final String MAIL_PROPERTIES_PATH = "properties/mail.properties";
    private static final String USER_KEY = "mail.user.user";
    private static final String PASSWORD_KEY = "mail.user.password";
    private static final String TITLE_MAIL = "Registration message from R...car";
    private static final String MESSAGE_TO_CODE = "Your registration confirmation code: ";
    private static final String MESSAGE_TO_LINK = "Please, follow the link and enter a code: ";
    private static final String REGISTRATION_CONFIRMATION_LINK = "http://localhost:8080/rentalcar/controller?command=to_code_entry_page_command";


    private EmailSender() {
    }

    public static EmailSender getInstance() {
        return INSTANCE;
    }

    public boolean sendMail(String emailTo, String code) throws ServiceException {
        LOGGER.info("method sendMail");
        boolean result;
        if (emailTo != null && !emailTo.isBlank()) {
            Properties properties = new Properties();
            try {
                InputStream inputStream = EmailSender.class.getClassLoader().getResourceAsStream(MAIL_PROPERTIES_PATH);
                if (inputStream == null) {
                    LOGGER.error("properties for mail is not found : {}", MAIL_PROPERTIES_PATH);
                    throw new ServiceException("Error. Properties for mail is not found");
                }
                properties.load(inputStream);
            } catch (IOException e) {
                LOGGER.error("properties for mail cannot be read", e);
                throw new ServiceException("Error. Properties file cannot be read", e);
            }
            String user = properties.getProperty(USER_KEY);
            String password = properties.getProperty(PASSWORD_KEY);

            Session session = createSession(properties, user, password);
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
                message.setSubject(TITLE_MAIL);

                BodyPart firstMessagePart = new MimeBodyPart();
                firstMessagePart.setText(MESSAGE_TO_CODE.concat(code).concat(System.lineSeparator()));
                BodyPart secondMessagePart = new MimeBodyPart();
                secondMessagePart.setText(MESSAGE_TO_LINK.concat(REGISTRATION_CONFIRMATION_LINK));
                Multipart multipartMessage = new MimeMultipart();
                multipartMessage.addBodyPart(firstMessagePart);
                multipartMessage.addBodyPart(secondMessagePart);

                message.setContent(multipartMessage);
                Transport.send(message);
                result = true;
                LOGGER.info("message was sent");
            } catch (MessagingException e) {
                LOGGER.error("Email did not send ", e);
                result = false;
            }
        } else {
            result = false;
            LOGGER.error("email address cannot be null or empty");
        }
        return result;
    }

    private Session createSession(Properties properties, String user, String password) {
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
    }
}