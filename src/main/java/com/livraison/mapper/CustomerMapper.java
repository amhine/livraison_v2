package com.livraison.mapper;

import com.livraison.dto.CustomerDTO;
import com.livraison.entity.Customer;
import org.springframework.stereotype.Component;

@Component

public class CustomerMapper {
    public CustomerDTO toDto(Customer customer){
        if(customer == null){
            return null;
        }
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .latitude(customer.getLatitude())
                .longitude(customer.getLongitude())
                .preferredTimeSlot(customer.getPreferredTimeSlot())
                .build();

    }

    public Customer toEntity(CustomerDTO dto){
        if(dto==null){
            return null;
        }
        return Customer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .preferredTimeSlot(dto.getPreferredTimeSlot())
                .build();
    }
}
