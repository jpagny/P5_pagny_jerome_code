package com.safetynet.alerts.service;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.DataFromJsonFile;
import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

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

        Person person = new Person();
        person.setId("1");
        person.setFirstName("John");
        person.setLastName("Rick");
        person.setAddress("29 15th St");
        person.setCity("Chicago");
        person.setZip("365781");
        person.setEmail("john.rick@gmail.com");
        person.setPhone("07894235694");

        data.getPersons().put("1", person);
    }

    @Test
    public void should_find_all_persons() {
        Iterable<Person> persons = personService.getPersons();
        long count = Iterators.size(persons.iterator());
        assertEquals(1, count);
    }

    @Test
    public void should_find_person_by_id() {
        Person person = personService.getPerson("1");
        assertNotNull(person);
        assertEquals("John", person.getFirstName());
    }

    @Test
    public void should_save_person() {
        Person person = new Person();
        person.setId("2");
        person.setFirstName("Joahn");
        person.setLastName("Rick");
        person.setAddress("29 15th St");
        person.setCity("Chicago");
        person.setZip("365781");
        person.setEmail("john.rick@gmail.com");
        person.setPhone("07894235694");

        Person personSaved = personService.savePerson(person);

        assertEquals(person, personSaved);
    }

    @Test
    public void should_update_person(){
        Person person = data.getPersons().get("1");
        person.setEmail("xxx@gmail.com");

        Person personUpdated = personService.updatePerson(person);

        assertEquals(person, personUpdated);
    }

    @Test
    public void should_delete_person_by_id() {
        personService.deletePerson("1");
        assertNull(personService.getPerson("1"));
    }

    @Test
    void should_returnListFamilyMemberByChild(){

        Person child = new Person();
        child.setId("2");
        child.setFirstName("Rocky");
        child.setLastName("Rick");
        child.setAddress("29 15th St");
        child.setCity("Chicago");
        child.setZip("365781");
        child.setEmail("john.rick@gmail.com");
        child.setPhone("07894235694");

        Person child2 = new Person();
        child2.setId("3");
        child2.setFirstName("Rocky");
        child2.setLastName("Spong");
        child2.setAddress("29 15th St");
        child2.setCity("Chicago");
        child2.setZip("365781");
        child2.setEmail("john.rick@gmail.com");
        child2.setPhone("07894235694");

        data.getPersons().put("2",child);
        data.getPersons().put("3",child2);

        Iterable<Person> listFamilyMember = personService.getFamilyMemberByChild(child);
        int countFamilyMember = Iterators.size(listFamilyMember.iterator());

        assertEquals(1, countFamilyMember);
        assertEquals(data.getPersons().get("1"),listFamilyMember.iterator().next());
    }

    @Test
    void should_return_listPersons_byAnAddress(){
        Iterable<Person> listPersons = personService.getPersonsByAddress("29 15th St");
        int countPersons = Iterators.size(listPersons.iterator());

        assertEquals(1, countPersons);
        assertEquals(data.getPersons().get("1"),listPersons.iterator().next());
    }

}
