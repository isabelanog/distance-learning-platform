package com.dlp.notification.services;

import com.dlp.notification.models.NotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    NotificationModel saveNotification(NotificationModel notificationModel);

    Page<NotificationModel> getNotificationsCreatedByUserId(UUID userId, Pageable pageable);
    Optional<NotificationModel> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
