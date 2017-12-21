package com.xyyh.mqtest.consumer.components;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.xyyh.mqtest.model.TModel;


@Component
public class Consumer {
	@JmsListener(destination = "queue1")
	public void comsume(TModel content) {
		System.out.println("recive message from queue1 [" + content + "]");
	}
}
