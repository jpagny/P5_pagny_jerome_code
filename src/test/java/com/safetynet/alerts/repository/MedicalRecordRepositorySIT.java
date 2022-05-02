package com.safetynet.alerts.repository;

import com.google.common.collect.Iterators;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.config.IntegrationTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(IntegrationTestConfig.class)
public class MedicalRecordRepositorySIT {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Test
    public void should_find_all_medicalRecords() {
        Iterable<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        long count = Iterators.size(medicalRecords.iterator());
        assertEquals(4, count);
    }

    @Test
    public void should_find_medicalRecord_by_id() {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(Long.parseLong("1"));
        assertTrue(medicalRecord.isPresent());
        assertEquals("John", medicalRecord.get().getFirstName());
    }

    @Test
    public void should_delete_medicalRecord_by_id() {
        medicalRecordRepository.deleteById(Long.parseLong("1"));
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(Long.parseLong("1"));
        assertThat(medicalRecord).isEmpty();
    }
}
