package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.dto.DeliveryHistoryDTO;

import java.util.List;

public interface DeliveryHistoryService {
    DeliveryHistoryDTO create(DeliveryHistoryDTO deliveryHistoryDto);
    DeliveryHistoryDTO getHistoryByDeliveryId(Long id);
    List<DeliveryHistoryDTO> getAll();
    void deleteCustomer(Long id);
}
