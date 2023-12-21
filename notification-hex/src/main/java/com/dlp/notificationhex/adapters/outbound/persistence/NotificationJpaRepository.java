package com.dlp.notificationhex.adapters.outbound.persistence;

import com.dlp.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.dlp.notificationhex.core.domain.enums.NotificationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, UUID> {

    Page<NotificationEntity> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, Pageable pageable);
    Optional<NotificationEntity> findNotificationModelByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
