package vip.codehome.camel.examples.custom;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author dsys
 * @version v1.0
 **/
public class MyFileComponentDemo {

  public static void main(String[] args) throws Exception {
    CamelContext camelContext=new DefaultCamelContext();
    //替代了在类路径META-INF/services/org/apache/camel/component下放置properties文件方式
    camelContext.addComponent("myfile",new CustomFileComponet());
    camelContext.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
           this.from("myfile:H:/temp/in").process(new Processor() {
             @Override
             public void process(Exchange exchange) throws Exception {

             }
           }).to("myfile:D:/temp/out");
      }
    });
    camelContext.start();
    Object object=new Object();
    synchronized (object){
      object.wait();
    }
  }
}
