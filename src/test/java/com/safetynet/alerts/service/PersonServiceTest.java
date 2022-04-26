package com.safetynet.alerts.service;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {

        ArrayList<Person> listPerson = new ArrayList<>();

        Person person = new Person();
        person.setId(Long.parseLong("1"));
        person.setFirstName("Jerome");
        person.setLastName("Pagny");
        person.setAddress("2 rue du coteau");
        person.setCity("Vernaison");
        person.setZip(69390);
        person.setEmail("pagny.jerome@gmail.com");
        person.setPhone("0666131700");

        listPerson.add(person);

        when(personRepository.findById(Long.parseLong("1")))
                .thenReturn(Optional.of(person));

        when(personRepository.findAll()).thenReturn(listPerson);

        doNothing().when(personRepository).deleteById(Long.parseLong("1"));

        when(personRepository.save(person)).thenReturn(person);

    }


    @Test
    public void should_find_all_persons() {
        Iterable person = personService.getPersons();
        long count = StreamSupport.stream(person.spliterator(), false).count();
        assertEquals(1, count);
    }

    @Test
    public void should_find_person_by_id() {
        Optional<Person> person = personService.getPerson(Long.parseLong("1"));
        assertTrue(person.isPresent());
        assertEquals("Jerome", person.get().getFirstName());
    }

    @Test
    public void should_delete_person_by_id() {
        personService.deletePerson(Long.parseLong("1"));
        verify(personRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void should_save_person() {
        Person person = new Person();
        person.setId(Long.parseLong("1"));
        person.setFirstName("Jerome");
        person.setLastName("Pagny");
        person.setAddress("2 rue du coteau");
        person.setCity("Vernaison");
        person.setZip(69390);
        person.setEmail("pagny.jerome@gmail.com");
        person.setPhone("0666131700");

        Person personSaved = personService.savePerson(person);

        assertEquals(person,personSaved);
    }


}
