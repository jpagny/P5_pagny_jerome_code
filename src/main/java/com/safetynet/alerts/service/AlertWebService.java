package com.safetynet.alerts.service;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

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

        data.put("address", address);

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
                    personDataBuild.append("medications:").append(medicalRecord.get().getMedications()).append(separator);
                    personDataBuild.append("allergies:").append(medicalRecord.get().getAllergies());
                }

                listOfPersons.add(personDataBuild);
            }
        });

        data.put("list of persons", listOfPersons);

        return data;
    }

    public List<String> getInformationPerson(String firstName, String lastName) {

        List<String> listOfPersons = new ArrayList<>();
        String separator = ",";

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        firstName.equalsIgnoreCase(thePerson.getFirstName())
                                && lastName.equalsIgnoreCase(thePerson.getLastName()))
                .forEach(thePerson -> {

                    StringBuilder personDataBuild = new StringBuilder();
                    Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    personDataBuild.append("first name:").append(thePerson.getFirstName()).append(separator);
                    personDataBuild.append("last name:").append(thePerson.getLastName()).append(separator);
                    personDataBuild.append("address:").append(thePerson.getAddress()).append(separator);
                    personDataBuild.append("email:").append(thePerson.getEmail()).append(separator);

                    if (medicalRecord.isPresent()) {
                        personDataBuild.append("age:").append(Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), "MM/dd/yyyy")).append(separator);
                        personDataBuild.append("medications:").append(medicalRecord.get().getMedications()).append(separator);
                        personDataBuild.append("allergies:").append(medicalRecord.get().getAllergies());
                    }

                    listOfPersons.add(personDataBuild.toString());

                });

        return listOfPersons;
    }

    public Map<String, Object> getListOfChildrenByAddress(String address) {

        Map<String, Object> listOfChildren = new HashMap<>();
        String separator = ",";

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        address.equalsIgnoreCase(thePerson.getAddress()))
                .forEach(thePerson -> {
                    Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    int old = Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), "MM/dd/yyyy");

                    if (old <= 18) {
                        Iterable<Person> familyMember = personService.getFamilyMemberByChild(thePerson);
                        StringBuilder childDataBuild = new StringBuilder();

                        childDataBuild.append(thePerson.getFirstName()).append(separator);
                        childDataBuild.append(thePerson.getLastName()).append(separator);
                        childDataBuild.append(old);

                        listOfChildren.put("Enfant : " + thePerson.getId(), childDataBuild);
                        listOfChildren.put("Autres membre de l'enfant " + thePerson.getId() + ":", familyMember);
                    }

                });

        return listOfChildren;
    }


}
