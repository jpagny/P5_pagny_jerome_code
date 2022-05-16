package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.utility.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class FireStationControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private DataFromJsonFile data;

    @Autowired
    private WebApplicationContext WebContext;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }


    @Test
    public void getAllFireStation() throws Exception {
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("address\":\"29 15th St")));
    }

    @Test
    public void getFireStationById() throws Exception {
        mockMvc.perform(get("/firestation/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void createFireStation() throws Exception {

        FireStation fireStation = new FireStation();
        fireStation.setId("xxxxx");
        fireStation.setAddress("15 Culver St");
        fireStation.setStation("3");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/firestation")
                        .content(Utils.asJsonString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("15 Culver St")));
    }

    @Test
    public void updateFireStation() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getFireStations().forEach((k, v) -> {
            if (v.getAddress().contains("15 Culver St")) {
                key.set(k);
            }
        });

        FireStation fireStation = new FireStation();
        fireStation.setAddress("1505 Culver St");
        fireStation.setStation("2");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/firestation/" + key)
                        .content(Utils.asJsonString(fireStation))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1505 Culver St")));
    }


    @Test
    public void deleteFireStation() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getFireStations().forEach((k, v) -> {
            if (v.getAddress().contains("1509 Culver St")) {
                key.set(k);
            }
        });

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/firestation/" + key))
                .andExpect(status().isOk());
    }


}
