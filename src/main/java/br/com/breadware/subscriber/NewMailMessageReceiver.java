package br.com.breadware.subscriber;

import br.com.breadware.properties.GoogleCloudPlatformProperties;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.WatchRequest;
import com.google.api.services.gmail.model.WatchResponse;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Component
public class NewMailMessageReceiver implements MessageReceiver {

    private static final String APPLICATION_NAME = "registrant";

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Inject
    private GoogleCloudPlatformProperties googleCloudPlatformProperties;

    public NewMailMessageReceiver(GoogleCloudPlatformProperties googleCloudPlatformProperties) {
        this.googleCloudPlatformProperties = googleCloudPlatformProperties;
    }

    @Override
    public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
        System.out.println("Received message " + pubsubMessage.getMessageId() + ".");
        System.out.println("Its content is: " + pubsubMessage.getData().toStringUtf8());

        ackReplyConsumer.ack();
    }

    public void watchGmail() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail gmail = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        String topicName = ProjectTopicName.format(googleCloudPlatformProperties.getProjectId(), googleCloudPlatformProperties.getTopicId());

        WatchRequest watchRequest = new WatchRequest();
        watchRequest.setTopicName(topicName).setLabelIds(Collections.singletonList("INBOX"));

        WatchResponse watchResponse = gmail.users().watch("userId", watchRequest).execute();
    }
}
