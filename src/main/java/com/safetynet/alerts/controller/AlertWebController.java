package com.safetynet.alerts.controller;

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




}
