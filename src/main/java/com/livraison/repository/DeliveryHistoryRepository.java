package com.livraison.repository;

import com.livraison.entity.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
}
