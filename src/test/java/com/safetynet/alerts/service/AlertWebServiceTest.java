package com.safetynet.alerts.service;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AlertWebServiceTest {
    /*

    @Autowired
    private AlertWebService alertWebService;

    @MockBean
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        ArrayList<Person> listPerson = new ArrayList<>();

        Person p1 = new Person();
        p1.setCity("Culver");
        p1.setEmail("xxx@gmail.com");

        Person p2 = new Person();
        p2.setCity("Culver");
        p2.setEmail("yyyy@gmail.com");

        Person p3 = new Person();
        p3.setCity("lyon");
        p3.setEmail("bbbb@gmail.com");

        listPerson.add(p1);
        listPerson.add(p2);
        listPerson.add(p3);

        when(personService.getPersons()).thenReturn(listPerson);
    }

    @Test
    public void getListEmailByCity() {
        List<String> listEmail = alertWebService.getListEmailByCity("Culver");
        assertEquals(2, Iterators.size(listEmail.iterator()));
    }
    */

}
