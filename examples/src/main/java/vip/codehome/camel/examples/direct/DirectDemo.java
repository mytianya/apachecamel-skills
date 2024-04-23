package vip.codehome.camel.examples.direct;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import vip.codehome.camel.examples.bean.BeanDemo;
import vip.codehome.camel.examples.bean.WebServiceBean;

public class DirectDemo {
    public static void main(String[] args) throws Exception {
        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new DirectDemo.DirectBuilder());
        context.start();
        ProducerTemplate producerTemplate=context.createProducerTemplate();
        producerTemplate.sendBody("direct:start",producerTemplate);
        while (true){
            Thread.sleep(10000);
        }

    }

    /**
     * @author dsys
     * @version v1.0
     **/
    public static class DirectBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("direct:start").bean(new ConsumerBean(),"consumer").to("stream:out");
        }
    }
}
