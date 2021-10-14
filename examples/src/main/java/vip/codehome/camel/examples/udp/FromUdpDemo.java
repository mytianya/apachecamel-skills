package vip.codehome.camel.examples.udp;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author dsys
 * @version v1.0
 **/
public class FromUdpDemo {

  public static void main(String[] args) throws Exception {
    CamelContext context=new DefaultCamelContext();
    context.addRoutes(new FromUdpRouteBuilder());
    context.start();
    while (true){
        Thread.sleep(10000);
    }
  }

  /**
   * @author dsys
   * @version v1.0
   **/
  public static class FromUdpRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("netty4:udp://0.0.0.0:8000?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535")
            .to("stream:out");
    }
  }
}
