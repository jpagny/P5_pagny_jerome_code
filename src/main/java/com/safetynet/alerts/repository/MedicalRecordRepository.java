package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.MedicalRecord;
import org.springframework.data.repository.CrudRepository;

public interface MedicalRecordRepository extends CrudRepository<MedicalRecord, Long> {
}
