package vip.codehome.camel.examples.udp;

import java.nio.charset.Charset;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import vip.codehome.camel.examples.common.LogHelper;
import vip.codehome.camel.examples.udp.FromUdpDemo.FromUdpRouteBuilder;

/**
 * @author dsys
 * @version v1.0
 **/
public class UdpToFile {

  public static void main(String[] args) throws Exception {
    CamelContext context=new DefaultCamelContext();
    context.addRoutes(new AftnBuilder("0.0.0.0",7107,LogHelper.getLogger("aftn"),"UTF-8"));
    context.addRoutes(new AwosRouteBuilder("0.0.0.0",7108,LogHelper.getLogger("awos"),"ASCII"));
    context.addRoutes(new FromUdpToFileRouteBuilder("0.0.0.0",7115,LogHelper.getLogger("MDRS"),"UTF-8"));
    context.addRoutes(new FromUdpToFileRouteBuilder("0.0.0.0",7118,LogHelper.getLogger("GATE-A"),"UTF-8"));
    context.addRoutes(new FromUdpToFileRouteBuilder("0.0.0.0",7119,LogHelper.getLogger("GATE-B"),"UTF-8"));
    context.addRoutes(new TraceBuilder("0.0.0.0",7134,LogHelper.getLogger("SYSTRACK"),"ASCII"));
    context.start();
    while (true){
      Thread.sleep(10000);
    }
  }
  public static class AftnBuilder extends RouteBuilder {
    String udpIp;
    Integer udpPort;
    Logger logger;
    String charset;
    public AftnBuilder(String udpIp,Integer udpPort,Logger logger,String charset){
      super();
      this.udpIp=udpIp;this.udpPort=udpPort;
      this.logger=logger;
      this.charset=charset;
    }
    @Override
    public void configure() throws Exception {
      from("netty4:udp://"+udpIp+":"+udpPort+"?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535")
          .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
              byte[] bytes=exchange.getIn().getBody(byte[].class);
              logger.info("ZCZC "+new String(bytes, Charset.forName(charset))+"\nNNNN");
            }
          });
    }
  }
  public static class TraceBuilder extends RouteBuilder {
    String udpIp;
    Integer udpPort;
    Logger logger;
    String charset;
    public TraceBuilder(String udpIp,Integer udpPort,Logger logger,String charset){
      super();
      this.udpIp=udpIp;this.udpPort=udpPort;
      this.logger=logger;
      this.charset=charset;
    }
    @Override
    public void configure() throws Exception {
      from("netty4:udp://"+udpIp+":"+udpPort+"?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535")
          .to("kafka:CUSTOM_RAWDATA_TRACE?brokers=192.28.4.1:9092&serializerClass=org.apache.kafka.common.serialization.ByteArraySerializer");
    }
  }
  public static class AwosRouteBuilder extends RouteBuilder {
    String udpIp;
    Integer udpPort;
    Logger logger;
    String charset;
    public AwosRouteBuilder(String udpIp,Integer udpPort,Logger logger,String charset){
      super();
      this.udpIp=udpIp;this.udpPort=udpPort;
      this.logger=logger;
      this.charset=charset;
    }
    @Override
    public void configure() throws Exception {
      from("netty4:udp://"+udpIp+":"+udpPort+"?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535")
          .to("kafka:CUSTOM_RAWDATA_AWOS?brokers=192.28.4.1:9092&serializerClass=org.apache.kafka.common.serialization.ByteArraySerializer");
    }
  }
  public static class FromUdpToFileRouteBuilder extends RouteBuilder {
    String udpIp;
    Integer udpPort;
    Logger logger;
    String charset;
    public FromUdpToFileRouteBuilder(String udpIp,Integer udpPort,Logger logger,String charset){
      super();
      this.udpIp=udpIp;this.udpPort=udpPort;
      this.logger=logger;
      this.charset=charset;
    }
    @Override
    public void configure() throws Exception {
      from("netty4:udp://"+udpIp+":"+udpPort+"?sync=false&udpByteArrayCodec=true&receiveBufferSizePredictor=65535")
          .process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
              byte[] bytes=exchange.getIn().getBody(byte[].class);
              logger.info(new String(bytes, Charset.forName(charset)));
            }
          });
    }
  }
}
