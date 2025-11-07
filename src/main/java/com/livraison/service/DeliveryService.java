package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.enums.DeliveryStatus;

import java.util.List;

public interface DeliveryService {

    List<DeliveryDTO> getAllDeliveries();
    DeliveryDTO getDeliveryById(Long id);
    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);
    DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO);
    DeliveryDTO updateStatus(Long id, DeliveryStatus status);
    void deleteDelivery(Long id);
}
