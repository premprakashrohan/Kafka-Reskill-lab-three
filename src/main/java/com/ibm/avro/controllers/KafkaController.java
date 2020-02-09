package com.ibm.avro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.avro.model.CustomObject;
import com.ibm.avro.services.Producer;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {
	private final Producer producer;

	@Autowired
	public KafkaController(Producer producer) {
		this.producer = producer;
	}

	@PostMapping(value = "/publish", consumes = "application/json")
	public void sendMessageToKafkaTopic(@RequestBody CustomObject customObject, @RequestParam("topic") String topic) {
		this.producer.sendMessage(topic, customObject);
	}

}

