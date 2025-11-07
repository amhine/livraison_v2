package com.livraison.repository;


import com.livraison.dto.VehicleDTO;
import com.livraison.entity.Vehicle;
import com.livraison.entity.enums.VehicleType ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

   }

