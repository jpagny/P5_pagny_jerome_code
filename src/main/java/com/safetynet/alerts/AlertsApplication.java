package com.safetynet.alerts;

import com.safetynet.alerts.config.LoadDataFromDataJson;
import com.safetynet.alerts.model.DataFromJsonFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class AlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Bean
    public DataFromJsonFile jsonFileLoader() throws IOException {
        log.info("Data JSON loaded");
        return LoadDataFromDataJson.fetchAllData("/data.json");
    }

}
