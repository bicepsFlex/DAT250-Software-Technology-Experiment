package Subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
		     Channel channel = connection.createChannel()) {
			channel.exchangeDeclare("logs", "fanout");
			
			String message = argv.length < 1 ? "info: Hello World!" :
                            String.join(" ", argv);
			channel.basicPublish( "logs", "", null, message.getBytes());
			
			System.out.println(" [x] Sent '" + message + "'");

		}
	  }
}
