package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class PersonService {

    @Autowired
    private DataFromJsonFile data;

    public Person getPerson(final String id) {
        return data.getPersons().get(id);
    }

    public Iterable<Person> getPersons() {
        return data.getPersons().values();
    }

    public Iterable<Person> getFamilyMemberByChild(Person child) {
        return StreamSupport.stream(data.getPersons().values().spliterator(), false)
                .filter(thePerson -> child.getLastName().equalsIgnoreCase(thePerson.getLastName())
                        && !child.getFirstName().equalsIgnoreCase(thePerson.getFirstName()))
                .collect(Collectors.toList());
    }

    public Iterable<Person> getPersonsByAddress(String address) {
        return StreamSupport.stream(data.getPersons().values().spliterator(), false)
                .filter(thePerson -> address.equalsIgnoreCase(thePerson.getAddress()))
                .collect(Collectors.toList());
    }

    public Person savePerson(Person person) {
        String uniqueID = UUID.randomUUID().toString();
        data.getPersons().put(uniqueID, person);
        return data.getPersons().get(uniqueID);
    }

    public void deletePerson(final String id) {
        data.getPersons().remove(id);
    }

}
