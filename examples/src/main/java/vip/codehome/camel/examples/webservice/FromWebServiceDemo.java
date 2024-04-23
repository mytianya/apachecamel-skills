package vip.codehome.camel.examples.webservice;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.Constants;
import org.apache.cxf.message.MessageContentsList;
import vip.codehome.camel.examples.udp.FromUdpDemo;

import static org.apache.camel.model.rest.RestParamType.body;

/***
 * camel-cxf componenet ,cxf是webservice库
 */
public class FromWebServiceDemo {
    public static void main(String[] args) throws Exception {
        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new FromWebServiceDemo.FromWebServiceRouteBuilder());
        context.start();
        while (true){
            Thread.sleep(10000);
        }

    }

    /**
     * @author dsys
     * @version v1.0
     **/
    public static class FromWebServiceRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("timer:start?fixedRate=true&period=5000").setHeader(CxfConstants.OPERATION_NAME, constant("test"))
                    .to("cxf:http://localhost:8080/cxf/weather?serviceClass=mytianya.gitee.io.webservice.WeatherService")
                    .process(new Processor() {
                        @Override
                        public void process(Exchange exchange) throws Exception {
                          Object object= exchange.getOut().getBody();
                            System.out.println(object);
                        }
                    });

        }
    }
}
