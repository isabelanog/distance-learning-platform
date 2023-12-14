package com.dlp.notification.controller;

import com.dlp.notification.dto.NotificationDto;
import com.dlp.notification.models.NotificationModel;
import com.dlp.notification.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {
    final NotificationService notificationService;

    public UserNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationModel>> getNotificationsReadByUserId(@PathVariable(value = "userId") UUID userId,
                                                                                @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                                Authentication authentication) {

        return ResponseEntity.status(HttpStatus.OK).body(notificationService.getNotificationsCreatedByUserId(userId, pageable));
    }
    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value = "userId") UUID userId,
                                                     @PathVariable(value = "notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {

        Optional<NotificationModel> notification = notificationService.getNotificationByNotificationIdAndUserId(notificationId, userId);

        if (notification.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found");
        }
        notification.get().setNotificationStatus(notificationDto.getNotificationStatus());

        notificationService.saveNotification(notification.get());

        return ResponseEntity.status(HttpStatus.OK).body(notification.get());
    }
}