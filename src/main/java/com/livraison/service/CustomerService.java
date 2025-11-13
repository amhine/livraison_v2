package com.livraison.service;

import com.livraison.entity.Customer;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    List<Customer> getAllCustomers();

    List<Customer> searchByName(String name);

    List<Customer> searchByAddress(String address);

    List<Customer> searchByTimeSlot(String timeSlot);
}
