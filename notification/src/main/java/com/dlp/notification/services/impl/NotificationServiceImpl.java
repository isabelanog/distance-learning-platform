package com.dlp.notification.services.impl;

import com.dlp.notification.enums.NotificationStatus;
import com.dlp.notification.models.NotificationModel;
import com.dlp.notification.repositories.NotificationRepository;
import com.dlp.notification.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {
    final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationModel saveNotification(NotificationModel notificationModel) {
        return notificationRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> getNotificationsCreatedByUserId(UUID userId, Pageable pageable) {
        return notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageable);
    }

    @Override
    public Optional<NotificationModel> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationRepository.findNotificationModelByNotificationIdAndUserId(notificationId, userId);
    }
}
