package com.livraison.repository;


import com.livraison.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
