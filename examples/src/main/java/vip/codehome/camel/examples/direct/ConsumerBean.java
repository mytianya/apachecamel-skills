package vip.codehome.camel.examples.direct;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.util.UUID;

public class ConsumerBean {
    public String consumer(ProducerTemplate producerTemplate){
        while (true){
            producerTemplate.sendBody(UUID.randomUUID().toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
