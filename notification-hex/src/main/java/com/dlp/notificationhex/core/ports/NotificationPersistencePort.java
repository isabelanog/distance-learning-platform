package com.dlp.notificationhex.core.ports;

import com.dlp.notificationhex.core.domain.NotificationDomain;
import com.dlp.notificationhex.core.domain.PageInfo;
import com.dlp.notificationhex.core.domain.enums.NotificationStatus;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
public interface NotificationPersistencePort {

    NotificationDomain saveNotification(NotificationDomain notificationDomain);
    List<NotificationDomain> getNotificationsByUserIdAndStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo);
    Optional<NotificationDomain> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
