package com.livraison.dto;

import com.livraison.entity.enums.StatusLivraison;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    private StatusLivraison status;
}
