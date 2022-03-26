package io.gateways.userservice.kafkaconfig;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

public interface KafkaService {

	 public Flux<ReceiverRecord<String,String>> getTestTopicFlux();
}
