package com.safetynet.alerts.controller;

import com.safetynet.alerts.service.AlertWebService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlertWebController.class)
public class AlertWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertWebService alertWebService;

    @Test
    public void getAllEmailByCity() throws Exception {
        mockMvc.perform(get("/communityEmail")
                        .param("city","Culver"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonsAndNumberFireStationByAddress() throws Exception {
        mockMvc.perform(get("/fire")
                        .param("address","1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void getInformationPerson() throws Exception {
        mockMvc.perform(get("/personInfo")
                        .param("firstName","John")
                        .param("lastName","Boyd"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListOfChildrenByAddress() throws Exception{
        mockMvc.perform(get("/childAlert")
                        .param("address","1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListOfPhoneNumberByFireStationNumber() throws Exception{
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation_number","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getListOfPersonByStationNumber() throws Exception{
        mockMvc.perform(get("/firestation")
                        .param("station_number","1"))
                .andExpect(status().isOk());
    }

}
