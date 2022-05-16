package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.PersonModel;
import com.safetynet.alerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<PersonModel> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/person/{id}")
    public PersonModel getPerson(@PathVariable final String id) {
        return personService.getPerson(id);
    }

    @PostMapping("/person")
    public PersonModel createPerson(@RequestBody PersonModel personModelToSave) {
        return personService.savePerson(personModelToSave);
    }

    @PutMapping("/person/{id}")
    public PersonModel updatePerson(@PathVariable("id") final String id, @RequestBody PersonModel personModelToUpdate) {
        PersonModel personModel = personService.getPerson(id);

        if (personModel != null) {
            String firstName = personModel.getFirstName();
            String lastName = personModel.getLastName();

            String address = personModelToUpdate.getAddress() == null ? personModel.getAddress() : personModelToUpdate.getAddress();
            String city = personModelToUpdate.getCity() == null ? personModel.getCity() : personModelToUpdate.getCity();
            String zip = personModelToUpdate.getZip() == null ? personModel.getZip() : personModelToUpdate.getZip();
            String phone = personModelToUpdate.getPhone() == null ? personModel.getPhone() : personModelToUpdate.getPhone();
            String email = personModelToUpdate.getEmail() == null ? personModel.getEmail() : personModelToUpdate.getEmail();

            PersonModel personModelUpdated = new PersonModel(id,firstName,lastName,address,city,zip,phone,email);

            personService.updatePerson(personModelUpdated);
            return personModelUpdated;

        } else {
            return null;

        }
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable("id") final String id) {
        personService.deletePerson(id);
    }


}
