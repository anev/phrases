package ru.evseev.phrases.core.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by anev on 19/05/16.
 */
public class KafkaClientFactory {

    @NotEmpty
    private String brokerList;

    @JsonProperty
    public String getBrokerList() {
        return brokerList;
    }

    @JsonProperty
    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public KafkaClient build(Environment environment) {
        final KafkaClient client = new KafkaClient(getBrokerList());
        environment.lifecycle().manage(new Managed() {
            public void start() {
            }

            public void stop() throws Exception {
                client.stop();
            }
        });
        return client;
    }
}
