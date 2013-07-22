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
			// 1.���MQ�����ӹ���
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					URL);
			// 2.ͨ�����ӹ������MQ������ʵ��
			connection = connectionFactory.createConnection();
			connection.start();
			// 3.ͨ��MQ������ʵ�����session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// 4.�����ϢĿ��
			Destination dest = session.createQueue(QUEUE_NAME);
			// 5.����MQ��Ϣ������
			MessageProducer producer = session.createProducer(dest);
			// 6.������Ϣ,�������߷�����Ϣ
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
