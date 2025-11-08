package com.livraison.controller;

import com.livraison.entity.Customer;
import com.livraison.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.saveCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer
    ) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Customer>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchByName(name));
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<Customer>> searchByAddress(@RequestParam String address) {
        return ResponseEntity.ok(customerService.searchByAddress(address));
    }

    @GetMapping("/search/timeslot")
    public ResponseEntity<List<Customer>> searchByTimeSlot(@RequestParam String timeSlot) {
        return ResponseEntity.ok(customerService.searchByTimeSlot(timeSlot));
    }
}
