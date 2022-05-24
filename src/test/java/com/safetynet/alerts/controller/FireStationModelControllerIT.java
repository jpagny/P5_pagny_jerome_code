package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.utility.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FireStationModelControllerIT {

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
    @DisplayName("Show information of all fire stations")
    public void showInformationOfAllFireStation() throws Exception {

        String allFireStationJson = Utils.asJsonString(data.getFireStations().values());

        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(content().json(allFireStationJson));
    }

    @Test
    @DisplayName("Show information of a fire station by id")
    public void getFireStationById() throws Exception {
        FireStationModel fireStation = data.getFireStations().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        String fireStationJson = Utils.asJsonString(fireStation);


        mockMvc.perform(get("/firestation/" + fireStation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(fireStationJson));
    }

    @Test
    @DisplayName("Create a fire station and return the fire station's information")
    public void createFireStation() throws Exception {

        FireStationModel fireStation = new FireStationModel("15 Culver St", "3");
        String fireStationJson = Utils.asJsonString(fireStation);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/firestation")
                        .content(fireStationJson)
                        .characterEncoding("utf-8")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(fireStationJson));
    }

    @Test
    @DisplayName("Update an information from a fire station by id")
    public void updateFireStation() throws Exception {
        AtomicReference<String> key = new AtomicReference<>("");

        data.getFireStations().forEach((k, v) -> {
            if (v.getAddress().contains("1509 Culver St")) {
                key.set(k);
            }
        });

        String jsonToUpdate = "{\"address\":\"1505 Culver St\"}";

        FireStationModel fireStationModel = new FireStationModel(key.get(), "1505 Culver St", "3");
        String fireStationJson = Utils.asJsonString(fireStationModel);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/firestation/" + key.get())
                        .characterEncoding("utf-8")
                        .content(jsonToUpdate)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(fireStationJson));
    }


    @Test
    @DisplayName("Delete a fire station by id")
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
