package ru.evseev.phrases;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import ru.evseev.phrases.core.SentenceProcessor;
import ru.evseev.phrases.core.kafka.KafkaClient;
import ru.evseev.phrases.core.kafka.KafkaCons;
import ru.evseev.phrases.health.SimpleHealthCheck;
import ru.evseev.phrases.resources.PhrasesResource;

/**
 * Created by anev on 16/05/16.
 */
public class PhrasesApplication extends Application<PhrasesConfiguration> {

    public static void main(String[] args) throws Exception {
        new PhrasesApplication().run(args);
    }

    public void run(PhrasesConfiguration config, Environment environment) throws Exception {

        environment.healthChecks().register("simple", new SimpleHealthCheck());
        KafkaClient kafkaClient = config.getKafkaClientFactory().build(environment);

        SentenceProcessor sentenceProcessor = new SentenceProcessor(config.getWordsInPhrase());

        FilesProcessor filesProcessor = new FilesProcessor(kafkaClient, config.getFolder());
        final PhrasesResource resource = new PhrasesResource(filesProcessor, sentenceProcessor);
        environment.jersey().register(resource);

        KafkaCons kafkaCons = new KafkaCons(
                config.getKafkaClientFactory().getBrokerList(),
                config.getZookeeper(),
                sentenceProcessor);

        environment.lifecycle().manage(kafkaCons);
    }
}
