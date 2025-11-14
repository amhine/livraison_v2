package com.livraison.service;

import com.livraison.dto.DeliveryHistoryDTO;
import com.livraison.entity.DeliveryHistory;
import com.livraison.mapper.DeliveryHistoryMapper;
import com.livraison.repository.DeliveryHistoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryHistoryServiceImpl implements DeliveryHistoryService {

    private final DeliveryHistoryRepository deliveryHistoryRepository;
    private final DeliveryHistoryMapper deliveryHistoryMapper;

    @Override
    public DeliveryHistoryDTO create(DeliveryHistoryDTO deliveryHistoryDto){
        DeliveryHistory entity = deliveryHistoryMapper.toEntity(deliveryHistoryDto);
        DeliveryHistory saved = deliveryHistoryRepository.save(entity);
        return deliveryHistoryMapper.toDTO(saved);
    }
    @Override
    public DeliveryHistoryDTO getHistoryByDeliveryId(Long id) {

        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DeliveryHistory not found with id " + id));

        return deliveryHistoryMapper.toDTO(deliveryHistory);
    }


    @Override
    public List<DeliveryHistoryDTO> getAll(){
        return  deliveryHistoryRepository.findAll()
                .stream()
                .map(deliveryHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id){
        deliveryHistoryRepository.deleteById(id);
    }


}
