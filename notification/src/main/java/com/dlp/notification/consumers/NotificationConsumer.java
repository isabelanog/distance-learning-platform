package com.dlp.notification.consumers;

import com.dlp.notification.dto.NotificationCommandDto;
import com.dlp.notification.enums.NotificationStatus;
import com.dlp.notification.models.NotificationModel;
import com.dlp.notification.services.NotificationService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class NotificationConsumer {

    final NotificationService notificationService;

    public NotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(messageConverter = "jsonConverter",
            bindings = @QueueBinding(
            value = @Queue(value = "${dlp.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${dlp.broker.exchange.notificationCommandExchange}",
                    type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${dlp.broker.key.notificationCommandKey}")
    )
    public void listenNotificationCommand(@Payload NotificationCommandDto notificationCommandDto) {

        var notificationModel = new NotificationModel();
        BeanUtils.copyProperties(notificationCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);

        notificationService.saveNotification(notificationModel);
    }
}
