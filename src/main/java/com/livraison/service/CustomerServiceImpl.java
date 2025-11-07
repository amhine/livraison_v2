package com.livraison.service;

import com.livraison.entity.Customer;
import com.livraison.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer c = existingCustomer.get();
            c.setName(customer.getName());
            c.setAddress(customer.getAddress());
            c.setLatitude(customer.getLatitude());
            c.setLongitude(customer.getLongitude());
            c.setPreferredTimeSlot(customer.getPreferredTimeSlot());
            return customerRepository.save(c);
        } else {
            throw new RuntimeException("Customer not found with id " + id);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> searchByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Customer> searchByAddress(String address) {
        return customerRepository.findByAddressContainingIgnoreCase(address);
    }

    @Override
    public List<Customer> searchByTimeSlot(String timeSlot) {
        return customerRepository.findByPreferredTimeSlot(timeSlot);
    }
}
