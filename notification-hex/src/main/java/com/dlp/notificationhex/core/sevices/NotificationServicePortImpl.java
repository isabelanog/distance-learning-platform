package com.dlp.notificationhex.core.sevices;

import com.dlp.notificationhex.core.domain.NotificationDomain;
import com.dlp.notificationhex.core.domain.PageInfo;
import com.dlp.notificationhex.core.domain.enums.NotificationStatus;
import com.dlp.notificationhex.core.ports.NotificationPersistencePort;
import com.dlp.notificationhex.core.ports.NotificationServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {
    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationModel) {
        return notificationPersistencePort.saveNotification(notificationModel);
    }

    @Override
    public List<NotificationDomain> getNotificationsByUserId(UUID userId, PageInfo pageInfo) {
        return notificationPersistencePort.getNotificationsByUserIdAndStatus(userId, NotificationStatus.CREATED, pageInfo);
    }

    @Override
    public Optional<NotificationDomain> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationPersistencePort.getNotificationByNotificationIdAndUserId(notificationId, userId);
    }
}
