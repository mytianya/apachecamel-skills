package vip.codehome.camel.examples.pf4j;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class Pf4jComponent extends DefaultComponent {
    private Pf4jConfiguration configuration;
    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> params) throws Exception {
        System.out.println("create P4fj Endpoint............");
        Pf4jEndpoint endpoint= new Pf4jEndpoint(this, uri,params);
        endpoint.getConfiguration().setPluginId(remaining);
        setProperties(endpoint.getConfiguration(), params);
        setProperties(endpoint, params);
        return endpoint;
    }
}
