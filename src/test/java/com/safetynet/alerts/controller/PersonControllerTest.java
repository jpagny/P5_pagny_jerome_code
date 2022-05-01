package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.api.controller.PersonController;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    public void getAllPerson() throws Exception {
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPersonById() throws Exception {
        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void createPerson() throws Exception {

        Person person = new Person();
        person.setLastName("xxx");
        person.setFirstName("xxx");
        person.setAddress("xxx");
        person.setCity("xxx");
        person.setZip("xxx");
        person.setPhone("xxx");
        person.setEmail("xxx");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updatePerson() throws Exception {

        Person person = new Person();
        person.setLastName("xxx");
        person.setFirstName("xxx");
        person.setAddress("xxx");
        person.setCity("xxx");
        person.setZip("xxx");
        person.setPhone("xxx");
        person.setEmail("xxx");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/1")
                        .content(asJsonString(person))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void deletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person/1"))
                .andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
