package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Optional<Person> getPerson(final Long id) {
        return personRepository.findById(id);
    }

    public Iterable<Person> getPersons() {
        return personRepository.findAll();
    }

    public Iterable<Person> getFamilyMemberByChild(Person child) {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .filter(thePerson -> child.getLastName().equalsIgnoreCase(thePerson.getLastName())
                        && !child.getFirstName().equalsIgnoreCase(thePerson.getFirstName()))
                .collect(Collectors.toList());
    }

    public Iterable<Person> getPersonsByAddress(String address){
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .filter(thePerson -> address.equalsIgnoreCase(thePerson.getAddress()))
                .collect(Collectors.toList());
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public void deletePerson(final Long id) {
        personRepository.deleteById(id);
    }

}
