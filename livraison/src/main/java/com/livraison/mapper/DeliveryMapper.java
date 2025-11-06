package com.livraison.mapper;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Customer;
import com.livraison.entity.Tour;

public class DeliveryMapper {

    public DeliveryDTO toDTO(Customer delivery) {
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

    public Customer toEntity(DeliveryDTO dto) {
        if (dto == null) return null;

        Customer delivery = new Customer();
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
