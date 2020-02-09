package com.ibm.avro.V2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ibm.avro.V2.model.CustomObject;

public @Service class Producer {
	@Autowired
	private KafkaTemplate<Long, CustomObject> kafkaTemplate;

	public void sendMessage(String TOPIC, CustomObject customObject) {
		this.kafkaTemplate.send(TOPIC, customObject);
	}
}