package br.com.breadware;

import br.com.breadware.subscriber.SubscriberCreator;
import br.com.breadware.watch.WatchRequester;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import javax.inject.Inject;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.breadware.properties")
public class Registrant implements CommandLineRunner {

    private final WatchRequester watchRequester;

    private final SubscriberCreator subscriberCreator;

    @Inject
    public Registrant(WatchRequester watchRequester, SubscriberCreator subscriberCreator) {
        this.watchRequester = watchRequester;
        this.subscriberCreator = subscriberCreator;
    }

    public static void main(String[] args) {
        SpringApplication.run(Registrant.class, args);
    }

    @Override
    public void run(String... args) {
        watchRequester.request();
        subscriberCreator.createAndStart();
    }
}