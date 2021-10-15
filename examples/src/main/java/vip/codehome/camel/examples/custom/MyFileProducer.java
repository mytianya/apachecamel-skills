package vip.codehome.camel.examples.custom;

import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

/**
 * @author dsys
 * @version v1.0
 **/
public class MyFileProducer extends DefaultProducer {
  private File outputDir;

  public MyFileProducer(Endpoint endpoint) {
    super(endpoint);
    this.outputDir=new File(endpoint.getEndpointUri().substring(endpoint.getEndpointUri().indexOf(":")+1));
  }

  @Override
  public void process(Exchange exchange) throws Exception {
    File file=exchange.getIn().getBody(File.class);
    if(file!=null){
      Files.move(file.toPath(),outputDir.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
  }
}
