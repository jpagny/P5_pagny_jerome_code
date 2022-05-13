package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable final String id) {
        return personService.getPerson(id);
    }

    @PostMapping("/person")
    public Person createPerson(@RequestBody Person personToSave) {
        return personService.savePerson(personToSave);
    }

    @PutMapping("/person/{id}")
    public Person updatePerson(@PathVariable("id") final String id, @RequestBody Person personToUpdate) {
        Person person = personService.getPerson(id);

        if (person != null) {

            Person currentPerson = person;
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
    public void deletePerson(@PathVariable("id") final String id) {
        personService.deletePerson(id);
    }


}
