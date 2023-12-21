package com.dlp.notificationhex.adapters.inbound.consumers;

import com.dlp.notificationhex.adapters.dtos.NotificationCommandDto;
import com.dlp.notificationhex.core.domain.NotificationDomain;
import com.dlp.notificationhex.core.domain.enums.NotificationStatus;
import com.dlp.notificationhex.core.ports.NotificationServicePort;
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

    final NotificationServicePort notificationServicePort;

    public NotificationConsumer(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
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

        var notificationDomain = new NotificationDomain();
        BeanUtils.copyProperties(notificationCommandDto, notificationDomain);
        notificationDomain.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationDomain.setNotificationStatus(NotificationStatus.CREATED);

        notificationServicePort.saveNotification(notificationDomain);
    }
}
