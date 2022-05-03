package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AlertWebService {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private MedicalRecordService medicalRecordService;

    public List<String> getListEmailByCity(String city) {
        ArrayList<String> listEmail = new ArrayList<>();

        personService.getPersons().forEach(person -> {
            if (person.getCity().equalsIgnoreCase(city)) {
                listEmail.add(person.getEmail());
            }
        });

        return listEmail;
    }

    public Map<String, Object> getPersonsAndNumberFireStationByAddress(String address) {

        Map<String, Object> data = new HashMap<>();
        List<Object> listOfPersons = new ArrayList<>();
        String separator = ",";

        Optional<FireStation> fireStation = fireStationService.getFireStationByAddress(address);

        fireStation.ifPresent(station -> data.put("number fireStation", station.getStation()));

        personService.getPersons().forEach(person -> {

            if (person.getAddress().equalsIgnoreCase(address)) {

                StringBuilder personDataBuild = new StringBuilder();
                Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

                personDataBuild.append("first name:").append(person.getFirstName()).append(separator);
                personDataBuild.append("last name:").append(person.getLastName()).append(separator);
                personDataBuild.append("phone:").append(person.getPhone()).append(separator);

                if (medicalRecord.isPresent()) {
                    personDataBuild.append("age:").append(Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), "MM/dd/yyyy")).append(separator);
                    personDataBuild.append("medications:").append(medicalRecord.get().getMedications());
                }

                listOfPersons.add(personDataBuild);
            }
        });

        data.put("list of persons", listOfPersons);

        return data;
    }


}
