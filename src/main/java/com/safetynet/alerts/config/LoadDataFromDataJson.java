package com.safetynet.alerts.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoadDataFromDataJson {
    private static final Logger LOG = LogManager.getLogger(LoadDataFromDataJson.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String fileJsonData;

    private static JsonNode loadNodes() throws IOException {
        InputStream inputStream = TypeReference.class.getResourceAsStream(fileJsonData);
        return mapper.readTree(inputStream);
    }

    public static DataFromJsonFile fetchAllData(String theFileJsonData) throws IOException {

        fileJsonData = theFileJsonData;

        Map<String, PersonModel> personData = buildPersonsData();
        Map<String, FireStationModel> fireStationMap = buildFireStationData();
        Map<String, MedicalRecordModel> medicalRecordMap = buildMedicalRecordData();

        LOG.debug("Loaded from a Json file : \r\n"
                + (personData.size()) + " persons \r\n"
                + (fireStationMap.size())
                + " firestations \r\n"
                + (medicalRecordMap.size())
                + " medicalrecords");

        return new DataFromJsonFile(personData, fireStationMap, medicalRecordMap);
    }

    private static Map<String, PersonModel> buildPersonsData() throws IOException {

        Map<String, PersonModel> personMap = new HashMap<>();

        JsonNode nodes = loadNodes();
        String personNodes = nodes.get("persons").toString();

        List<PersonModel> personModelList = mapper.readValue(personNodes, new TypeReference<>() {
        });

        personModelList.forEach(thePerson -> {
            PersonModel person = new PersonModel(thePerson.getFirstName(),
                    thePerson.getLastName(),
                    thePerson.getAddress(),
                    thePerson.getCity(),
                    thePerson.getZip(),
                    thePerson.getPhone(),
                    thePerson.getEmail()
            );
            personMap.put(person.getId(), person);
        });

        return personMap;
    }

    private static Map<String, FireStationModel> buildFireStationData() throws IOException {

        Map<String, FireStationModel> fireStationMap = new HashMap<>();
        JsonNode nodes = loadNodes();
        String fireStationNodes = nodes.get("firestations").toString();
        List<FireStationModel> fireStationModelList = mapper.readValue(fireStationNodes, new TypeReference<>() {
        });

        fireStationModelList.forEach(theFireStation -> {
            FireStationModel fireStation = new FireStationModel(
                    theFireStation.getAddress(),
                    theFireStation.getStation()
            );
            fireStationMap.put(fireStation.getId(), fireStation);
        });

        return fireStationMap;
    }

    private static Map<String, MedicalRecordModel> buildMedicalRecordData() throws IOException {

        Map<String, MedicalRecordModel> medicalRecordMap = new HashMap<>();
        JsonNode nodes = loadNodes();
        String medicalRecordNodes = nodes.get("medicalrecords").toString();
        List<MedicalRecordModel> medicalRecordModelList = mapper.readValue(medicalRecordNodes, new TypeReference<>() {
        });

        medicalRecordModelList.forEach(theMedicalRecord -> {
            MedicalRecordModel medicalRecord = new MedicalRecordModel(
                    theMedicalRecord.getFirstName(),
                    theMedicalRecord.getLastName(),
                    theMedicalRecord.getBirthdate(),
                    theMedicalRecord.getMedications(),
                    theMedicalRecord.getAllergies()
            );
            medicalRecordMap.put(medicalRecord.getId(), medicalRecord);
        });

        return medicalRecordMap;
    }


}
