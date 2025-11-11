package com.livraison.repository;

import com.livraison.entity.Delivery;
import com.livraison.entity.enums.StatusLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Page<Delivery> findAll(Pageable pageable);

    Page<Delivery> findByNameClientContainingIgnoreCase(String nameClient, Pageable pageable);

    Page<Delivery> findByStatus(StatusLivraison status, Pageable pageable);

    @Query("select d from Delivery d where d.latitude between :minLat and :maxLat and d.longitude between :minLon and :maxLon")
    Page<Delivery> searchByGeo(@Param("minLat") double minLat,
                               @Param("maxLat") double maxLat,
                               @Param("minLon") double minLon,
                               @Param("maxLon") double maxLon,
                               Pageable pageable);
}
