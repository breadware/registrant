package br.com.breadware.util;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Component
public class MimeMessageUtil {

    private String retrieveContentAsText(MimeMessage message) {
        StringBuffer contentStringBuffer = new StringBuffer();
        try {
            if (message.isMimeType(MediaType.TEXT_PLAIN_VALUE)) {
                contentStringBuffer.append(message.getContent()
                        .toString());
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                getTextFromMimeMultipart(mimeMultipart, contentStringBuffer);
            }
        } catch (MessagingException | IOException exception) {
            // TODO Correctly handle exception.
            exception.printStackTrace();
            return null;
        }
    }

    private void getTextFromMimeMultipart(MimeMultipart mimeMultipart, StringBuffer stringBuffer) throws MessagingException, IOException {
        for (int i = 0; i < mimeMultipart.getCount(); i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType(MediaType.TEXT_PLAIN_VALUE) || bodyPart.isMimeType(MediaType.TEXT_HTML_VALUE)) {
                stringBuffer.append("\n" + bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent(), stringBuffer);
            }
        }
    }
}
