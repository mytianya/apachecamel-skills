package vip.codehome.camel.examples.pf4j;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

public class Pf4jConsumer extends DefaultConsumer {
    public Pf4jConsumer(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

    @Override
    protected void doStart() throws Exception {
        while (true){
            Thread.sleep(1000);
            Pf4jEndpoint endpoint=(Pf4jEndpoint) getEndpoint();
            System.out.println(endpoint.getConfiguration());
            Exchange exchange=endpoint.createExchange();
            exchange.getIn().setBody(UUID.randomUUID().toString());
            try{
                this.getProcessor().process(exchange);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
