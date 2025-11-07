package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.entity.*;
import com.livraison.mapper.TourMapper;
import com.livraison.optimizer.TourOptimizer;
import com.livraison.repository.*;
import com.livraison.util.DistanceCalculator;
import com.livraison.entity.enums.OptimizerType;
import com.livraison.dto.OptimizeTourRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final TourMapper tourMapper;



    @Override
    public List<TourDTO> findAll() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TourDTO findById(Long id) {
        return tourRepository.findById(id)
                .map(tourMapper::toDTO)
                .orElse(null);
    }

    @Override
    public TourDTO save(TourDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Customer> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour tour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        return tourMapper.toDTO(tourRepository.save(tour));
    }

    @Override
    public TourDTO update(Long id, TourDTO dto) {
        Optional<Tour> existing = tourRepository.findById(id);
        if (existing.isEmpty()) return null;

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Customer> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour updatedTour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        updatedTour.setId(id);
        return tourMapper.toDTO(tourRepository.save(updatedTour));
    }

    @Override
    public void delete(Long id) {
        tourRepository.deleteById(id);
    }



    public double getTotalDistanceAfterOptimization(Long id, TourOptimizer optimizer) {
        TourDTO optimized = optimizeTour(id, optimizer);
        return optimized.getDistanceTotale();
    }

    @Override
    public TourDTO optimizeTour(Long tourId, TourOptimizer optimizer) {
        Optional<Tour> optTour = tourRepository.findById(tourId);
        if (optTour.isEmpty()) {
            return null;
        }

        Tour tour = optTour.get();
        Warehouse warehouse = tour.getWarehouses();
        List<Customer> deliveries = tour.getDeliveries();

        if (warehouse == null || deliveries == null || deliveries.isEmpty()) {
            tour.setDistanceTotale(0D);
            tour.setOptimizerUsed(resolveOptimizerType(optimizer));
            Tour saved = tourRepository.save(tour);
            return tourMapper.toDTO(saved);
        }

        List<Customer> ordered = optimizer.optimize(warehouse, deliveries);

        for (Customer d : ordered) {
            d.setTour(tour);
        }
        tour.setDeliveries(ordered);

        double total = computeTotalDistance(warehouse, ordered);
        tour.setDistanceTotale(total);
        tour.setOptimizerUsed(resolveOptimizerType(optimizer));

        Tour saved = tourRepository.save(tour);
        return tourMapper.toDTO(saved);
    }

    @Override
    public double getTotalDistance(Long tourId) {
        Optional<Tour> optTour = tourRepository.findById(tourId);
        if (optTour.isEmpty()) {
            return 0D;
        }
        Tour tour = optTour.get();
        Warehouse warehouse = tour.getWarehouses();
        List<Customer> ordered = tour.getDeliveries();
        if (warehouse == null || ordered == null || ordered.isEmpty()) {
            return 0D;
        }
        return computeTotalDistance(warehouse, ordered);
    }

    @Override
    public TourDTO createAndOptimize(OptimizeTourRequest req, TourOptimizer optimizer) {
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId()).orElse(null);
        Warehouse warehouse = warehouseRepository.findById(req.getWarehouseId()).orElse(null);
        List<Customer> deliveries = req.getDeliveryIds() != null ?
                deliveryRepository.findAllById(req.getDeliveryIds()) : java.util.Collections.emptyList();

        Tour toCreate = Tour.builder()
                .date(req.getDate())
                .vehicle(vehicle)
                .warehouses(warehouse)
                .deliveries(deliveries)
                .build();

        if (warehouse != null && deliveries != null && !deliveries.isEmpty()) {
            List<Customer> ordered = optimizer.optimize(warehouse, deliveries);
            for (Customer d : ordered) {
                d.setTour(toCreate);
            }
            toCreate.setDeliveries(ordered);
            double total = computeTotalDistance(warehouse, ordered);
            toCreate.setDistanceTotale(total);
            toCreate.setOptimizerUsed(resolveOptimizerType(optimizer));
        } else {
            toCreate.setDistanceTotale(0D);
            toCreate.setOptimizerUsed(resolveOptimizerType(optimizer));
        }

        Tour saved = tourRepository.save(toCreate);
        return tourMapper.toDTO(saved);
    }

    private OptimizerType resolveOptimizerType(TourOptimizer optimizer) {
        if (optimizer instanceof com.livraison.optimizer.NearestNeighborOptimizer) {
            return OptimizerType.plus_proche_voisin;
        }
        return OptimizerType.clarke_et_wright;
    }

    private double computeTotalDistance(Warehouse warehouse, List<Customer> sequence) {
        double total = 0D;
        if (sequence == null || sequence.isEmpty() || warehouse == null) {
            return total;
        }

        Customer first = sequence.get(0);
        total += DistanceCalculator.calculateDistance(
                warehouse.getLatitude(), warehouse.getLongitude(),
                first.getLatitude(), first.getLongitude()
        );

        for (int i = 0; i < sequence.size() - 1; i++) {
            Customer a = sequence.get(i);
            Customer b = sequence.get(i + 1);
            total += DistanceCalculator.calculateDistance(
                    a.getLatitude(), a.getLongitude(),
                    b.getLatitude(), b.getLongitude()
            );
        }

        Customer last = sequence.get(sequence.size() - 1);
        total += DistanceCalculator.calculateDistance(
                last.getLatitude(), last.getLongitude(),
                warehouse.getLatitude(), warehouse.getLongitude()
        );

        return total;
    }

}
