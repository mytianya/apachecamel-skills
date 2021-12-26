package vip.codehome.camel.examples.core;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.RouteDefinition;

/**
 * @author dsys
 * @version v1.0
 **/
public class FromUdpDemo {

  public static void main(String[] args) throws Exception {
    CamelContext context=new DefaultCamelContext();
    CommonRouteBuilder commonRouteBuilder=new CommonRouteBuilder();
    commonRouteBuilder.setId("test");
    commonRouteBuilder.setFromUrl("netty4:udp://0.0.0.0:8000?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535");
    commonRouteBuilder.setToUrl("stream:out");
    commonRouteBuilder.setProcessor(new CommonProcessor());
    context.addRoutes(commonRouteBuilder);
    context.start();
    while (true){
        Thread.sleep(10000);
    }

  }

  /**
   * @author dsys
   * @version v1.0
   **/

}
