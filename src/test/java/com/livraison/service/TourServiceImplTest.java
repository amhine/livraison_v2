package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.entity.*;
import com.livraison.mapper.TourMapper;
import com.livraison.optimizer.NearestNeighborOptimizer;
import com.livraison.repository.DeliveryRepository;
import com.livraison.repository.TourRepository;
import com.livraison.repository.VehicleRepository;
import com.livraison.repository.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourServiceImplTest {

    private TourRepository tourRepository;
    private VehicleRepository vehicleRepository;
    private WarehouseRepository warehouseRepository;
    private DeliveryRepository deliveryRepository;
    private TourMapper tourMapper;

    private TourServiceImpl service;

    @BeforeEach
    void setup() {
        tourRepository = mock(TourRepository.class);
        vehicleRepository = mock(VehicleRepository.class);
        warehouseRepository = mock(WarehouseRepository.class);
        deliveryRepository = mock(DeliveryRepository.class);
        tourMapper = new TourMapper();
        service = new TourServiceImpl(tourRepository, vehicleRepository, warehouseRepository, deliveryRepository, tourMapper);
    }

    @Test
    void findAll_mapsToDTOs() {
        Tour t = Tour.builder().id(1L).date(LocalDate.now()).build();
        when(tourRepository.findAll()).thenReturn(List.of(t));

        List<TourDTO> list = service.findAll();
        assertEquals(1, list.size());
        assertEquals(1L, list.get(0).getId());
    }

    @Test
    void optimizeTour_updatesOrderAndDistance() {
        Warehouse wh = Warehouse.builder().id(1L).latitude(48.8566).longitude(2.3522).build();
        Customer d1 = Customer.builder().id(1L).latitude(48.8606).longitude(2.3376).build();
        Customer d2 = Customer.builder().id(2L).latitude(48.8570).longitude(2.3709).build();
        Tour tour = Tour.builder().id(10L).warehouses(wh).deliveries(List.of(d1, d2)).build();

        when(tourRepository.findById(10L)).thenReturn(Optional.of(tour));
        when(tourRepository.save(any(Tour.class))).thenAnswer(inv -> inv.getArgument(0));

        TourDTO dto = service.optimizeTour(10L, new NearestNeighborOptimizer());
        assertNotNull(dto);
        assertEquals(10L, dto.getId());

        ArgumentCaptor<Tour> captor = ArgumentCaptor.forClass(Tour.class);
        verify(tourRepository).save(captor.capture());
        Tour saved = captor.getValue();
        assertNotNull(saved.getDeliveries());
        assertEquals(2, saved.getDeliveries().size());
        assertTrue(saved.getDistanceTotale() >= 0.0);
    }
}
