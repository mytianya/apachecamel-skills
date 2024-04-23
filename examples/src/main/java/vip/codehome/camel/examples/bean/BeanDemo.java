package vip.codehome.camel.examples.bean;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.impl.DefaultCamelContext;
import vip.codehome.camel.examples.webservice.FromWebServiceDemo;

public class BeanDemo {
    public static void main(String[] args) throws Exception {
        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new BeanDemo.BeanBuilder());
        context.start();
        while (true){
            Thread.sleep(10000);
        }

    }

    /**
     * @author dsys
     * @version v1.0
     **/
    public static class BeanBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("timer:start?fixedRate=true&period=5000")
                    .setHeader("allParams",constant("{xxx}"))
                    .bean(new WebServiceBean(),"invoke2")
                    .to("stream:out");

        }
    }
}
