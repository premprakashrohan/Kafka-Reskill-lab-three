package com.ibm.avro.V1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ibm.avro.V1.model.CustomObject;


@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(id = "1", topics = "#{'${spring.kafka.topics}'}", groupId = "group_id",properties= {"schema.registry.url= http://localhost:8081"})
	public void consume(CustomObject customObject) {
		logger.info(String.format("$$ -> V1 Consumed Message -> %s", customObject));
	}
}