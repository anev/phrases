package ru.evseev.phrases.core.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by anev on 19/05/16.
 */
public class KafkaClient {

    private final ProducerConfig config;
    private Producer<String, String> producer;
    public static final String SENTENCIES = "sentencies";

    public KafkaClient(String brokerList) {

        Properties props = new Properties();
        props.put("metadata.broker.list", brokerList);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 1000000);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "ru.evseev.phrases.core.kafka.SimplePartitioner");
        props.put("request.required.acks", "1");
        props.put("buffer.memory", 1000000);

        config = new ProducerConfig(props);
        producer = new Producer(config);
    }

    public void stop() throws Exception {
        producer.close();
    }

    public void send(String topic, String value) {
        producer.send(new KeyedMessage(topic, value));
    }

    public void sendSentence(String value) {
        producer.send(new KeyedMessage(SENTENCIES, value));
    }
}
