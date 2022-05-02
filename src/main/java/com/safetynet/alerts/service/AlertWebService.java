package com.safetynet.alerts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertWebService {

    @Autowired
    private PersonService personService;

    public List<String> getListEmailByCity(String city){
        ArrayList<String> listEmail = new ArrayList<>();

        personService.getPersons().forEach(person -> {
            if ( person.getCity().equalsIgnoreCase(city)){
                listEmail.add(person.getEmail());
            }});

        return listEmail;
    }


}
