package com.ibm.avro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.avro.model.CustomObject;
@EnableKafka
@SpringBootApplication
public class Application  {
	 
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


}
@Service
class Producer {
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);

	@Autowired
	private KafkaTemplate<Long, CustomObject> kafkaTemplate;

	public void sendMessage(String TOPIC, CustomObject customObject) {
		this.kafkaTemplate.send(TOPIC, customObject);
	}
}

@RestController
@RequestMapping(value = "/kafka")
class KafkaController {
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

@Service
class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);

	@KafkaListener(id = "0", topics = "#{'${spring.kafka.topics}'}", groupId = "group_id",properties= {"schema.registry.url= http://localhost:8081"})
	public void consume(CustomObject customObject) {

		logger.info(String.format("$$ -> Consumed Message -> %s", customObject));

	}
}
@Service
class ConsumerV1 {
	private final Logger logger = LoggerFactory.getLogger(ConsumerV1.class);

	@KafkaListener(id = "1", topics = "#{'${spring.kafka.topics}'}", groupId = "group_id",properties= {"schema.registry.url= http://localhost:8081"})
	public void consume(com.ibm.avro.model.V1.CustomObject customObject) {

		logger.info(String.format("$$ -> V1 Consumed Message -> %s", customObject));
	}
}

@Service
class ConsumerV2 {
	private final Logger logger = LoggerFactory.getLogger(ConsumerV2.class);

	@KafkaListener(id = "2", topics = "#{'${spring.kafka.topics}'}", groupId = "group_id", properties= {"schema.registry.url= http://localhost:8081"})
	public void consume(com.ibm.avro.model.V2.CustomObject customObject) {

		logger.info(String.format("$$ -> V2 Consumed Message -> %s", customObject));

	}
}