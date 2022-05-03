package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.utility.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void getAllFireStation() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationById() throws Exception {
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId(Long.parseLong("1"));
        fireStation.setAddress("29 16th St");
        fireStation.setStation("1");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/firestation")
                        .content(Utils.asJsonString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setStation("2");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/firestation/1")
                        .content(Utils.asJsonString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteFireStation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/firestation/1"))
                .andExpect(status().isOk());
    }


}
