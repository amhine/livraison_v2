package com.livraison.repository;


import com.livraison.entity.Warehouses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouses, Long> {
   
}
