package com.ibm.avro.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ibm.avro.model.CustomObject;

@Service
public class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private KafkaTemplate<Long, CustomObject> kafkaTemplate;

	public void sendMessage(String TOPIC, CustomObject customObject) {
		this.kafkaTemplate.send(TOPIC, customObject);
	}
}