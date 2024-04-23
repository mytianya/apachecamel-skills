package vip.codehome.camel.examples.pf4j;

import lombok.Data;
import org.apache.camel.spi.*;

@UriParams
@Data
public class Pf4jConfiguration implements Cloneable {
    @UriPath(label = "common")
    private String pluginId;
    @UriParam(label = "common")
    private Float version;

}
