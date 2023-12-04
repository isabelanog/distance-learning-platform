package com.dlp.notification.repositories;

import com.dlp.notification.enums.NotificationStatus;
import com.dlp.notification.models.NotificationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, UUID> {

    Page<NotificationModel> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, Pageable pageable);
    Optional<NotificationModel> findNotificationModelByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
