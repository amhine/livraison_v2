package com.livraison.mapper;

import com.livraison.dto.DeliveryHistoryDTO;
import com.livraison.entity.DeliveryHistory;
import org.springframework.stereotype.Component;

@Component
public class DeliveryHistoryMapper {

    public DeliveryHistoryDTO toDTO(DeliveryHistory deliveryHistory){
        if(deliveryHistory==null){
            return null;
        }
        return DeliveryHistoryDTO.builder()
                .id(deliveryHistory.getId())
                .deliveryDate(deliveryHistory.getDeliveryDate())
                .plannedTime(deliveryHistory.getPlannedTime())
                .actualTime(deliveryHistory.getActualTime())
                .delay(deliveryHistory.getDelay())
                .dayOfWeek(deliveryHistory.getDayOfWeek())
                .build();
    }


    public DeliveryHistory toEntity(DeliveryHistoryDTO deliveryHistoryDto) {
        if (deliveryHistoryDto == null) {
            return null;
        }
        return DeliveryHistory.builder()
                .id(deliveryHistoryDto.getId())
                .deliveryDate(deliveryHistoryDto.getDeliveryDate())
                .plannedTime(deliveryHistoryDto.getPlannedTime())
                .actualTime(deliveryHistoryDto.getActualTime())
                .delay(deliveryHistoryDto.getDelay())
                .dayOfWeek(deliveryHistoryDto.getDayOfWeek())
                .build();
    }
}
