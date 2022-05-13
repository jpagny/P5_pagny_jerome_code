package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @SpyBean
    DataFromJsonFile data;

    @BeforeEach
    public void setUp() {
        data.getPersons().clear();

        Person person = new Person();
        person.setId("1");
        person.setFirstName("John");
        person.setLastName("Rick");
        person.setAddress("29 15th St\t");
        person.setCity("Chicago");
        person.setZip("365781");
        person.setEmail("john.rick@gmail.com");
        person.setPhone("07894235694");

        data.getPersons().put("1", person);
    }

    @Test
    public void should_find_all_persons() {
        Iterable persons = personService.getPersons();
        long count = Iterators.size(persons.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_person_by_id() {
        Person person = personService.getPerson("1");
        assertTrue(person != null);
        assertEquals("John", person.getFirstName());
    }

    @Test
    public void should_save_person() {
        Person person = new Person();
        person.setId("2");
        person.setFirstName("Joahn");
        person.setLastName("Rick");
        person.setAddress("29 15th St\t");
        person.setCity("Chicago");
        person.setZip("365781");
        person.setEmail("john.rick@gmail.com");
        person.setPhone("07894235694");

        Person personSaved = personService.savePerson(person);

        assertEquals(person, personSaved);
    }

    @Test
    public void should_delete_person_by_id() {
        personService.deletePerson("1");
        assertTrue(personService.getPerson("1") == null);
    }


}
