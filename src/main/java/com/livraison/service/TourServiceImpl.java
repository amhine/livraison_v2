package com.livraison.service;

import com.livraison.dto.TourDTO;
import com.livraison.dto.OptimizeTourRequest;
import com.livraison.entity.*;
import com.livraison.entity.enums.TourStatus;
import com.livraison.entity.enums.OptimizerType;
import com.livraison.mapper.TourMapper;
import com.livraison.optimizer.TourOptimizer;
import com.livraison.repository.*;
import com.livraison.util.DistanceCalculator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final VehicleRepository vehicleRepository;
    private final WarehouseRepository warehouseRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final TourMapper tourMapper;

    public TourServiceImpl(TourRepository tourRepository,
                           VehicleRepository vehicleRepository,
                           WarehouseRepository warehouseRepository,
                           DeliveryRepository deliveryRepository,
                           TourMapper tourMapper) {
        this.tourRepository = tourRepository;
        this.vehicleRepository = vehicleRepository;
        this.warehouseRepository = warehouseRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryHistoryRepository = deliveryHistoryRepository; // correct type
        this.tourMapper = tourMapper;
    }


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
        Warehouses warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Delivery> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour tour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        return tourMapper.toDTO(tourRepository.save(tour));
    }

    @Override
    public TourDTO update(Long id, TourDTO dto) {
        Optional<Tour> existing = tourRepository.findById(id);
        if (existing.isEmpty()) return null;

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId()).orElse(null);
        Warehouses warehouse = warehouseRepository.findById(dto.getWarehouseId()).orElse(null);
        List<Delivery> deliveries = dto.getDeliveryIds() != null ?
                deliveryRepository.findAllById(dto.getDeliveryIds()) : null;

        Tour updatedTour = tourMapper.toEntity(dto, vehicle, warehouse, deliveries);
        updatedTour.setId(id);
        return tourMapper.toDTO(tourRepository.save(updatedTour));
    }

    @Override
    public void delete(Long id) {
        tourRepository.deleteById(id);
    }

    @Override
    public TourDTO updateStatus(Long id, TourStatus status) {
        Optional<Tour> opt = tourRepository.findById(id);
        if (opt.isEmpty()) return null;

        Tour tour = opt.get();
        tour.setStatus(status);

        // Enregistrer un historique lorsque la tournée est terminée
        if (status == TourStatus.COMPLETED) {
            LocalDate deliveryDate = tour.getDate();
            DayOfWeek dow = deliveryDate != null ? deliveryDate.getDayOfWeek() : null;
            List<Delivery> deliveries = tour.getDeliveries();

            if (deliveries != null) {
                for (Delivery d : deliveries) {
                    DeliveryHistory h = DeliveryHistory.builder()
                            .customer(d.getCustomer())
                            .delivery(d)
                            .tour(tour)
                            .deliveryDate(deliveryDate)
                            .plannedTime(null)
                            .actualTime(null)
                            .delay(null)
                            .dayOfWeek(dow != null ? dow.name() : null)
                            .build();
                    deliveryHistoryRepository.save(h);
                }
            }
        }

        Tour saved = tourRepository.save(tour);
        return tourMapper.toDTO(saved);
    }

    @Override
    public TourDTO optimizeTour(Long tourId, TourOptimizer optimizer) {
        Optional<Tour> optTour = tourRepository.findById(tourId);
        if (optTour.isEmpty()) {
            return null;
        }

        Tour tour = optTour.get();
        Warehouses warehouse = tour.getWarehouses();
        List<Delivery> deliveries = tour.getDeliveries();

        if (warehouse == null || deliveries == null || deliveries.isEmpty()) {
            tour.setDistanceTotale(0D);
            tour.setOptimizerUsed(resolveOptimizerType(optimizer));
            Tour saved = tourRepository.save(tour);
            return tourMapper.toDTO(saved);
        }

        List<Delivery> ordered = optimizer.optimize(warehouse, deliveries);
        ordered.forEach(d -> d.setTour(tour));

        tour.setDeliveries(ordered);
        tour.setDistanceTotale(computeTotalDistance(warehouse, ordered));
        tour.setOptimizerUsed(resolveOptimizerType(optimizer));

        Tour saved = tourRepository.save(tour);
        return tourMapper.toDTO(saved);
    }

    @Override
    public double getTotalDistance(Long tourId) {
        Optional<Tour> optTour = tourRepository.findById(tourId);
        if (optTour.isEmpty()) return 0D;

        Tour tour = optTour.get();
        Warehouses warehouse = tour.getWarehouses();
        List<Delivery> ordered = tour.getDeliveries();

        if (warehouse == null || ordered == null || ordered.isEmpty()) return 0D;
        return computeTotalDistance(warehouse, ordered);
    }

    @Override
    public TourDTO createAndOptimize(OptimizeTourRequest req, TourOptimizer optimizer) {
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId()).orElse(null);
        Warehouses warehouse = warehouseRepository.findById(req.getWarehouseId()).orElse(null);
        List<Delivery> deliveries = req.getDeliveryIds() != null ?
                deliveryRepository.findAllById(req.getDeliveryIds()) : java.util.Collections.emptyList();

        Tour toCreate = Tour.builder()
                .date(req.getDate())
                .vehicle(vehicle)
                .warehouses(warehouse)
                .deliveries(deliveries)
                .build();

        if (warehouse != null && deliveries != null && !deliveries.isEmpty()) {
            List<Delivery> ordered = optimizer.optimize(warehouse, deliveries);
            ordered.forEach(d -> d.setTour(toCreate));

            toCreate.setDeliveries(ordered);
            toCreate.setDistanceTotale(computeTotalDistance(warehouse, ordered));
        } else {
            toCreate.setDistanceTotale(0D);
        }

        toCreate.setOptimizerUsed(resolveOptimizerType(optimizer));
        Tour saved = tourRepository.save(toCreate);
        return tourMapper.toDTO(saved);
    }

    private OptimizerType resolveOptimizerType(TourOptimizer optimizer) {
        if (optimizer instanceof com.livraison.optimizer.NearestNeighborOptimizer) {
            return OptimizerType.plus_proche_voisin;
        }
        return OptimizerType.clarke_et_wright;
    }

    private double computeTotalDistance(Warehouses warehouse, List<Delivery> sequence) {
        double total = 0D;
        if (sequence == null || sequence.isEmpty() || warehouse == null) return total;

        Delivery first = sequence.get(0);
        total += DistanceCalculator.calculateDistance(
                warehouse.getLatitude(), warehouse.getLongitude(),
                first.getLatitude(), first.getLongitude()
        );

        for (int i = 0; i < sequence.size() - 1; i++) {
            Delivery a = sequence.get(i);
            Delivery b = sequence.get(i + 1);
            total += DistanceCalculator.calculateDistance(
                    a.getLatitude(), a.getLongitude(),
                    b.getLatitude(), b.getLongitude()
            );
        }

        Delivery last = sequence.get(sequence.size() - 1);
        total += DistanceCalculator.calculateDistance(
                last.getLatitude(), last.getLongitude(),
                warehouse.getLatitude(), warehouse.getLongitude()
        );

        return total;
    }
    @Override
    public double getTotalDistanceAfterOptimization(Long tourId, TourOptimizer optimizer) {
        Optional<Tour> optTour = tourRepository.findById(tourId);
        if (optTour.isEmpty()) return 0D;

        Tour tour = optTour.get();
        Warehouses warehouse = tour.getWarehouses();
        List<Delivery> deliveries = tour.getDeliveries();

        if (warehouse == null || deliveries == null || deliveries.isEmpty()) return 0D;

        // Optimisation de la tournée
        List<Delivery> optimizedDeliveries = optimizer.optimize(warehouse, deliveries);

        // Calcul de la distance totale après optimisation
        return computeTotalDistance(warehouse, optimizedDeliveries);
    }

}
