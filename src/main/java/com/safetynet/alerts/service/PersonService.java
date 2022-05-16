package com.safetynet.alerts.service;

import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private DataFromJsonFile data;

    public PersonModel getPerson(final String id) {
        return data.getPersons().get(id);
    }

    public Iterable<PersonModel> getPersons() {
        return data.getPersons().values();
    }

    public Iterable<PersonModel> getFamilyMemberByChild(PersonModel child) {
        return data.getPersons().values().stream()
                .filter(thePerson -> child.getLastName().equalsIgnoreCase(thePerson.getLastName())
                        && !child.getFirstName().equalsIgnoreCase(thePerson.getFirstName()))
                .collect(Collectors.toList());
    }

    public Iterable<PersonModel> getPersonsByAddress(String address) {
        return data.getPersons().values().stream()
                .filter(thePerson -> address.equalsIgnoreCase(thePerson.getAddress()))
                .collect(Collectors.toList());
    }

    public PersonModel savePerson(PersonModel personModel) {
        data.getPersons().put(personModel.getId(), personModel);
        return data.getPersons().get(personModel.getId());
    }

    public PersonModel updatePerson(PersonModel personModel){
        data.getPersons().put(personModel.getId(), personModel);
        return personModel;
    }

    public void deletePerson(final String id) {
        data.getPersons().remove(id);
    }

}
