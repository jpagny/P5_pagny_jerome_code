package com.safetynet.alerts.api.controller;

import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPerson(@PathVariable final Long id) {
        return personService.getPerson(id);
    }

    @PostMapping("/person")
    public Person createPerson(@RequestBody Person personToSave) {
        return personService.savePerson(personToSave);
    }

    @PutMapping("/person/{id}")
    public Person updateEmployee(@PathVariable("id") final Long id, @RequestBody Person personToUpdate) {
        Optional<Person> person = personService.getPerson(id);

        if (person.isPresent()) {

            Person currentPerson = person.get();
            String address = personToUpdate.getAddress();
            String city = personToUpdate.getCity();
            String zip = personToUpdate.getZip();
            String phone = personToUpdate.getPhone();
            String email = personToUpdate.getEmail();

            if (address != null) {
                currentPerson.setAddress(address);
            }

            if (zip != null) {
                currentPerson.setCity(city);
            }

            if (phone != null) {
                currentPerson.setPhone(phone);
            }

            if (email != null) {
                currentPerson.setEmail(email);
            }

            personService.savePerson(currentPerson);
            return currentPerson;

        } else {
            return null;

        }
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable("id") final Long id) {
        personService.deletePerson(id);
    }


}
