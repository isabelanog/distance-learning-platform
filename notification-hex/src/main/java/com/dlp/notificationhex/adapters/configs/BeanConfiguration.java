package com.dlp.notificationhex.adapters.configs;

import com.dlp.notificationhex.NotificationHexApplication;
import com.dlp.notificationhex.core.ports.NotificationPersistencePort;
import com.dlp.notificationhex.core.sevices.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexApplication.class)
public class BeanConfiguration {

   @Bean
   NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
       return new NotificationServicePortImpl(notificationPersistencePort);
   }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
