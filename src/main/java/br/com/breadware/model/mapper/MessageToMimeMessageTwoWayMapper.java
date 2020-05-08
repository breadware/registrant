package br.com.breadware.model.mapper;

import br.com.breadware.exception.MappingException;
import br.com.breadware.model.message.ErrorMessage;
import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class MessageToMimeMessageTwoWayMapper implements TwoWayMapper<Message, MimeMessage> {

    @Override
    public MimeMessage mapTo(Message message) {

        byte[] emailBytes = Base64.decodeBase64(message.getRaw());

        try {
            return new MimeMessage(null, new ByteArrayInputStream(emailBytes));
        } catch (MessagingException exception) {
            throw new MappingException(exception, ErrorMessage.ERROR_MAPPING_OBJECT, Message.class, MimeMessage.class);
        }
    }

    @Override
    public Message mapFrom(MimeMessage mimeMessage) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            mimeMessage.writeTo(byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String encodedEmail = com.google.api.client.util.Base64.encodeBase64URLSafeString(bytes);
            Message message = new Message();
            message.setRaw(encodedEmail);
            return message;
        } catch (MessagingException | IOException exception) {
            throw new MappingException(exception, ErrorMessage.ERROR_MAPPING_OBJECT, MimeMessage.class, Message.class);
        }
    }
}
