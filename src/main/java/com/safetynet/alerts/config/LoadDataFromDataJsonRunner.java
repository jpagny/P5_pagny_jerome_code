package com.safetynet.alerts.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
public class LoadDataFromDataJsonRunner implements CommandLineRunner {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public void run(String... args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        JsonNode nodes = mapper.readTree(inputStream);

        // save person data
        if (Iterators.size(personRepository.findAll().iterator()) == 0) {
            String personNodes = nodes.get("persons").toString();
            List<Person> listPerson = mapper.readValue(personNodes, new TypeReference<>() {
            });
            personRepository.saveAll(listPerson);
        }

        // save fireStation data
        if (Iterators.size(fireStationRepository.findAll().iterator()) == 0){
            String fireStationNodes = nodes.get("firestations").toString();
            List<FireStation> listFireStations = mapper.readValue(fireStationNodes, new TypeReference<>() {
            });
            fireStationRepository.saveAll(listFireStations);
        }

        // save medicalRecord data
        if (Iterators.size(medicalRecordRepository.findAll().iterator()) == 0){
            String medicalRecordNodes = nodes.get("medicalrecords").toString();
            List<MedicalRecord> listMedicalRecord = mapper.readValue(medicalRecordNodes, new TypeReference<>() {
            });
            medicalRecordRepository.saveAll(listMedicalRecord);
        }

    }

}
