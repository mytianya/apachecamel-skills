package vip.codehome.camel.examples.file;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import vip.codehome.camel.examples.file.FromFileDemo.FromFileRouteBuilder;

/**
 * @author dsys
 * @version v1.0
 **/
public class ToFileDemo {

  public static void main(String[] args) throws Exception {
    CamelContext context=new DefaultCamelContext();
    context.addRoutes(new ToFileRouteBuilder());
    context.start();
    while (true){
      Thread.sleep(10000);
    }
  }
  public static class ToFileRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      from("kafka:CUSTOM_EVENT?brokers=172.19.1.3:9092&serializerClass=org.apache.kafka.common.serialization.ByteArraySerializer&autoOffsetReset=earliest&groupId=z11")
          .to("file://home/atc/log/trace");
    }
  }
}
