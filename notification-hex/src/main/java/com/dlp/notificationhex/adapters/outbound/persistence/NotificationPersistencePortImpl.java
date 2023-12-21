package com.dlp.notificationhex.adapters.outbound.persistence;

import com.dlp.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.dlp.notificationhex.core.domain.NotificationDomain;
import com.dlp.notificationhex.core.domain.PageInfo;
import com.dlp.notificationhex.core.domain.enums.NotificationStatus;
import com.dlp.notificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        NotificationEntity notificationEntity = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> getNotificationsByUserIdAndStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());

        return notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, notificationStatus, pageable).stream()
                .map(notificationEntity -> modelMapper.map(notificationEntity, NotificationDomain.class)).collect(Collectors.toList());

    }

    @Override
    public Optional<NotificationDomain> getNotificationByNotificationIdAndUserId(UUID notificationId, UUID userId) {

        Optional<NotificationEntity> notificationEntity = notificationJpaRepository.findNotificationModelByNotificationIdAndUserId(notificationId, userId);

        if (notificationEntity.isPresent()) {
            return Optional.of(modelMapper.map(notificationEntity.get(), NotificationDomain.class));
        }
        return Optional.empty();
    }
}
