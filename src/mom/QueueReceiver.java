package mom;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class QueueReceiver {
    public static void main(String[] args) {
        String queueName = null;
        Context jndiContext = null;
        QueueConnectionFactory queueConnectionFactory = null;
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        Queue queue = null;
        javax.jms.QueueReceiver queueReceiver = null;
        TextMessage message = null;
        Properties env = new Properties();
        queueName = "Test Queue";
        try
        {
            jndiContext = new InitialContext(env);
        }
        catch (NamingException e)
        {
            System.out.println(e.toString());
            System.exit(1);
        }
        try
        {
            queueConnectionFactory = (QueueConnectionFactory)jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue) jndiContext.lookup(queueName);
        }
        catch (NamingException e)
        {
            System.out.println(e.toString());
            System.exit(1);
        }
        try
        {
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueReceiver = queueSession.createReceiver(queue);
            queueConnection.start();
            while (true) {
                Message m = queueReceiver.receive(1);
                if (m != null) {
                    if (m instanceof TextMessage) {
                        message = (TextMessage) m;
                        System.out.println("Сообщение: " +
                                message.getText());
                    }
                }
            }
        }
        catch (JMSException e)
        {
            System.out.println(e.toString());
        }

    }
}
