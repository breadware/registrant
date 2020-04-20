package br.com.breadware.google.mail.message.composer;

import br.com.breadware.bo.AssociateBo;
import br.com.breadware.configuration.BeanNames;
import br.com.breadware.model.Associate;
import br.com.breadware.model.Email;
import br.com.breadware.model.mapper.MessageToMimeMessageTwoWayMapper;
import br.com.breadware.model.message.EmailMessage;
import br.com.breadware.properties.RegistrantProperties;
import br.com.breadware.util.MessageRetriever;
import br.com.breadware.util.MimeMessageUtil;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;

@Component
public class GmailMessageComposer {

    private final AssociateBo associateBo;

    private final RegistrantProperties registrantProperties;

    private final MessageRetriever messageRetriever;

    private final MimeMessageUtil mimeMessageUtil;

    private final MessageToMimeMessageTwoWayMapper messageToMimeMessageTwoWayMapper;

    private final InternetAddress associationEmailInternetAddress;

    private final GmailMessageBodyComposer gmailMessageBodyComposer;

    public GmailMessageComposer(RegistrantProperties registrantProperties, MessageRetriever messageRetriever, AssociateBo associateBo, MimeMessageUtil mimeMessageUtil, MessageToMimeMessageTwoWayMapper messageToMimeMessageTwoWayMapper, @Named(BeanNames.ASSOCIATION_EMAIL_INTERNET_ADDRESS) InternetAddress associationEmailInternetAddress, GmailMessageBodyComposer gmailMessageBodyComposer) {
        this.registrantProperties = registrantProperties;
        this.messageRetriever = messageRetriever;
        this.associateBo = associateBo;
        this.mimeMessageUtil = mimeMessageUtil;
        this.messageToMimeMessageTwoWayMapper = messageToMimeMessageTwoWayMapper;
        this.associationEmailInternetAddress = associationEmailInternetAddress;
        this.gmailMessageBodyComposer = gmailMessageBodyComposer;
    }


    public Message compose(Email email, Associate associate) {
        try {
            String associateFullName = associateBo.elaborateFullName(associate);
            InternetAddress toInternetAddress = new InternetAddress(associate.getEmail(), associateFullName);

            String subject = elaborateSubject(email.getSubjectMessage(), associateFullName);

            MimeMultipart messageBody = gmailMessageBodyComposer.create(email.getThymeleafTemplates(), associate);

            MimeMessage mimeMessage = mimeMessageUtil.create(toInternetAddress, associationEmailInternetAddress, subject, messageBody);
            return messageToMimeMessageTwoWayMapper.mapFrom(mimeMessage);
        } catch (UnsupportedEncodingException | MessagingException exception) {
            // TODO Correctly handle exception.
            exception.printStackTrace();
        }
        return null;
    }

    private String elaborateSubject(EmailMessage subjectEmailMessage, String associateFullName) {
        return messageRetriever.getMessage(subjectEmailMessage, registrantProperties.getAssociationName(), associateFullName);
    }
}
