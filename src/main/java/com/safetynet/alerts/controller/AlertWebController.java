package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.*;
import com.safetynet.alerts.service.AlertWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertWebController {

    @Autowired
    private AlertWebService alertWebService;


    @GetMapping("/communityEmail")
    public List<String> getEmailListByCity(@RequestParam String city) {
        return alertWebService.getListEmailByCity(city);
    }

    @GetMapping("/fire")
    public FireDTO getPersonsAndNumberFireStationByAddress(@RequestParam String address) {
        return alertWebService.getPersonsAndNumberFireStationByAddress(address);
    }

    @GetMapping("/personInfo")
    public List<PersonInfoDTO> getInformationPerson(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        return alertWebService.getInformationPerson(firstName, lastName);
    }

    @GetMapping("/childAlert")
    public List<ChildAlertDTO> getListOfChildrenByAddress(@RequestParam String address) {
        return alertWebService.getListOfChildrenByAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> getListOfPhoneNumberByFireStationNumber(@RequestParam String firestation) {
        return alertWebService.getListOfPhoneNumberByFireStationNumber(firestation);
    }

    @GetMapping("/firestation")
    public List<HouseholdFireStationDTO> getListOfHouseholdsByStationNumber(@RequestParam String stationNumber) {
        return alertWebService.getListOfHouseholdByStationNumber(stationNumber);
    }

    @GetMapping("/flood/stations")
    public List<HouseholdFloodDTO> getListOfPersonsByStationsNumber(@RequestParam int[] stations) {
        return alertWebService.getListOfPersonByStationsNumber(stations);
    }

}
