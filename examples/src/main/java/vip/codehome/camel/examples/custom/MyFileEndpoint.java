package vip.codehome.camel.examples.custom;

import java.io.File;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.component.file.FileComponent;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;

/**
 * @author dsys
 * @version v1.0
 * 如果一个组件是一个起始组件，或称为源组件，那么对应的Endpoint就必须要能创建出一个Consumer对象，
 * 如果这个组件还想是目的组件，那么对应的Endpoint还必须能创建出Producer对象。Consumer负责Exchange对象的产生，而Producer负责将Exchange对象中的消费。

 **/
public class MyFileEndpoint extends DefaultEndpoint {
  public MyFileEndpoint(CustomFileComponet componet,String uri){
    super(uri,componet);
  }
  @Override
  public Producer createProducer() throws Exception {
    return null;
  }

  @Override
  public Consumer createConsumer(Processor processor) throws Exception {
    return new MyFileConsumer(this,processor);
  }

  @Override
  public boolean isSingleton() {
    return false;
  }
  public Exchange createExchange(File file){
    Exchange exchange=new DefaultExchange(getCamelContext());
    exchange.setProperty(FileComponent.FILE_EXCHANGE_FILE,file);
    exchange.getIn().setBody(file,File.class);
    return exchange;
  }
}
