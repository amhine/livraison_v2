package com.livraison.repository;

import com.livraison.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCase(String name);

    List<Customer> findByAddressContainingIgnoreCase(String address);

    List<Customer> findByPreferredTimeSlot(String preferredTimeSlot);
}
