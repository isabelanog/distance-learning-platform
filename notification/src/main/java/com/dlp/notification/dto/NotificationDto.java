package com.dlp.notification.dto;

import com.dlp.notification.enums.NotificationStatus;

import javax.validation.constraints.NotNull;

public class NotificationDto {
    @NotNull(message = "Notification status must not be null")
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
