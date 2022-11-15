package mom;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class QueueSender {
    public static void main(String[] args) {
        String queueName = null;
        Context jndiContext = null;
        QueueConnectionFactory queueConnectionFactory = null;
        QueueConnection queueConnection = null;
        QueueSession queueSession = null;
        Queue queue = null;
        javax.jms.QueueSender queueSender = null;
        TextMessage message = null;
        final int NUM_MSGS = 0;
        final String MSG_TEXT = new String("Текст сообщения");
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
            queueConnectionFactory = (QueueConnectionFactory)
                    jndiContext.lookup("QueueConnectionFactory");
            queue = (Queue)jndiContext.lookup(queueName);
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
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText(MSG_TEXT + " " + (i + 1));
                System.out.println("Отправленное сообщение: " + message.getText());
                queueSender.send(message);
            }
        }
        catch (JMSException e)
        {
            System.out.println(e.toString());
        }
    }
}