package vip.codehome.camel.examples.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultMessage;

import java.util.Arrays;

@Slf4j
public  class CommonProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        byte[] bytes=exchange.getIn().getBody(byte[].class);
        String msg=new String(bytes);
        if(msg.startsWith("S")){
            //exchange.getIn().setBody("fuck");
            exchange.getIn().setBody(null);
        }
        log.info(Arrays.toString(bytes));
    }
}
