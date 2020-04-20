package br.com.breadware;

import br.com.breadware.google.cloud.pubsub.GmailInboxHistoryEventSubscriberCreator;
import br.com.breadware.google.mail.watch.WatchRequester;
import br.com.breadware.model.Associate;
import br.com.breadware.model.ThymeleafTemplates;
import br.com.breadware.properties.RegistrantProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.inject.Inject;

@SpringBootApplication(exclude = {ThymeleafAutoConfiguration.class})
@ConfigurationPropertiesScan("br.com.breadware.properties")
public class Registrant implements CommandLineRunner {

    private final WatchRequester watchRequester;

    private final GmailInboxHistoryEventSubscriberCreator gmailInboxHistoryEventSubscriberCreator;

    @Inject
    public Registrant(WatchRequester watchRequester, GmailInboxHistoryEventSubscriberCreator gmailInboxHistoryEventSubscriberCreator) {
        this.watchRequester = watchRequester;
        this.gmailInboxHistoryEventSubscriberCreator = gmailInboxHistoryEventSubscriberCreator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Registrant.class, args);
    }

    @Override
    public void run(String... args) {
        watchRequester.request();
        gmailInboxHistoryEventSubscriberCreator.createAndStart();
    }
}