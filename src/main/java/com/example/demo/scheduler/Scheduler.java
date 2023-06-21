package com.example.demo.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.orms.Drone;


@Component
public class Scheduler {
	private static final Logger log = LoggerFactory.getLogger(Drone.class.getName());


	@Scheduled(fixedRate = 5000)
	public void reportC() {
		log.info("AN INFO Message");
		log.trace("A TRACE Message");
		log.debug("A DEBUG Message");
	}
}
