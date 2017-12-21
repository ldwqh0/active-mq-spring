package com.xyyh.mqtest.producer.conponents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xyyh.mqtest.model.TModel;


@Component
public class Producer {
	private final JmsTemplate jmsTemplate;
	private int count = 0;

	@Autowired
	public Producer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Scheduled(fixedRate = 1000)
	public void create() {
		jmsTemplate.convertAndSend("queue1", new TModel(count++));
	}
}
