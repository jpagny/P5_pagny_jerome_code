package com.safetynet.alerts;

import com.safetynet.alerts.config.LoadDataFromDataJsonRunner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AlertsApplicationTests {

	@SpyBean
	LoadDataFromDataJsonRunner loadDataFromDataJsonRunner;

	@Test
	void contextLoads() throws Exception {
		verify(loadDataFromDataJsonRunner,times(1)).run(any());
	}

}
