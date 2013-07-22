package com.bes.common.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class MQTester {
	private static final String URL = "tcp://192.168.40.6:61616";
	private static final String QUEUE_NAME = "choice.queue";

	@Test
	public void sendQueueMessage() throws JMSException {
		Connection connection = null;
		try {
			// 1.获得MQ的链接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					URL);
			// 2.通过链接工厂获得MQ的链接实例
			connection = connectionFactory.createConnection();
			connection.start();
			// 3.通过MQ的链接实例获得session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// 4.获得消息目标
			Destination dest = session.createQueue(QUEUE_NAME);
			// 5.创建MQ消息生产者
			MessageProducer producer = session.createProducer(dest);
			// 6.构造消息,由生产者发送消息
			TextMessage message = session.createTextMessage();
			message.setStringProperty("name", "zuchunlei");
			producer.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	@Test
	public void setTopicMessage(){
		
	}
	
}
