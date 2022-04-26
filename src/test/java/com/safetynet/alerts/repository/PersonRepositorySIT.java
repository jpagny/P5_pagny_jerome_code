package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(scripts = "/data-test.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonRepositorySIT {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void should_find_all_persons() {
        Iterable person = personRepository.findAll();
        long count = StreamSupport.stream(person.spliterator(), false).count();
        assertEquals(23, count);
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
