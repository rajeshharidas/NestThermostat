package com.weatherize.mynest.live.streamprocessor.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.AcknowledgeablePubsubMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.google.cloud.pubsub.v1.Subscriber;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.weatherize.mynest.live.streamprocessor.StreamprocessorApplication;
import com.weatherize.mynest.live.streamprocessor.model.TemperatureData;
import com.weatherize.mynest.live.streamprocessor.util.CVSFilesfromAWSS3;

@RestController
public class NestStreamProcessorController {


		private static final Logger LOGGER = LoggerFactory.getLogger(StreamprocessorApplication.class);

		private final PubSubTemplate pubSubTemplate;

		private final ArrayList<Subscriber> allSubscribers;
		
		@Autowired
		private KafkaTemplate<String, String> template;


		public NestStreamProcessorController(PubSubTemplate pubSubTemplate) {
			this.pubSubTemplate = pubSubTemplate;
			this.allSubscribers = new ArrayList<>();
		}


		@GetMapping("/postMessage")
		public RedirectView publish(@RequestParam("topicName") String topicName,
				@RequestParam("message") String message, @RequestParam("count") int messageCount) {
			for (int i = 0; i < messageCount; i++) {
				this.pubSubTemplate.publish(topicName, message);
			}

			return buildStatusView("Messages published asynchronously; status unknown.");
		}

		@GetMapping("/pull")
		public RedirectView pull(@RequestParam("subscription1") String subscriptionName) {

			Collection<AcknowledgeablePubsubMessage> messages = this.pubSubTemplate.pull(subscriptionName, 10, true);

			if (messages.isEmpty()) {
				return buildStatusView("No messages available for retrieval.");
			}

			RedirectView returnView;
			try {
				ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(messages);
				ackFuture.get();
				returnView = buildStatusView(String.format("Pulled and acked %s message(s)", messages.size()));
			}
			catch (Exception ex) {
				LOGGER.warn("Acking failed.", ex);
				returnView = buildStatusView("Acking failed");
			}

			return returnView;
		}

		@GetMapping("/multipull")
		public RedirectView multipull(
				@RequestParam("subscription1") String subscriptionName1,
				@RequestParam("subscription2") String subscriptionName2) {

			Set<AcknowledgeablePubsubMessage> mixedSubscriptionMessages = new HashSet<>();
			mixedSubscriptionMessages.addAll(this.pubSubTemplate.pull(subscriptionName1, 1000, true));
			mixedSubscriptionMessages.addAll(this.pubSubTemplate.pull(subscriptionName2, 1000, true));

			if (mixedSubscriptionMessages.isEmpty()) {
				return buildStatusView("No messages available for retrieval.");
			}

			RedirectView returnView;
			try {
				ListenableFuture<Void> ackFuture = this.pubSubTemplate.ack(mixedSubscriptionMessages);
				ackFuture.get();
				returnView = buildStatusView(
						String.format("Pulled and acked %s message(s)", mixedSubscriptionMessages.size()));
			}
			catch (Exception ex) {
				LOGGER.warn("Acking failed.", ex);
				returnView = buildStatusView("Acking failed");
			}

			return returnView;
		}

		@GetMapping("/subscribe")
		public RedirectView subscribe(@RequestParam("subscription") String subscriptionName) {
			Subscriber subscriber = this.pubSubTemplate.subscribe(subscriptionName, (message) -> {

				String json = message.getPubsubMessage().getData().toStringUtf8();

				LOGGER.info("Message received from " + subscriptionName + " subscription: "
						+ json);
				
				JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
				if (convertedObject.isJsonObject()) {
					//Transform into another payload
					this.template.send("myNestTopic", json);
				}
				
				message.ack();
			});

			this.allSubscribers.add(subscriber);
			return buildStatusView("Subscribed.");
		}

		/*
		@GetMapping("/loadfroms3")
		public RedirectView loadfroms3() {
			
			CVSFilesfromAWSS3 csvFilefromAWSS3 = new CVSFilesfromAWSS3();
			List<TemperatureData> data = csvFilefromAWSS3.GetTemperatureDatafromS3();
			
			data.forEach(item -> {
				
				if (item != null) {
				JsonObject json = new JsonObject();
				json.addProperty("timeofcapture",DateFormat.getInstance().format(item.getTimeofcapture()));
				json.addProperty("temperature",item.getTemperature());
				json.addProperty("humidity",item.getHumidity());
				json.addProperty("timetotarget",item.getTimetotarget());
				json.addProperty("mode",item.getMode());
				json.addProperty("hvaccycleon",false);
				
				this.template.send("myNestTopic", json.toString());
				}
			});
						
			
			return buildStatusView("Load complete.");
		}
		*/
		
		private RedirectView buildStatusView(String statusMessage) {
			RedirectView view = new RedirectView("/");
			view.addStaticAttribute("statusMessage", statusMessage);
			return view;
		}
	}