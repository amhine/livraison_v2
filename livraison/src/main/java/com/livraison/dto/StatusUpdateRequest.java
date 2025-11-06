package com.livraison.dto;

import com.livraison.entity.enums.DeliveryStatus;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    private DeliveryStatus status;
}
