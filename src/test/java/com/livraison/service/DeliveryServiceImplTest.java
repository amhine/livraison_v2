package com.livraison.service;

import com.livraison.dto.DeliveryDTO;
import com.livraison.entity.Delivery;
import com.livraison.entity.Tour;
import com.livraison.mapper.DeliveryMapper;
import com.livraison.repository.DeliveryRepository;
import com.livraison.repository.TourRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryServiceImplTest {

    private DeliveryRepository deliveryRepository;
    private TourRepository tourRepository;
    private DeliveryMapper deliveryMapper;

    private DeliveryServiceImpl service;

    @BeforeEach
    void setup() {
        deliveryRepository = mock(DeliveryRepository.class);
        tourRepository = mock(TourRepository.class);
        deliveryMapper = new DeliveryMapper() {
            @Override public DeliveryDTO toDTO(Delivery d) {
                DeliveryDTO dto = new DeliveryDTO();
                if (d == null) return null;
                dto.setId(d.getId());
                dto.setNameClient(d.getNameClient());
                dto.setAddress(d.getAddress());
                dto.setLatitude(d.getLatitude());
                dto.setLongitude(d.getLongitude());
                dto.setPoids(d.getPoids());
                dto.setVolume(d.getVolume());
                dto.setStatus(d.getStatus());
                dto.setTourId(d.getTour() != null ? d.getTour().getId() : null);
                return dto;
            }
            @Override public Delivery toEntity(DeliveryDTO dto) {
                if (dto == null) return null;
                Delivery d = new Delivery();
                d.setId(dto.getId());
                d.setNameClient(dto.getNameClient());
                d.setAddress(dto.getAddress());
                d.setLatitude(dto.getLatitude());
                d.setLongitude(dto.getLongitude());
                d.setPoids(dto.getPoids());
                d.setVolume(dto.getVolume());
                d.setStatus(dto.getStatus());
                if (dto.getTourId() != null) {
                    d.setTour(Tour.builder().id(dto.getTourId()).build());
                }
                return d;
            }
        };
        service = new DeliveryServiceImpl(deliveryRepository, deliveryMapper, tourRepository);
    }

    @Test
    void getDeliveryById_whenNotFound_throws() {
        when(deliveryRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getDeliveryById(99L));
    }

    @Test
    void createDelivery_withTour_setsRelation() {
        DeliveryDTO input = new DeliveryDTO();
        input.setNameClient("Alice");
        input.setTourId(7L);
        when(tourRepository.findById(7L)).thenReturn(Optional.of(Tour.builder().id(7L).build()));
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(inv -> inv.getArgument(0));

        DeliveryDTO created = service.createDelivery(input);
        assertEquals(7L, created.getTourId());
    }
}
