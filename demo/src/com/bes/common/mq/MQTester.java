package com.bes.common.mq;

import java.util.Calendar;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class MQTester {
	private static final String URL = "tcp://192.168.40.6:61616";
	private static final String QUEUE_NAME = "choice.queue";
	private static final String TOPIC_NAME = "choice.topic";

	private volatile boolean start = true;
	private Object lock = new Object();
	
	@Test
	public void sendQueueMessage() throws JMSException {
		Connection connection = null;
		try {
			// 1.���MQ�����ӹ���
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
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
	public void receiveQueueMessage() throws JMSException {
		Connection connection = null;
		try {
			// 1.���MQ�����ӹ���
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
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
			MessageConsumer consumer = session.createConsumer(dest);
			// 6.��Ϣ�����߽�����Ϣ,��������ҵ��Ĵ���
			//Message message = consumer.receive();
			//System.out.println(message);
			consumer.setMessageListener(new QueueMessageListener());
			
			while(start){
				waitingShutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private void waitingShutdown(){
		synchronized (lock) {
			if(start){
				try {
					lock.wait(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				start=!start;
			}
		}
	}
	
	@Test
	public void setTopicMessage() throws JMSException {
		Connection connection = null;
		try {
			// 1.���MQ�����ӹ���
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					URL);
			// 2.ͨ�����ӹ������MQ������ʵ��
			connection = connectionFactory.createConnection();
			connection.start();
			// 3.ͨ��MQ������ʵ�����session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// 4.�����ϢĿ��
			Destination dest = session.createTopic(TOPIC_NAME);
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
	public void receiveTopicMessage() throws JMSException {
		Connection connection = null;
		try {
			// 1.���MQ�����ӹ���
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					URL);
			// 2.ͨ�����ӹ������MQ������ʵ��
			connection = connectionFactory.createConnection();
			connection.start();
			// 3.ͨ��MQ������ʵ�����session
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			// 4.�����ϢĿ��
			Destination dest = session.createTopic(TOPIC_NAME);
			// 5.����MQ��Ϣ������
			MessageConsumer consumer = session.createConsumer(dest);
			// 6.��Ϣ�����߽�����Ϣ,��������ҵ��Ĵ���
			Message message = consumer.receive();
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int num = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(num);
	}
}
