package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
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
public class AlertWebControllerITTest {

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
    @DisplayName("Show all email by city")
    public void showAllEmailByCity() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Culver"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jaboyd@email.com")));
    }

    @Test
    @DisplayName("List persons and number station by an address")
    public void getPersonsAndNumberFireStationByAddress() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"address\":\"1509 Culver St\",\"numberStation\":\"3\"")));
    }

    @Test
    @DisplayName("Get person's information by first name and last name")
    public void getInformationPersonByFirstNameAndLastName() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "John")
                        .param("lastName", "Boyd"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":2,\"address\":\"1509 Culver St\"")));
    }

    @Test
    @DisplayName("Get list of children with family member by address")
    public void getListOfChildrenByAddress() throws Exception {
        mockMvc.perform(get("/childAlert")
                        .param("address", "1509 Culver St"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"firstName\":\"Jacob\",\"lastName\":\"Boyd\",\"age\":2,\"listFamilyMember\":[{")));
    }

    @Test
    public void getListOfPhoneNumberByFireStationNumber() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getFireStations().forEach((k, v) -> {
            if (v.getAddress().contains("1509 Culver St")) {
                key.set(k);
            }
        });

        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", key.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("841-874-6512")));
    }

    @Test
    public void getListOfPersonByStationNumber() throws Exception {
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"count total children\":\"1\",\"count total adults:\":\"1\"")));
    }

    @Test
    public void getListOfPersonByStationsNumber() throws Exception {
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "3,2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Person id")));
    }

}
