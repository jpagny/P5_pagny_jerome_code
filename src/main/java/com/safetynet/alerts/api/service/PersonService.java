package com.safetynet.alerts.api.service;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.repository.PersonRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    public Person savePerson(Person person) {
        Person personSaved = personRepository.save(person);
        return personSaved;
    }

    public void deletePerson(final Long id) {
        personRepository.deleteById(id);
    }



}
