package com.safetynet.alerts;

import com.safetynet.alerts.config.LoadDataFromDataJson;
import com.safetynet.alerts.model.DataFromJsonFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class AlertsApplication {

    private static final Logger LOG = LogManager.getLogger(AlertsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    @Bean
    public DataFromJsonFile jsonFileLoader() throws IOException {
        LOG.debug("Data JSON loaded");
        return LoadDataFromDataJson.fetchAllData("/data.json");
    }

}
