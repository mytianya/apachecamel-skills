package vip.codehome.camel.examples.core;

import lombok.Data;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;

import java.util.Collections;

@Data
public  class CommonRouteBuilder extends RouteBuilder {
    String id;
    String fromUrl;
    String toUrl;
    Processor processor;
    @Override
    public void configure() throws Exception {
        RouteDefinition routeDefinition=new RouteDefinition();
        routeDefinition.setId(id);
        routeDefinition.from(fromUrl).process(processor).to(toUrl);
        RoutesDefinition routesDefinition=new RoutesDefinition();
        routesDefinition.setRoutes(Collections.singletonList(routeDefinition));
        this.setRouteCollection(routesDefinition);


//        from("")
//            .to("");
    }
}