package com.safetynet.alerts.repository;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.api.model.Person;
import com.safetynet.alerts.api.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(IntegrationTestConfig.class)
public class PersonRepositorySIT {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void should_find_all_persons() {
        Iterable person = personRepository.findAll();
        long count = Iterators.size(person.iterator());
        assertEquals(2, count);
    }

    @Test
    public void should_find_person_by_id() {
        Optional<Person> person = personRepository.findById(Long.parseLong("1"));
        assertEquals("John", person.get().getFirstName());
    }

    @Test
    public void should_delete_person_by_id() {
        personRepository.deleteById(Long.parseLong("1"));
        Optional<Person> person = personRepository.findById(Long.parseLong("1"));
        assertThat(person).isEmpty();
    }


}
