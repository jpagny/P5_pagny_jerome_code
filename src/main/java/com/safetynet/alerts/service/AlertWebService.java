package com.safetynet.alerts.service;

import com.safetynet.alerts.constant.App;
import com.safetynet.alerts.dto.FireDTO;
import com.safetynet.alerts.dto.HouseholdDTO;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
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

    public FireDTO getPersonsAndNumberFireStationByAddress(String address) {

        Optional<FireStationModel> fireStation = fireStationService.getFireStationByAddress(address);

        if ( fireStation.isPresent() ) {

            List<HouseholdDTO> listHouseholdDTO = new ArrayList<>();

            StreamSupport.stream(personService.getPersons().spliterator(), false)
                    .filter(thePerson -> address.equalsIgnoreCase(thePerson.getAddress()))
                    .forEach(thePerson -> {

                        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                        if (medicalRecord.isPresent()) {
                            String firstName = thePerson.getFirstName();
                            String lastName = thePerson.getLastName();
                            String phone = thePerson.getPhone();
                            int age = medicalRecordService.getAge(medicalRecord.get());
                            List<String> medications = medicalRecord.get().getMedications();
                            List<String> allergies = medicalRecord.get().getAllergies();

                            listHouseholdDTO.add(new HouseholdDTO(firstName,lastName, phone, age, medications, allergies));
                        }
            });

            return new FireDTO(address,fireStation.get().getStation(),listHouseholdDTO);
        }

        return null;
    }

    public List<String> getInformationPerson(String firstName, String lastName) {

        List<String> listOfPersons = new ArrayList<>();

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        firstName.equalsIgnoreCase(thePerson.getFirstName())
                                && lastName.equalsIgnoreCase(thePerson.getLastName()))
                .forEach(thePerson -> {

                    StringBuilder personDataBuild = new StringBuilder();
                    Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    personDataBuild.append("first name:").append(thePerson.getFirstName()).append(App.SEPARATOR);
                    personDataBuild.append("last name:").append(thePerson.getLastName()).append(App.SEPARATOR);
                    personDataBuild.append("address:").append(thePerson.getAddress()).append(App.SEPARATOR);
                    personDataBuild.append("email:").append(thePerson.getEmail()).append(App.SEPARATOR);

                    if (medicalRecord.isPresent()) {
                        personDataBuild.append("age:").append(medicalRecordService.getAge(medicalRecord.get())).append(App.SEPARATOR);
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
                    Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    if (medicalRecord.isPresent()) {

                        if (medicalRecordService.isMinor(medicalRecord.get())) {
                            Iterable<PersonModel> familyMember = personService.getFamilyMemberByChild(thePerson);
                            StringBuilder childDataBuild = new StringBuilder();

                            childDataBuild.append(thePerson.getFirstName()).append(App.SEPARATOR);
                            childDataBuild.append(thePerson.getLastName()).append(App.SEPARATOR);
                            childDataBuild.append(medicalRecordService.getAge(medicalRecord.get()));

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
        FireStationModel fireStation = fireStationService.getFireStation(fireStationNumber);

        if (fireStation != null) {
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
        ArrayList<PersonModel> listOfPersons = new ArrayList<>();
        AtomicInteger countChildren = new AtomicInteger();
        AtomicInteger countAdult = new AtomicInteger();

        StreamSupport.stream(fireStationService.getFireStationsByStationNumber(Integer.parseInt(stationNumber)).spliterator(), false)
                .forEach(theStation -> {
                    List<PersonModel> list = StreamSupport.stream(personService.getPersonsByAddress(theStation.getAddress()).spliterator(), false).collect(Collectors.toList());
                    listOfPersons.addAll(list);
                });

        listOfPersons.forEach(thePerson -> {
            Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);
            if (medicalRecord.isPresent()) {
                if (medicalRecordService.isMinor(medicalRecord.get())) {
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

                        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                        if (medicalRecord.isPresent()) {

                            String personDataBuilder = thePerson.getFirstName() + App.SEPARATOR +
                                    thePerson.getLastName() + App.SEPARATOR +
                                    thePerson.getPhone() + App.SEPARATOR +
                                    medicalRecordService.getAge(medicalRecord.get()) +
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
