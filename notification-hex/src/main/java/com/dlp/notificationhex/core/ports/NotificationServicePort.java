package com.dlp.notificationhex.core.ports;

import com.dlp.notificationhex.core.domain.NotificationDomain;
import com.dlp.notificationhex.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {
    NotificationDomain saveNotification(NotificationDomain notificationModel);

    List<NotificationDomain> getNotificationsByUserId(UUID userId, PageInfo pageInfo);

    Optional<NotificationDomain> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId);

}
