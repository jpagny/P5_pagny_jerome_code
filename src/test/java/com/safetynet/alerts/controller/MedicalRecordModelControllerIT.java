package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.MedicalRecordModel;
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

import java.util.ArrayList;
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
public class MedicalRecordModelControllerIT {

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
    @DisplayName("Show information of all medical record")
    public void showInformationOfAllMedicalRecord() throws Exception {

        String allMedicalRecordJson = Utils.asJsonString(data.getMedicalRecords().values());

        mockMvc.perform(get("/medicalRecords"))
                .andExpect(status().isOk())
                .andExpect(content().json(allMedicalRecordJson));
    }

    @Test
    @DisplayName("Show information of a medical record by id")
    public void showInformationOfAMedicalRecordById() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getMedicalRecords().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        String medicalRecordJson = Utils.asJsonString(data.getMedicalRecords().get(key.get()));

        mockMvc.perform(get("/medicalRecord/" + key))
                .andExpect(status().isOk())
                .andExpect(content().json(medicalRecordJson));
    }

    @Test
    @DisplayName("Create a medical record and return the medical record's information")
    public void createAMedicalRecordAndReturnTheMedicalRecordSInformation() throws Exception {

        ArrayList<String> medications = new ArrayList<>();
        medications.add("aznol:350mg");
        medications.add("hydrapermazol:100mg");

        MedicalRecordModel medicalRecordModel = new MedicalRecordModel("John",
                "Rick",
                "12/17/2021",
                medications,
                new ArrayList<>());

        String newMedicalRecordJson = Utils.asJsonString(medicalRecordModel);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .content(newMedicalRecordJson)
                        .characterEncoding("utf-8")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(newMedicalRecordJson));
    }

    @Test
    @DisplayName("Update an information from a medical record by id")
    public void updateMedicalRecord() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getMedicalRecords().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        MedicalRecordModel theMedicalRecord = data.getMedicalRecords().get(key.get());

        String jsonToUpdate = "{\"medications\":[\"terazine:650mg\", \"hydrapermazol:1000mg\"], " +
                "\"allergies\":[\"peanut:650mg\"]}";

        ArrayList<String> medications = new ArrayList<>();
        medications.add("terazine:650mg");
        medications.add("hydrapermazol:1000mg");

        ArrayList<String> allergies = new ArrayList<>();
        allergies.add("peanut:650mg");

        MedicalRecordModel medicalRecordUpdated = new MedicalRecordModel(key.get(), theMedicalRecord.getFirstName(),
                theMedicalRecord.getLastName(),
                theMedicalRecord.getBirthdate(),
                medications,
                allergies);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/medicalRecord/" + key.get())
                        .characterEncoding("utf-8")
                        .content(jsonToUpdate)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(Utils.asJsonString(medicalRecordUpdated)));
    }


    @Test
    @DisplayName("Delete a medical record by id")
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
