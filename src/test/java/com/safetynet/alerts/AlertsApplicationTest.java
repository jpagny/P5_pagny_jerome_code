package com.safetynet.alerts;

import com.safetynet.alerts.config.LoadDataFromDataJson;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest
class AlertsApplicationTest {

    @SpyBean
    LoadDataFromDataJson loadDataFromDataJson;



}
