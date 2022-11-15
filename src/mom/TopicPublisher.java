package mom;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class TopicPublisher {
    public static void main(String[] args) {
        String topicName = null;
        Context jndiContext = null;
        TopicConnectionFactory topicConnectionFactory = null;
        TopicConnection topicConnection = null;
        TopicSession topicSession = null;
        Topic topic = null;
        javax.jms.TopicPublisher topicPublisher = null;
        TextMessage message = null;
        final int NUM_MSGS = 0;
        final String MSG_TEXT = "Текст сообщения";
        Properties env = new Properties();
        topicName = "Test Topic";
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
            topicConnectionFactory = (TopicConnectionFactory)jndiContext.lookup("TopicConnectionFactory");
            topic = (Topic) jndiContext.lookup(topicName);
        }
        catch (NamingException e)
        {
            System.out.println(e.toString());
            System.exit(1);
        }
        try
        {
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topicPublisher = topicSession.createPublisher(topic);
            message = topicSession.createTextMessage();
            for (int i = 0; i < NUM_MSGS; i++)
            {
                message.setText(MSG_TEXT + " " + (i + 1));
                System.out.println("Сообщение: " + message.getText());
                topicPublisher.publish(message);
            }
        }
        catch (JMSException e)
        {
            System.out.println(e.toString());
        }
    }
}
