package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecord;
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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicalRecordControllerITTest {

    @SpyBean
    private DataFromJsonFile data;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext WebContext;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }

    @Test
    public void getAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("medications")));
    }

    @Test
    public void getMedicalRecordById() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getMedicalRecords().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        mockMvc.perform(get("/medicalRecord/" + key))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("medications")));
    }

    @Test
    public void createMedicalRecord() throws Exception {

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId("1");
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
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")));
    }

    @Test
    public void updateMedicalRecord() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getMedicalRecords().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(String.valueOf(key));
        medicalRecord.setBirthdate("03/07/1984");

        ArrayList<String> medications = new ArrayList<>();
        medications.add("pharmacol:5000mg");
        medicalRecord.setMedications(medications);

        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecord.setAllergies(allergies);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord/" + key)
                        .content(Utils.asJsonString(medicalRecord))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("pharmacol:5000mg")));
    }


    @Test
    public void deleteMedicalRecord() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getMedicalRecords().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/medicalRecord/" + key))
                .andExpect(status().isOk());
    }
}
