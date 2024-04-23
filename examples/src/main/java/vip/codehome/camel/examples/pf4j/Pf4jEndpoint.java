package vip.codehome.camel.examples.pf4j;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.kafka.KafkaConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.util.ObjectHelper;

import java.util.Map;
@Slf4j
public class Pf4jEndpoint extends DefaultEndpoint {
    private Pf4jConfiguration configuration=new Pf4jConfiguration();
    public Pf4jEndpoint(Pf4jComponent component, String uri, Map<String,Object> parameters){
        super(uri,component);
    }
    @Override
    public Producer createProducer() throws Exception {
        return null;
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new Pf4jConsumer(this,processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Pf4jConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Pf4jConfiguration configuration) {
        this.configuration = configuration;
    }
}
