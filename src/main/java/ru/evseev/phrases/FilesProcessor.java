package ru.evseev.phrases;

import ru.evseev.phrases.core.kafka.KafkaClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * Created by anev on 20/05/16.
 */
public class FilesProcessor {

    private final KafkaClient kafkaClient;
    private String folder;

    public FilesProcessor(KafkaClient kafkaClient, String folder) {
        this.kafkaClient = kafkaClient;
        this.folder = folder;
    }

    public Result process() throws IOException {
        Sender sender = new Sender();
        splitBySentencies(folder).forEach(sender::send);
        return sender.result;
    }

    public static Stream<String> splitBySentencies(String folder) throws IOException {
        return Files.walk(Paths.get(folder))
                .parallel()
                .filter(Files::isRegularFile)
                .map(f -> {
                    try {
                        return new String(Files.readAllBytes(f));
                    } catch (IOException e) {
                        return "";
                    }
                })
                .map(s -> Arrays.asList(s.split("\\.")))
                .flatMap(Collection::stream)
                .map(FilesProcessor::removePunctuation)
                .map(String::trim)
                .filter(s -> !s.isEmpty());
    }

    static String removePunctuation(String s) {
        return s.replaceAll("[^a-zA-Zа-яА-Я]]", "");
    }

    class Sender {
        public final Result result = new Result();

        public void send(String s) {
            try {
                kafkaClient.sendSentence(s);
                result.ok();
            } catch (Exception e) {
                result.err();
            }
        }
    }

    public class Result {
        public final AtomicLong ok = new AtomicLong(0);
        public final AtomicLong err = new AtomicLong(0);

        public void ok() {
            ok.incrementAndGet();
        }

        public void err() {
            ok.incrementAndGet();
        }

        public long getOk() {
            return ok.get();
        }

        public long getErr() {
            return err.get();
        }
    }
}
