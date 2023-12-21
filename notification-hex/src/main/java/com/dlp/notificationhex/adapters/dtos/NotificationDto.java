package com.dlp.notificationhex.adapters.dtos;


import com.dlp.notificationhex.core.domain.enums.NotificationStatus;

import javax.validation.constraints.NotNull;

public class NotificationDto {
    @NotNull(message = "Status of notification must not be null")
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
