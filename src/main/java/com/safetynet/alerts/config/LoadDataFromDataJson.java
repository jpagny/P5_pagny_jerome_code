package com.safetynet.alerts.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

        Map<String, Person> personData = buildPersonsData();
        Map<String, FireStation> fireStationMap = buildFireStationData();
        Map<String, MedicalRecord> medicalRecordMap = buildMedicalRecordData();

        LOG.debug("Loaded from a Json file : \r\n"
                + (personData.size()) + " persons \r\n"
                + (fireStationMap.size())
                + " firestations \r\n"
                + (medicalRecordMap.size())
                + " medicalrecords");

        return new DataFromJsonFile(personData, fireStationMap, medicalRecordMap);
    }

    private static Map<String, Person> buildPersonsData() throws IOException {

        Map<String, Person> personMap = new HashMap<>();

        JsonNode nodes = loadNodes();
        String personNodes = nodes.get("persons").toString();
        List<Person> personList = mapper.readValue(personNodes, new TypeReference<>() {
        });

        personList.forEach(thePerson -> {
            String uniqueID = UUID.randomUUID().toString();
            thePerson.setId(uniqueID);
            personMap.put(uniqueID, thePerson);
        });

        return personMap;
    }

    private static Map<String, FireStation> buildFireStationData() throws IOException {

        Map<String, FireStation> fireStationMap = new HashMap<>();
        JsonNode nodes = loadNodes();
        String fireStationNodes = nodes.get("firestations").toString();
        List<FireStation> fireStationList = mapper.readValue(fireStationNodes, new TypeReference<>() {
        });

        fireStationList.forEach(theFireStation -> {
            String uniqueID = UUID.randomUUID().toString();
            theFireStation.setId(uniqueID);
            fireStationMap.put(uniqueID, theFireStation);
        });

        return fireStationMap;
    }

    private static Map<String, MedicalRecord> buildMedicalRecordData() throws IOException {

        Map<String, MedicalRecord> medicalRecordMap = new HashMap<>();
        JsonNode nodes = loadNodes();
        String medicalRecordNodes = nodes.get("medicalrecords").toString();
        List<MedicalRecord> medicalRecordList = mapper.readValue(medicalRecordNodes, new TypeReference<>() {
        });

        medicalRecordList.forEach(theMedicalRecord -> {
            String uniqueID = UUID.randomUUID().toString();
            theMedicalRecord.setId(uniqueID);
            medicalRecordMap.put(uniqueID, theMedicalRecord);
        });

        return medicalRecordMap;
    }


}