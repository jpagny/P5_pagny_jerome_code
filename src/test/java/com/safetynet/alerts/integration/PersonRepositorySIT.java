package com.safetynet.alerts.integration;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.integration.config.IntegrationTestConfig;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {IntegrationTestConfig.class})
public class PersonRepositorySIT {

    @Autowired
    private PersonService personService;

    @Test
    public void should_find_all_persons() {
        Iterable<Person> person = personService.getPersons();
        long count = Iterators.size(person.iterator());
        assertEquals(2, count);
    }

    @Test
    public void should_find_person_by_id() {
        Person person = personService.getPersons().iterator().next();
        String id = person.getId();
        person = personService.getPerson(id);
        assertNotNull(person);
    }

    @Test
    public void should_delete_person_by_id() {
        personService.deletePerson("1");
        Person person = personService.getPerson("1");
        assertThat(person).isNull();
    }


}
