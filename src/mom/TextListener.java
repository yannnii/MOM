package mom;

import javax.jms.*;
public class TextListener implements MessageListener {
    public void onMessage(Message message) {
        TextMessage msg = null;
        try
        {
            if (message instanceof TextMessage)
            {
                msg = (TextMessage) message;
                System.out.println("Прочитанное сообщение: " + msg.getText());
            }
            else
            {
                System.out.println(message.getClass().getName());
            }
        }
        catch (JMSException e)
        {
            System.out.println(e.toString());
        }
    }
}
