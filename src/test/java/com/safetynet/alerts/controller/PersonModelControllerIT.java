package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.config.IntegrationTestConfig;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.PersonModel;
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
public class PersonModelControllerIT {

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
    @DisplayName("Show information of all persons")
    public void showInformationOfAllPersons() throws Exception {

        String allPersonsJson = Utils.asJsonString(data.getPersons().values());

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(allPersonsJson));
    }

    @Test
    @DisplayName("Show information of a person by id")
    public void showInformationOfAPersonById() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        String personJson = Utils.asJsonString(data.getPersons().get(key.get()));

        mockMvc.perform(get("/person/" + key))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(personJson));
    }

    @Test
    @DisplayName("Create a new person and return the person's information")
    public void createANewPersonAndReturnThePersonSInformation() throws Exception {

        PersonModel personModel = new PersonModel("Nathalie", "Kylian", "2 avenue du Professor Coley"
                , "Mystery", "666999", "6678954123", "nathalie@gmail.com");

        String newPersonJson = Utils.asJsonString(personModel);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/person")
                        .content(newPersonJson)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(newPersonJson));
    }


    @Test
    @DisplayName("Updated an information from a person by id")
    public void updatePerson() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        PersonModel thePerson = data.getPersons().get(key.get());

        String jsonToUpdate = "{\"email\":\"test@gmail.com\"}";

        PersonModel personUpdated = new PersonModel(key.toString(), thePerson.getFirstName(), thePerson.getLastName(),
                thePerson.getAddress(), thePerson.getCity(), thePerson.getZip(), thePerson.getPhone(), "test@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/" + key.get())
                        .characterEncoding("utf-8")
                        .contentType(APPLICATION_JSON)
                        .content(jsonToUpdate)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(Utils.asJsonString(personUpdated)));
    }

    @Test
    @DisplayName("Update all information from a person by id")
    public void updateAllPersonSIInformation() throws Exception {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        PersonModel thePerson = data.getPersons().get(key.get());

        String jsonToUpdate = "{\"address\":\"25 rue de la glace\", " +
                "\"city\":\"Eskimo\", " +
                "\"zip\":\"66998\", " +
                "\"phone\":\"+6699778822\", " +
                "\"email\":\"test@gmail.com\"}";

        PersonModel personUpdated = new PersonModel(key.toString(), thePerson.getFirstName(), thePerson.getLastName(),
                "25 rue de la glace", "Eskimo", "66998", "+6699778822", "test@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/" + key.get())
                        .characterEncoding("utf-8")
                        .contentType(APPLICATION_JSON)
                        .content(jsonToUpdate)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(Utils.asJsonString(personUpdated)));
    }

    @Test
    @DisplayName("Update all information from a wrong id and return null")
    public void updateAllInformationFromAWrongIdAndReturnNull() throws Exception {

        String jsonToUpdate = "{\"address\":\"25 rue de la glace\", " +
                "\"city\":\"Eskimo\", " +
                "\"zip\":\"66998\", " +
                "\"phone\":\"+6699778822\", " +
                "\"email\":\"test@gmail.com\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/person/xx")
                        .characterEncoding("utf-8")
                        .contentType(APPLICATION_JSON)
                        .content(jsonToUpdate)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }


    @Test
    @DisplayName("Delete a person by id")
    public void deleteAPersonById() throws Exception {

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

    @Test
    @DisplayName("Delete a person with wrong id")
    public void deleteAPersonWithWrongId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/person/xxx"))
                .andExpect(status().isOk());
    }


}
