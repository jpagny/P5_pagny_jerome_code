package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.utility.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void getAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMedicalRecordById() throws Exception {
        mockMvc.perform(get("/medicalRecord/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(Long.parseLong("1"));
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Rick");
        medicalRecord.setBirthdate("12/17/2021");
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .content(Utils.asJsonString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(Long.parseLong("1"));
        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:150mg");
        medicalRecord.setMedications(medications);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord/1")
                        .content(Utils.asJsonString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteMedicalRecord() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/medicalRecord/1"))
                .andExpect(status().isOk());
    }
}
