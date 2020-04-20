package br.com.breadware.util;

import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.nio.charset.StandardCharsets;

@Component
public class MimeMultipartUtil {

    private static final String MIME_MULTIPART_SUBTYPE_ALTERNATIVE = "alternative";

    private static final String MEDIA_TYPE_TEXT_PLAIN_UTF8 = new MediaType("text", "plain",StandardCharsets.UTF_8).toString();

    private static final String MEDIA_TYPE_TEXT_HTML_UTF8 = new MediaType("text", "html",StandardCharsets.UTF_8).toString();


    public MimeMultipart create(String textContent, String htmlContent) {

        try {

            MimeMultipart mimeMultipart = new MimeMultipart(MIME_MULTIPART_SUBTYPE_ALTERNATIVE);

            MimeBodyPart textMessageBodyPart = new MimeBodyPart();
            textMessageBodyPart.setContent(textContent, MEDIA_TYPE_TEXT_PLAIN_UTF8);
            mimeMultipart.addBodyPart(textMessageBodyPart);

            MimeBodyPart htmlMessageBodyPart = new MimeBodyPart();
            htmlMessageBodyPart.setContent(htmlContent, MEDIA_TYPE_TEXT_HTML_UTF8);
            mimeMultipart.addBodyPart(htmlMessageBodyPart);

            return mimeMultipart;
        } catch (MessagingException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_CREATING_MIME_MULTIPART_OBJECT);
        }
    }
}
