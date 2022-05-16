package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.model.FireStationModel;
import com.safetynet.alerts.model.MedicalRecordModel;
import com.safetynet.alerts.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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

        if (fireStation.isPresent()) {

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

                            listHouseholdDTO.add(new HouseholdDTO(firstName, lastName, phone, age, medications, allergies));
                        }
                    });

            return new FireDTO(address, fireStation.get().getStation(), listHouseholdDTO);
        }

        return null;
    }

    public List<PersonInfoDTO> getInformationPerson(String firstName, String lastName) {

        List<PersonInfoDTO> listOfPersonInfos = new ArrayList<>();

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        lastName.equalsIgnoreCase(thePerson.getLastName()))
                .forEach(thePerson -> {

                    Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    if (medicalRecord.isPresent()) {
                        String theFirstName = thePerson.getFirstName();
                        String address = thePerson.getAddress();
                        String city = thePerson.getCity();
                        String zip = thePerson.getZip();
                        String email = thePerson.getEmail();
                        int age = medicalRecordService.getAge(medicalRecord.get());
                        List<String> medications = medicalRecord.get().getMedications();
                        List<String> allergies = medicalRecord.get().getAllergies();

                        listOfPersonInfos.add(new PersonInfoDTO(theFirstName, lastName, age, address, city, zip, email, medications, allergies));

                    }

                });

        return listOfPersonInfos;
    }

    public List<ChildAlertDTO> getListOfChildrenByAddress(String address) {

        List<ChildAlertDTO> listOfChildren = new ArrayList();

        StreamSupport.stream(personService.getPersons().spliterator(), false)
                .filter(thePerson ->
                        address.equalsIgnoreCase(thePerson.getAddress()))
                .forEach(thePerson -> {
                    Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                    if (medicalRecord.isPresent()) {

                        if (medicalRecordService.isMinor(medicalRecord.get())) {
                            Iterable<PersonModel> familyMember = personService.getFamilyMemberByChild(thePerson);
                            String firstName = thePerson.getFirstName();
                            String lastName = thePerson.getLastName();
                            int age = medicalRecordService.getAge(medicalRecord.get());

                            listOfChildren.add(new ChildAlertDTO(firstName, lastName, age, familyMember));
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

    public List<HouseholdFireStationDTO> getListOfHouseholdByStationNumber(String stationNumber) {

        List<HouseholdFireStationDTO> householdFireStationDTO = new ArrayList<>();

        StreamSupport.stream(fireStationService.getFireStationsByStationNumber(Integer.parseInt(stationNumber)).spliterator(), false)
                .forEach(theStation -> {

                    AtomicInteger countChildren = new AtomicInteger();
                    AtomicInteger countAdult = new AtomicInteger();
                    List<PersonStationDTO> listOfPersons = new ArrayList<>();

                    StreamSupport.stream(personService.getPersonsByAddress(theStation.getAddress()).spliterator(), false)
                            .forEach(thePerson -> {

                                String firstName = thePerson.getFirstName();
                                String lastName = thePerson.getLastName();
                                String address = thePerson.getAddress();
                                String phone = thePerson.getPhone();
                                listOfPersons.add(new PersonStationDTO(firstName, lastName, address, phone));

                                Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);
                                if (medicalRecord.isPresent()) {
                                    if (medicalRecordService.isMinor(medicalRecord.get())) {
                                        countChildren.getAndIncrement();
                                    } else {
                                        countAdult.getAndIncrement();
                                    }
                                }
                            });

                    String numberStation = theStation.getStation();
                    int countAdultPersons = countAdult.get();
                    int countChildPersons = countChildren.get();

                    householdFireStationDTO.add(new HouseholdFireStationDTO(numberStation, listOfPersons, countAdultPersons, countChildPersons));
                });

        return householdFireStationDTO;
    }

    public List<HouseholdFloodDTO> getListOfPersonByStationsNumber(int[] stationNumbers) {

        List<HouseholdFloodDTO> listHouseholdFloodDto = new ArrayList<>();

        Arrays.stream(stationNumbers)
                .forEach(number -> StreamSupport.stream(fireStationService.getFireStationsByStationNumber(number).spliterator(), false)
                        .forEach(theStation -> {

                            List<PersonFloodDTO> listOfPersonFloodDTO = new ArrayList<>();

                            StreamSupport.stream(personService.getPersonsByAddress(theStation.getAddress()).spliterator(), false)
                                    .forEach(thePerson -> {

                                        Optional<MedicalRecordModel> medicalRecord = medicalRecordService.getMedicalRecordByPerson(thePerson);

                                        if (medicalRecord.isPresent()) {
                                            String lastName = thePerson.getLastName();
                                            List<String> medications = medicalRecord.get().getMedications();
                                            List<String> allergies = medicalRecord.get().getAllergies();
                                            String phone = thePerson.getPhone();
                                            int age = medicalRecordService.getAge(medicalRecord.get());

                                            listOfPersonFloodDTO.add(new PersonFloodDTO(lastName, medications, allergies, phone, age));
                                        }

                                    });

                            String address = theStation.getAddress();

                            listHouseholdFloodDto.add(new HouseholdFloodDTO(address, String.valueOf(number), listOfPersonFloodDTO));
                        }));

        return listHouseholdFloodDto;
    }


}
