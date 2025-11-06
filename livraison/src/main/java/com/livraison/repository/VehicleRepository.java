package com.livraison.repository;


import com.livraison.dto.VehicleDTO;
import com.livraison.entity.Vehicle;
import com.livraison.entity.enums.VehicleType ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByTypeOrderByCapaciteVolumeDESC(double capacitePoids);

    @Query("Select v From Vehicle v where v.capacitePoids=:capacitePoids Order By v.capacitePoids DESC")
    List<Vehicle> findByCapacitePoids(double capacitePoids);
}

