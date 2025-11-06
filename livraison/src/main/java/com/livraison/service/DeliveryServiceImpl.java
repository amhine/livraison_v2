package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Customer;
import com.livraison.entity.Tour;
import com.livraison.entity.enums.DeliveryStatus;
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
        Customer delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));
        return deliveryMapper.toDTO(delivery);
    }

    @Override
    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Customer delivery = deliveryMapper.toEntity(deliveryDTO);

        if (deliveryDTO.getTourId() != null) {
            Tour tour = tourRepository.findById(deliveryDTO.getTourId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Tour not found with id " + deliveryDTO.getTourId()
                    ));
            delivery.setTour(tour);
        } else {
            delivery.setTour(null);
        }

        Customer saved = deliveryRepository.save(delivery);
        return deliveryMapper.toDTO(saved);
    }

    @Override
    public DeliveryDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Customer existing = deliveryRepository.findById(id)
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

        Customer updated = deliveryRepository.save(existing);
        return deliveryMapper.toDTO(updated);
    }
    @Override
    public DeliveryDTO updateStatus(Long id, DeliveryStatus status) {
        Customer existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));

        existing.setStatus(status);
        return deliveryMapper.toDTO(deliveryRepository.save(existing));
    }


    @Override
    public void deleteDelivery(Long id) {
        Customer existing = deliveryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Delivery not found with id " + id));
        deliveryRepository.delete(existing);
    }
    
}
