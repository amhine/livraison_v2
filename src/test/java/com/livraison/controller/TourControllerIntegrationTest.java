package com.livraison.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livraison.dto.TourDTO;
import com.livraison.entity.enums.OptimizerType;
import com.livraison.repository.TourRepository;
import com.livraison.service.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TourControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourService tourService;

    @BeforeEach
    void setUp() {
        tourRepository.deleteAll();
    }

    @Test
    void testCreateAndRetrieveTour() throws Exception {
        TourDTO newTour = new TourDTO();
        newTour.setDate(LocalDate.now());
        newTour.setDistanceTotale(0.0);
        newTour.setOptimizerUsed(OptimizerType.plus_proche_voisin);
        newTour.setVehicleId(1L);
        newTour.setWarehouseId(1L);

        MvcResult result = mockMvc.perform(post("/api/tours")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTour)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.date").exists())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        TourDTO createdTour = objectMapper.readValue(response, TourDTO.class);
        assertNotNull(createdTour.getId());

        mockMvc.perform(get("/api/tours/" + createdTour.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdTour.getId()))
                .andExpect(jsonPath("$.optimizerUsed").value(OptimizerType.plus_proche_voisin.toString()));
    }

    @Test
    void testOptimizeTour() throws Exception {
        TourDTO newTour = new TourDTO();
        newTour.setDate(LocalDate.now());
        newTour.setDistanceTotale(100.0);
        newTour.setOptimizerUsed(OptimizerType.plus_proche_voisin);
        newTour.setVehicleId(1L);
        newTour.setWarehouseId(1L);
        TourDTO savedTour = tourService.save(newTour);
        mockMvc.perform(get("/api/tours/" + savedTour.getId() + "/optimize"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTour.getId()))
                .andExpect(jsonPath("$.optimizerUsed").exists());
    }



    @Test
    void testUpdateAndDeleteTour() throws Exception {
        TourDTO newTour = new TourDTO();
        newTour.setDate(LocalDate.now());
        newTour.setDistanceTotale(50.0);
        newTour.setOptimizerUsed(OptimizerType.plus_proche_voisin);
        newTour.setVehicleId(1L);
        newTour.setWarehouseId(1L);

        TourDTO savedTour = tourService.save(newTour);
        savedTour.setDistanceTotale(75.0);

        mockMvc.perform(put("/api/tours/" + savedTour.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedTour)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distanceTotale").value(75.0));

        mockMvc.perform(delete("/api/tours/" + savedTour.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/tours/" + savedTour.getId()))
                .andExpect(status().isNotFound());
    }
}