package com.livraison.repository;

import com.livraison.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Customer, Long> {
}
