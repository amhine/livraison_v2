package com.livraison.mapper;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Delivery;
import com.livraison.entity.Tour;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public DeliveryDTO toDTO(Delivery delivery) {
        if (delivery == null) return null;

        return DeliveryDTO.builder()
                .id(delivery.getId())
                .nameClient(delivery.getNameClient())
                .address(delivery.getAddress())
                .latitude(delivery.getLatitude())
                .longitude(delivery.getLongitude())
                .poids(delivery.getPoids())
                .volume(delivery.getVolume())
                .status(delivery.getStatus())
                .tourId(delivery.getTour() != null ? delivery.getTour().getId() : null)
                .build();
    }

    public Delivery toEntity(DeliveryDTO dto) {
        if (dto == null) return null;

        Delivery delivery = new Delivery();
        delivery.setId(dto.getId());
        delivery.setNameClient(dto.getNameClient());
        delivery.setAddress(dto.getAddress());
        delivery.setLatitude(dto.getLatitude());
        delivery.setLongitude(dto.getLongitude());
        delivery.setPoids(dto.getPoids());
        delivery.setVolume(dto.getVolume());
        delivery.setStatus(dto.getStatus());

        if (dto.getTourId() != null) {
            Tour tour = new Tour();
            tour.setId(dto.getTourId());
            delivery.setTour(tour);
        }

        return delivery;
    }
}
