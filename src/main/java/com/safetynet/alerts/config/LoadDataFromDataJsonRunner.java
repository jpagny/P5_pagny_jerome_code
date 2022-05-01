package com.safetynet.alerts.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class LoadDataFromDataJsonRunner implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        JsonNode nodes = mapper.readTree(inputStream);

        // save person data
        if (Iterators.size(personRepository.findAll().iterator()) == 0) {
            String listPersons = nodes.get("persons").toString();
            List<Person> listPerson = mapper.readValue(listPersons, new TypeReference<List<Person>>() {
            });
            personRepository.saveAll(listPerson);
        }


    }

}
