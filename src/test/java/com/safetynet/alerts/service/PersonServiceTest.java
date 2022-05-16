package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.PersonModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {
        data.getPersons().clear();

        PersonModel person = new PersonModel("John", "Rick", "29 15th St", "Chicago", "365781", "john.rick@gmail.com", "07894235694");
        data.getPersons().put(person.getId(), person);
    }

    @Test
    @DisplayName("show all persons")
    public void showAllPersons() {
        Iterable<PersonModel> persons = personService.getPersons();
        long count = Iterators.size(persons.iterator());
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Show a person by id")
    public void showAPersonById() {

        PersonModel person = data.getPersons().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        PersonModel personToFind = personService.getPerson(person.getId());
        assertNotNull(person);
        assertEquals(personToFind, person);
    }

    @Test
    @DisplayName("Create a new person")
    public void createANewPerson() {
        PersonModel person = new PersonModel("Johna", "Ricky", "29 15th St", "Chicago", "365781", "john.rick@gmail.com", "07894235694");
        PersonModel personSaved = personService.savePerson(person);
        assertEquals(person, personSaved);
    }

    @Test
    @DisplayName("Update a person")
    public void updateAPerson() {

        PersonModel person = data.getPersons().entrySet()
                .stream()
                .findFirst()
                .get().getValue();

        PersonModel personToUpdate = data.getPersons().get(person.getId());
        person.setEmail("xxx@gmail.com");
        PersonModel personUpdated = personService.updatePerson(person);
        assertEquals(personToUpdate, personUpdated);
    }

    @Test
    @DisplayName("Delete a person by id")
    public void deleteAPersonById() {
        personService.deletePerson("1");
        assertNull(personService.getPerson("1"));
    }

    @Test
    @DisplayName("Show all family member by a child")
    void showAllFamilyMemberByAChild() {

        AtomicReference<String> key = new AtomicReference<>("");

        data.getPersons().forEach((k, v) -> {
            if (v.getFirstName().equals("John")) {
                key.set(k);
            }
        });

        PersonModel theFather = data.getPersons().get(key.get());
        PersonModel theChild = new PersonModel("Rocky", "Rick", "29 15th St", "Chicago", "365781", "john.ricka@gmail.com", "07894235694");
        PersonModel otherChild = new PersonModel("Rock", "Spring", "28 15th St", "Chicago", "365781", "johna.ricka@gmail.com", "07894235694");

        data.getPersons().put(theChild.getId(), theChild);
        data.getPersons().put(otherChild.getId(), otherChild);

        Iterable<PersonModel> listFamilyMember = personService.getFamilyMemberByChild(theChild);
        int countFamilyMember = Iterators.size(listFamilyMember.iterator());

        assertEquals(1, countFamilyMember);
        assertEquals(theFather, listFamilyMember.iterator().next());
    }

    @Test
    @DisplayName("Show all persons which live in an address")
    void showAllPersonsWhichLiveInAnAddress() {
        ArrayList<PersonModel> listAllPersonsWhichLiveInThisAddress = new ArrayList<>();

        data.getPersons().forEach((k, v) -> {
            if (v.getAddress().equals("29 15th St")) {
                listAllPersonsWhichLiveInThisAddress.add(v);
            }
        });

        Iterable<PersonModel> listPersons = personService.getPersonsByAddress("29 15th St");
        int countPersons = Iterators.size(listPersons.iterator());

        assertEquals(1, countPersons);
        assertEquals(listAllPersonsWhichLiveInThisAddress, listPersons);
    }


}
