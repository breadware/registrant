package br.com.breadware.model.mapper;

import com.google.api.services.gmail.model.Message;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;

@Component
public class MessageToMimeMessageMapper implements Mapper<Message, MimeMessage> {

    @Override
    public MimeMessage map(Message message) throws MessagingException {

        byte[] emailBytes = Base64.decodeBase64(message.getRaw());

        return new MimeMessage(null, new ByteArrayInputStream(emailBytes));
    }
}