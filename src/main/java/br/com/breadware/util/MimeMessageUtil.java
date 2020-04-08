package br.com.breadware.util;

import br.com.breadware.exception.MimeMessageHandlingException;
import br.com.breadware.model.message.ErrorMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Component
public class MimeMessageUtil {

    public static final String MIMETYPE_MULTIPART_ANY = "multipart/*";

    public String retrieveContentAsText(MimeMessage message) throws MimeMessageHandlingException {
        StringBuffer contentStringBuffer = new StringBuffer();

        try {
            if (message.isMimeType(MediaType.TEXT_PLAIN_VALUE)) {
                contentStringBuffer.append(message.getContent()
                        .toString());
            } else if (message.isMimeType(MIMETYPE_MULTIPART_ANY)) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                getTextFromMimeMultipart(mimeMultipart, contentStringBuffer);
            }
        } catch (MessagingException | IOException exception) {
            throw new MimeMessageHandlingException(exception, ErrorMessage.ERROR_RETRIEVING_MIME_MESSAGE_CONTENT_AS_TEXT);
        }
        return contentStringBuffer.toString();
    }

    private void getTextFromMimeMultipart(MimeMultipart mimeMultipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            // TODO Currently discarding HTML content.
            if (bodyPart.isMimeType(MediaType.TEXT_PLAIN_VALUE) /* || bodyPart.isMimeType(MediaType.TEXT_HTML_VALUE)*/) {
                stringBuffer.append("\n");
                stringBuffer.append(bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent(), stringBuffer);
            }
        }
    }
}
