package com.weatherize.mynest.live.streamprocessor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


@SpringBootApplication
public class StreamprocessorApplication implements CommandLineRunner {

	public static Logger logger = LoggerFactory.getLogger(StreamprocessorApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(StreamprocessorApplication.class, args);
		
	} 

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);
    
    @Bean
    public MessageChannel pubsubInputChannel() {
  	return new DirectChannel();
    }
    
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
  	  @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
  	  PubSubTemplate pubSubTemplate) {
  	PubSubInboundChannelAdapter adapter =
  		new PubSubInboundChannelAdapter(pubSubTemplate, "NestHVACEventSubscription");
  	adapter.setOutputChannel(inputChannel);
  	adapter.setAckMode(AckMode.MANUAL);
  	logger.info("Inbound Channel Adapter is initialized");

  	return adapter;
    }
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
  	return message -> {
  		logger.info("Message arrived! Payload: " + new String((byte[]) message.getPayload()));
  	  BasicAcknowledgeablePubsubMessage originalMessage =
  		message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
  	  originalMessage.ack();
  	};
    }
    
    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
  	return new PubSubMessageHandler(pubsubTemplate, "NestHVACEventTopic");
    }
        

    @Override
    public void run(String... args) throws Exception {
        this.template.send("myTopic", "foo11");
        this.template.send("myTopic", "foo12");
        this.template.send("myTopic", "foo13");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    @KafkaListener(topics = "myTopic")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        latch.countDown();
    }
}
