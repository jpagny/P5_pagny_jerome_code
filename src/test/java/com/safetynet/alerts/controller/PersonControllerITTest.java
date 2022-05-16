package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.Person;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {IntegrationTestConfig.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    DataFromJsonFile data;

    @Autowired
    private WebApplicationContext WebContext;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(WebContext).build();
    }

    @Test
    public void getAllPerson() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Jacob")));
    }

    @Test
    public void getPersonById() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("Jacob")) {
                key.set(k);
            }
        });

        mockMvc.perform(get("/person/" + key))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Boyd")));
    }

    @Test
    public void createPerson() throws Exception {

        Person person = new Person();
        person.setFirstName("Nathalie");
        person.setLastName("Couard");
        person.setAddress("2 avenue du Professeur Coley");
        person.setCity("Mystere");
        person.setZip("666999");
        person.setPhone("xxxxxxxxx");
        person.setEmail("nathalie@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .content(Utils.asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Nathalie")));
    }

    @Test
    public void updatePerson() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        Person person = new Person();
        person.setAddress("1509 Culver St");
        person.setCity("Culver");
        person.setZip("97450");
        person.setPhone("841-874-6515");
        person.setEmail("jabod@email.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/" + key)
                        .content(Utils.asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"zip\":\"97451\"")));
    }

    @Test
    public void deletePerson() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person/" + key))
                .andExpect(status().isOk());
    }


}
