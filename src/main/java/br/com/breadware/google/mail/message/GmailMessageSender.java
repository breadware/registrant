package br.com.breadware.google.mail.message;

import br.com.breadware.configuration.GcpConfiguration;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
public class GmailMessageSender {

    private final Gmail gmail;

    @Inject
    public GmailMessageSender(Gmail gmail) {
        this.gmail = gmail;
    }

    public void send(Message message) {
        try {
            gmail.users()
                    .messages()
                    .send(GcpConfiguration.USER_ID, message)
                    .execute();
        } catch (IOException e) {
            // TODO Correctly handle exception.
            e.printStackTrace();
        }
    }
}
