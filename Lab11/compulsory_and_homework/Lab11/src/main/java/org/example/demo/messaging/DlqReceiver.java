package org.example.demo.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DlqReceiver {
    @RabbitListener(queues = RabbitMQConfig.DLQ_QUEUE)
    public void processFailedMessage(GradeMessage failedMessage) {
        System.out.println("!!! [DLQ] Received FAILED message: " + failedMessage);
    }
}