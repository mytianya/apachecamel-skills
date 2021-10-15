package vip.codehome.camel.examples.custom;

import java.util.Map;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * @author dsys
 * @version v1.0
 **/
public class CustomFileComponet extends DefaultComponent {

  @Override
  protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters)
      throws Exception {
    return new MyFileEndpoint(this,uri);
  }
}
