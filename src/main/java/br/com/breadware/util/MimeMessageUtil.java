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
        StringBuilder contentStringBuilder = new StringBuilder();

        try {
            if (message.isMimeType(MediaType.TEXT_PLAIN_VALUE)) {
                contentStringBuilder.append(message.getContent()
                        .toString());
            } else if (message.isMimeType(MIMETYPE_MULTIPART_ANY)) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                getTextFromMimeMultipart(mimeMultipart, contentStringBuilder);
            }
        } catch (MessagingException | IOException exception) {
            throw new MimeMessageHandlingException(exception, ErrorMessage.ERROR_RETRIEVING_MIME_MESSAGE_CONTENT_AS_TEXT);
        }
        return contentStringBuilder.toString();
    }

    private void getTextFromMimeMultipart(MimeMultipart mimeMultipart, StringBuilder stringBuilder) throws MessagingException, IOException {
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType(MediaType.TEXT_PLAIN_VALUE)) {
                stringBuilder.append("\n");
                stringBuilder.append(bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent(), stringBuilder);
            }
        }
    }
}
