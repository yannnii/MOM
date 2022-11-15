package mom;

import javax.jms.*;
import javax.naming.*;
import java.util.Properties;

public class TopicSubscriber {
    public static void main(String[] args) {
        String topicName = null;
        Context jndiContext = null;
        TopicConnectionFactory topicConnectionFactory = null;
        TopicConnection topicConnection = null;
        TopicSession topicSession = null;
        Topic topic = null;
        javax.jms.TopicSubscriber topicSubscriber = null;
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
            topicSubscriber = topicSession.createSubscriber(topic);
            TextListener topicListener = new TextListener();
            topicSubscriber.setMessageListener(topicListener);
            topicConnection.start();
        }
        catch (JMSException e)
        {
            e.printStackTrace();
        }
    }
}
