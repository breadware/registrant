package br.com.breadware.google.mail.message;

import br.com.breadware.configuration.GcpConfiguration;
import br.com.breadware.exception.RegistrantRuntimeException;
import br.com.breadware.model.message.ErrorMessage;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class GmailMessageModifier {

    private final Gmail gmail;

    @Inject
    public GmailMessageModifier(Gmail gmail) {
        this.gmail = gmail;
    }

    public void modify(String id, ModifyMessageRequest modifyMessageRequest) {
        try {
            gmail.users()
                    .messages()
                    .modify(GcpConfiguration.USER_ID, id, modifyMessageRequest)
                    .execute();
        } catch (IOException exception) {
            throw new RegistrantRuntimeException(exception, ErrorMessage.ERROR_MODIFYING_GMAIL_MESSAGE);
        }
    }
}
