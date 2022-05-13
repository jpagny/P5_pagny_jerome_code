package com.safetynet.alerts.integration;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.integration.config.IntegrationTestConfig;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {IntegrationTestConfig.class})
public class MedicalRecordRepositorySIT {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @Test
    public void should_find_all_medicalRecords() {
        Iterable<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecords();
        long count = Iterators.size(medicalRecords.iterator());
        assertEquals(4, count);
    }

    @Test
    public void should_find_medicalRecord_by_id() {
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecords().iterator().next();
        String id = medicalRecord.getId();
        medicalRecord = medicalRecordService.getMedicalRecord(id);
        assertTrue(medicalRecord != null);
    }

    @Test
    public void should_delete_medicalRecord_by_id() {
        medicalRecordService.deleteMedicalRecord("1");
        MedicalRecord medicalRecord = medicalRecordService.getMedicalRecord("1");
        assertThat(medicalRecord).isNull();
    }
}
