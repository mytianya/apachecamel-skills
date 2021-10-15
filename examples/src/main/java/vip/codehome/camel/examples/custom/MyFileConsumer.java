package vip.codehome.camel.examples.custom;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

/**
 * @author dsys
 * @version v1.0
 **/
public class MyFileConsumer extends DefaultConsumer implements Runnable {
  private ScheduledExecutorService executorService= Executors.newScheduledThreadPool(10);
  private File pollDir;
  public MyFileConsumer(Endpoint endpoint, Processor processor) {
    super(endpoint, processor);
    String pollDir=endpoint.getEndpointUri().substring(endpoint.getEndpointUri().indexOf(":")+1);
    this.pollDir=new File(pollDir);
  }

  @Override
  protected void doStart() throws Exception {
    super.doStart();
    executorService.scheduleAtFixedRate(this,1000L,100L, TimeUnit.MILLISECONDS);
  }

  @Override
  public void run() {
    File[] files=pollDir.listFiles();
    for(File file:files){
      MyFileEndpoint endpoint=(MyFileEndpoint) getEndpoint();
      Exchange exchange=endpoint.createExchange(file);
      try{
        this.getProcessor().process(exchange);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
  }
}
