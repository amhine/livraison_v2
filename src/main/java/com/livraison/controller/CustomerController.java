package com.livraison.controller;

import com.livraison.entity.Customer;
import com.livraison.dto.CustomerDTO;
import com.livraison.mapper.CustomerMapper;
import com.livraison.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer) {
        Customer savedCustomer = customerService.saveCustomer(customerMapper.toEntity(customer));
        return ResponseEntity.ok(customerMapper.toDto(savedCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDTO customer
    ) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerMapper.toEntity(customer));
        return ResponseEntity.ok(customerMapper.toDto(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> list = customerService.getAllCustomers().stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<CustomerDTO>> searchByName(@RequestParam String name) {
        List<CustomerDTO> list = customerService.searchByName(name).stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<CustomerDTO>> searchByAddress(@RequestParam String address) {
        List<CustomerDTO> list = customerService.searchByAddress(address).stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search/timeslot")
    public ResponseEntity<List<CustomerDTO>> searchByTimeSlot(@RequestParam String timeSlot) {
        List<CustomerDTO> list = customerService.searchByTimeSlot(timeSlot).stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
