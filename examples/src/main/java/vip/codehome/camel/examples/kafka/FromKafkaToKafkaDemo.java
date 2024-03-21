package vip.codehome.camel.examples.kafka;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * 从kafka接收到发送到kafka队列
 */
public class FromKafkaToKafkaDemo {
    public static void main(String[] args) throws Exception {
        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new FromKafkaToKafkaDemoRouteBuilder());
        context.start();
        while (true){
            Thread.sleep(10000);
        }
    }

    public static class FromKafkaToKafkaDemoRouteBuilder extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("kafka:CUSTOM_EVENT?brokers=192.28.4.23:9092&serializerClass=org.apache.kafka.common.serialization.ByteArraySerializer&circularTopicDetection=false")
                    .to("kafka:CUSTOM_EVENT?brokers=192.28.7.3:9092&maxPollRecords=1&autoOffsetReset=latest&bridgeErrorHandler=true&groupId=ha-test" +
                            "&valueDeserializer=org.apache.kafka.common.serialization.ByteArrayDeserializer"+
                            "autoCommitEnable=true&circularTopicDetection=false");
        }
    }
}
