package com.safetynet.alerts.utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAgeByBirthdate(String brithDate, String pattern){
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(brithDate, DateTimeFormatter.ofPattern(pattern));
        return Period.between(birthday, today).getYears();
    }

}
