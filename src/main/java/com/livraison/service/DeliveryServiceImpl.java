package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Delivery;
import com.livraison.entity.Tour;
import com.livraison.entity.enums.StatusLivraison;
import com.livraison.mapper.DeliveryMapper;
import com.livraison.repository.DeliveryRepository;
import com.livraison.repository.TourRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final TourRepository tourRepository;

    @Override
    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll()
                .stream()
                .map(deliveryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryMapper.toEntity(deliveryDTO);

        if (deliveryDTO.getTourId() != null) {
            Tour tour = tourRepository.findById(deliveryDTO.getTourId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Tour not found with id " + deliveryDTO.getTourId()
                    ));
            delivery.setTour(tour);
        } else {
            delivery.setTour(null);
        }

        Delivery saved = deliveryRepository.save(delivery);
        return deliveryMapper.toDTO(saved);
    }

    @Override
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));

        existing.setNameClient(deliveryDTO.getNameClient());
        existing.setAddress(deliveryDTO.getAddress());
        existing.setLatitude(deliveryDTO.getLatitude());
        existing.setLongitude(deliveryDTO.getLongitude());
        existing.setPoids(deliveryDTO.getPoids());
        existing.setVolume(deliveryDTO.getVolume());
        existing.setStatus(deliveryDTO.getStatus());

        if (deliveryDTO.getTourId() != null) {
            Tour tour = tourRepository.findById(deliveryDTO.getTourId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Tour not found with id " + deliveryDTO.getTourId()
                    ));
            existing.setTour(tour);
        } else {
            existing.setTour(null);
        }

        Delivery updated = deliveryRepository.save(existing);
        return deliveryMapper.toDTO(updated);
    }
    @Override
    public DeliveryDTO updateStatus(Long id, StatusLivraison status) {
        Delivery existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));

        existing.setStatus(status);
        return deliveryMapper.toDTO(deliveryRepository.save(existing));
    }


    @Override
    public void deleteDelivery(Long id) {
        Delivery existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));
        deliveryRepository.delete(existing);
    }
    
}
