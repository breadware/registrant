package br.com.breadware.google.mail.message.composer;

import br.com.breadware.model.Associate;
import br.com.breadware.model.ThymeleafTemplates;
import br.com.breadware.properties.ReceiverBankAccountProperties;
import br.com.breadware.properties.RegistrantProperties;
import br.com.breadware.util.MimeMultipartUtil;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMultipart;

@Component
public class GmailMessageBodyComposer {

    private static final String ASSOCIATE_VARIABLE_NAME = "associate";

    private static final String ASSOCIATION_VARIABLE_NAME = "association";

    private static final String RECEIVER_BANK_ACCOUNT_VARIABLE_NAME = "receiverBankAccount";

    private final TemplateEngine templateEngine;

    private final MimeMultipartUtil mimeMultipartUtil;

    private final RegistrantProperties registrantProperties;

    private final ReceiverBankAccountProperties receiverBankAccountProperties;

    public GmailMessageBodyComposer(TemplateEngine templateEngine, MimeMultipartUtil mimeMultipartUtil, RegistrantProperties registrantProperties, ReceiverBankAccountProperties receiverBankAccountProperties) {
        this.templateEngine = templateEngine;
        this.mimeMultipartUtil = mimeMultipartUtil;
        this.registrantProperties = registrantProperties;
        this.receiverBankAccountProperties = receiverBankAccountProperties;
    }

    public MimeMultipart create(ThymeleafTemplates thymeleafTemplates, Associate associate) {
        Context context = createContext(associate);
        String htmlContent = templateEngine.process(thymeleafTemplates.getHtmlTemplateName(), thymeleafTemplates.getHtmlTemplateFragment(), context);
        String textContent = templateEngine.process(thymeleafTemplates.getTextTemplateName(), context);
        return mimeMultipartUtil.create(textContent, htmlContent);
    }

    private Context createContext(Associate associate) {
        Context context = new Context();
        context.setVariable(ASSOCIATE_VARIABLE_NAME, associate);
        context.setVariable(ASSOCIATION_VARIABLE_NAME, registrantProperties.getAssociationName());
        context.setVariable(RECEIVER_BANK_ACCOUNT_VARIABLE_NAME, receiverBankAccountProperties);
        return context;
    }
}
