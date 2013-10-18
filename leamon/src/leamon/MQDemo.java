package leamon;


public class MQDemo {
/*
	public static void sendMessage() {
		ConnectionFactory connectionFactory = null;

		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD,
				"tcp://192.168.40.6:61616");
		Connection connection = null;
		try {
			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination1 = session.createQueue("zuchunlei");
			Destination destination2 = session.createQueue("ZUCHUNLEI");
			Destination destination3 = session.createQueue("ZuchunlEI");

			MessageProducer producer1 = session.createProducer(destination1);
			MessageProducer producer2 = session.createProducer(destination2);
			MessageProducer producer3 = session.createProducer(destination3);

			TextMessage message = session.createTextMessage();
			message.setText("zuchunlei");

			producer1.send(message);
			producer2.send(message);
			producer3.send(message);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			sendMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
