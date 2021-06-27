package edu.sd.ms.backing.messaging.rabbitmq;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class HelloRabbitMQClient {
	
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello RabbitMQClient");
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			System.out.println("Waiting for messages. To exit, press CTRL+C");
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println("Received: '" + message + "'.");
			};
			channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
