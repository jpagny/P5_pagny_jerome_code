package com.safetynet.alerts.integration.config;

import com.safetynet.alerts.config.LoadDataFromDataJson;
import com.safetynet.alerts.model.DataFromJsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;

@TestConfiguration
public class IntegrationTestConfig {

    @Bean
    @Primary
    public DataFromJsonFile jsonFileLoader() throws IOException {
        return LoadDataFromDataJson.fetchAllData("/data-test.json");
    }

}
