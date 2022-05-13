package com.safetynet.alerts.service;

import com.safetynet.alerts.constant.App;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
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
            if (person.getCity().equalsIgnoreCase(city)
                    && !listEmail.contains(person.getEmail())) {
                listEmail.add(person.getEmail());
            }
        });

        return listEmail;
    }

    public Map<String, Object> getPersonsAndNumberFireStationByAddress(String address) {

        Map<String, Object> data = new HashMap<>();
        List<Object> listOfPersons = new ArrayList<>();
        Optional<FireStation> fireStation = fireStationService.getFireStationByAddress(address);

        data.put("address", address);

        fireStation.ifPresent(station -> data.put("number fireStation", station.getStation()));

        personService.getPersons().forEach(person -> {

            if (person.getAddress().equalsIgnoreCase(address)) {

                StringBuilder personDataBuild = new StringBuilder();
                Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);

                personDataBuild.append("first name:").append(person.getFirstName()).append(App.SEPARATOR);
                personDataBuild.append("last name:").append(person.getLastName()).append(App.SEPARATOR);
                personDataBuild.append("phone:").append(person.getPhone()).append(App.SEPARATOR);

                if (medicalRecord.isPresent()) {
                    personDataBuild.append("age:").append(Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(),
                            App.DATE_FORMAT)).append(App.SEPARATOR);
                    personDataBuild.append("medications:").append(medicalRecord.get().getMedications()).append(App.SEPARATOR);
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

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        firstName.equalsIgnoreCase(thePerson.getFirstName())
                                && lastName.equalsIgnoreCase(thePerson.getLastName()))
                .forEach(thePerson -> {

                    StringBuilder personDataBuild = new StringBuilder();
                    Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    personDataBuild.append("first name:").append(thePerson.getFirstName()).append(App.SEPARATOR);
                    personDataBuild.append("last name:").append(thePerson.getLastName()).append(App.SEPARATOR);
                    personDataBuild.append("address:").append(thePerson.getAddress()).append(App.SEPARATOR);
                    personDataBuild.append("email:").append(thePerson.getEmail()).append(App.SEPARATOR);

                    if (medicalRecord.isPresent()) {
                        personDataBuild.append("age:").append(Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate()
                                , App.DATE_FORMAT)).append(App.SEPARATOR);
                        personDataBuild.append("medications:").append(medicalRecord.get().getMedications()).append(App.SEPARATOR);
                        personDataBuild.append("allergies:").append(medicalRecord.get().getAllergies());
                    }

                    listOfPersons.add(personDataBuild.toString());
                });

        return listOfPersons;
    }

    public Map<String, Object> getListOfChildrenByAddress(String address) {

        Map<String, Object> listOfChildren = new LinkedHashMap<>();

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        address.equalsIgnoreCase(thePerson.getAddress()))
                .forEach(thePerson -> {
                    Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    if (medicalRecord.isPresent()) {
                        int old = Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), App.DATE_FORMAT);

                        if (old <= App.AGE_OF_MAJORITY) {
                            Iterable<Person> familyMember = personService.getFamilyMemberByChild(thePerson);
                            StringBuilder childDataBuild = new StringBuilder();

                            childDataBuild.append(thePerson.getFirstName()).append(App.SEPARATOR);
                            childDataBuild.append(thePerson.getLastName()).append(App.SEPARATOR);
                            childDataBuild.append(old);

                            listOfChildren.put("Enfant id " + thePerson.getId() + " :", childDataBuild);
                            listOfChildren.put("Autres membre de l'enfant " + thePerson.getFirstName()
                                    + "(" + thePerson.getId() + ") :", familyMember);
                        }
                    }
                });

        return listOfChildren;
    }

    public List<String> getListOfPhoneNumberByFireStationNumber(String fireStationNumber) {
        List<String> listOfPhoneNumber = new ArrayList<>();
        FireStation fireStation = fireStationService.getFireStation(fireStationNumber);

        if ( fireStation != null) {
            StreamSupport.stream(personService.getPersons().spliterator(), false)
                    .filter(thePerson -> fireStation.getAddress().equalsIgnoreCase(thePerson.getAddress()))
                    .forEach(thePerson -> {
                        if (!listOfPhoneNumber.contains(thePerson.getPhone())) {
                            listOfPhoneNumber.add(thePerson.getPhone());
                        }
                    });
        }

        return listOfPhoneNumber;
    }

    public Map<String, String> getListOfPersonByStationNumber(String stationNumber) {

        Map<String, String> data = new LinkedHashMap<>();
        ArrayList<Person> listOfPersons = new ArrayList<>();
        AtomicInteger countChildren = new AtomicInteger();
        AtomicInteger countAdult = new AtomicInteger();

        StreamSupport.stream(fireStationService.getFireStationsByStationNumber(Integer.parseInt(stationNumber)).spliterator(), false)
                .forEach(theStation -> {
                    List<Person> list = StreamSupport.stream(personService.getPersonsByAddress(theStation.getAddress()).spliterator(), false).collect(Collectors.toList());
                    listOfPersons.addAll(list);
                });

        listOfPersons.forEach(thePerson -> {
            Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);
            if (medicalRecord.isPresent()) {
                int age = Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), App.DATE_FORMAT);
                if (age <= App.AGE_OF_MAJORITY) {
                    countChildren.getAndIncrement();
                } else {
                    countAdult.getAndIncrement();
                }
            }

            String personDataBuilder = thePerson.getFirstName() + App.SEPARATOR +
                    thePerson.getLastName() + App.SEPARATOR +
                    thePerson.getAddress() + App.SEPARATOR +
                    thePerson.getPhone();

            data.put("person id " + thePerson.getId() + " :", personDataBuilder);
        });

        data.put("count total children", countChildren.toString());
        data.put("count total adults:", countAdult.toString());

        return data;
    }

    public Map<String, Map<String, String>> getListOfPersonByStationsNumber(int[] stationNumber) {
        Map<String, Map<String, String>> listOfFireStation = new LinkedHashMap<>();

        Arrays.stream(stationNumber).forEach(number -> StreamSupport.stream(fireStationService.getFireStationsByStationNumber(number).spliterator(), false)
                .forEach(theStation -> {

                    Map<String, String> listOfPersons = new HashMap<>();

                    StreamSupport.stream(personService.getPersonsByAddress(theStation.getAddress()).spliterator(), false).forEach(thePerson -> {

                        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                        if (medicalRecord.isPresent()) {

                            String personDataBuilder = thePerson.getFirstName() + App.SEPARATOR +
                                    thePerson.getLastName() + App.SEPARATOR +
                                    thePerson.getPhone() + App.SEPARATOR +
                                    Utils.getAgeByBirthdate(medicalRecord.get().getBirthdate(), App.DATE_FORMAT) +
                                    App.SEPARATOR +
                                    medicalRecord.get().getMedications() + App.SEPARATOR +
                                    medicalRecord.get().getAllergies();

                            listOfPersons.put("Person id " + thePerson.getId(), personDataBuilder);
                        }

                    });

                    listOfFireStation.put("station id " + theStation.getStation() + " - " + theStation.getAddress(), listOfPersons);
                }));

        return listOfFireStation;
    }


}
