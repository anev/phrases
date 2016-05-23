package ru.evseev.phrases;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import ru.evseev.phrases.core.kafka.KafkaClientFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by anev on 16/05/16.
 */
public class PhrasesConfiguration extends Configuration {

    private String zookeeper;
    private int wordsInPhrase;
    private String folder;

    @Valid
    @NotNull
    private KafkaClientFactory kafkaClientFactory = new KafkaClientFactory();

    @JsonProperty("kafka")
    public KafkaClientFactory getKafkaClientFactory() {
        return kafkaClientFactory;
    }

    @JsonProperty("kafka")
    public void setKafkaClientFactory(KafkaClientFactory kafkaClientFactory) {
        this.kafkaClientFactory = kafkaClientFactory;
    }

    @JsonProperty
    public String getZookeeper() {
        return zookeeper;
    }

    @JsonProperty
    public void setZookeeper(String zookeeper) {
        this.zookeeper = zookeeper;
    }

    @JsonProperty
    public int getWordsInPhrase() {
        return wordsInPhrase;
    }

    @JsonProperty
    public void setWordsInPhrase(int wordsInPhrase) {
        this.wordsInPhrase = wordsInPhrase;
    }

    @JsonProperty
    public String getFolder() {
        return folder;
    }

    @JsonProperty
    public void setFolder(String folder) {
        this.folder = folder;
    }
}
