package com.safetynet.alerts.service;

import com.safetynet.alerts.model.PersonModel;

public interface IPersonService {
    PersonModel getPerson(final String id);

    Iterable<PersonModel> getPersons();

    Iterable<PersonModel> getFamilyMemberByChild(PersonModel child);

    Iterable<PersonModel> getPersonsByAddress(String address);

    PersonModel savePerson(PersonModel personModel);

    PersonModel updatePerson(PersonModel personModel);

    void deletePerson(final String id);
}
