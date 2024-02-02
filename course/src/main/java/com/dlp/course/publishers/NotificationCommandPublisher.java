package com.dlp.course.publishers;

import com.dlp.course.dtos.NotificationCommandDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationCommandPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${dlp.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

    @Value(value = "${dlp.broker.key.notificationCommandKey}")
    private String notificationCommandKey;

    public void publishNotificationCommand(NotificationCommandDto notificationCommandDto) {
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, notificationCommandDto);
    }
}
