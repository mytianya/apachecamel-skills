package vip.codehome.camel.examples.pf4j;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import vip.codehome.camel.examples.custom.CustomFileComponet;

public class Pf4jComponentDemo {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext=new DefaultCamelContext();
        //替代了在类路径META-INF/services/org/apache/camel/component下放置properties文件方式
        camelContext.addComponent("pf4j",new Pf4jComponent());
        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                this.from("pf4j:ws?version=1.0").process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                }).to("stream:out");
            }
        });
        camelContext.start();
        Object object=new Object();
        synchronized (object){
            object.wait();
        }
    }
}
