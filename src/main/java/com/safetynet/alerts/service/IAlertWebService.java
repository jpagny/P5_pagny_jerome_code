package com.safetynet.alerts.service;

import com.safetynet.alerts.dto.*;

import java.util.List;

public interface IAlertWebService {

    List<String> getListEmailByCity(String city);

    FireDTO getPersonsAndNumberFireStationByAddress(String address);

    List<PersonInfoDTO> getInformationPerson(String firstName, String lastName);

    List<ChildAlertDTO> getListOfChildrenByAddress(String address);

    List<String> getListOfPhoneNumberByFireStationNumber(String fireStationNumber);

    List<HouseholdFireStationDTO> getListOfHouseholdByStationNumber(String stationNumber);

    List<HouseholdFloodDTO> getListOfPersonByStationsNumber(int[] stationNumbers);

}
