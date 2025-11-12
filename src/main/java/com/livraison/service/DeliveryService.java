package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.enums.StatusLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeliveryService {

    List<DeliveryDTO> getAllDeliveries();
    DeliveryDTO getDeliveryById(Long id);
    DeliveryDTO createDelivery(DeliveryDTO deliveryDTO);
    DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO);
    DeliveryDTO updateStatus(Long id, StatusLivraison status);
    void deleteDelivery(Long id);

    Page<DeliveryDTO> getDeliveries(Pageable pageable);
    Page<DeliveryDTO> searchByName(String name, Pageable pageable);
    Page<DeliveryDTO> findByStatus(StatusLivraison status, Pageable pageable);
    Page<DeliveryDTO> searchByGeo(double minLat, double maxLat, double minLon, double maxLon, Pageable pageable);
}
