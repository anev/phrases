package ru.evseev.phrases.core.kafka;

import io.dropwizard.lifecycle.Managed;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import ru.evseev.phrases.core.SentenceProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by anev on 20/05/16.
 */
public class KafkaCons implements Managed {

    private final ConsumerConnector consumer;
    private final SentenceProcessor proc;

    public KafkaCons(String servers, String zookeeper, SentenceProcessor proc) {

        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("bootstrap.servers", servers);
        props.put("group.id", "phrases");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        this.proc = proc;
    }

    public void listen() {
        Map<String, Integer> topicCountMap = new HashMap();
        topicCountMap.put(KafkaClient.SENTENCIES, 10);

        final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap =
                consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> list = consumerMap.get(KafkaClient.SENTENCIES);

        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        for (KafkaStream<byte[], byte[]> stream : list) {
            executorService.submit(() -> {
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                while (it.hasNext()) {
                    proc.process(new String(it.next().message()));
                }
            });
        }
    }

    @Override
    public void start() throws Exception {
        listen();
    }

    @Override
    public void stop() throws Exception {

    }
}
