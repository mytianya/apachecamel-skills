package vip.codehome.camel.examples.file;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author dsys
 * @version v1.0
 * 读取文件发送
 **/
public class FromFileDemo {
  public static void main(String[] args) throws Exception {
    CamelContext context=new DefaultCamelContext();
    context.addRoutes(new FromFileRouteBuilder());
    context.start();
    while (true){
      Thread.sleep(10000);
    }
  }

  /**
   * @author dsys
   * @version v1.0
   **/
  public static class FromFileRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
      from("stream:file?fileName=C:\\Users\\codehome\\Desktop\\nginx.conf"
          + "&scanStreamDelay=1000").streamCaching()
//          .process(new Processor() {
//            @Override
//            public void process(Exchange exchange) throws Exception {
//              System.out.println("Recv:"+exchange.getMessage(String.class));
//            }
//          })
          .to("stream:out");//.to("netty4:udp://127.0.0.1:8000?receiveBufferSizePredictor=65535");
    }
  }
}
